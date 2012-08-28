//$Id: PojoComponentTuplizer.java 9617 2006-03-14 23:47:37Z steve.ebersole@jboss.com $
package org.hibernate.tuple;

import java.lang.reflect.Method;
import java.io.Serializable;
import java.util.HashMap;

import net.sf.cglib.beans.BulkBean;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.proxy.Factory;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.NoOp;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.proxy.Callback;

import org.hibernate.HibernateException;
import org.hibernate.PropertyAccessException;
import org.hibernate.AssertionFailure;
import org.hibernate.cfg.Environment;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.mapping.Component;
import org.hibernate.mapping.Property;
import org.hibernate.property.Getter;
import org.hibernate.property.PropertyAccessor;
import org.hibernate.property.PropertyAccessorFactory;
import org.hibernate.property.Setter;
import org.hibernate.util.ReflectHelper;

/**
 * @author Gavin King
 */
public class PojoComponentTuplizer extends AbstractComponentTuplizer {
	
	private final Class componentClass;
	private transient BulkBean optimizer;
	private transient FastClass fastClass;
	private final Getter parentGetter;
	private final Setter parentSetter;

	public PojoComponentTuplizer(Component component) {
		super( component );

		this.componentClass = component.getComponentClass();

		String[] getterNames = new String[propertySpan];
		String[] setterNames = new String[propertySpan];
		Class[] propTypes = new Class[propertySpan];
		for ( int i = 0; i < propertySpan; i++ ) {
			getterNames[i] = getters[i].getMethodName();
			setterNames[i] = setters[i].getMethodName();
			propTypes[i] = getters[i].getReturnType();
		}

		final String parentPropertyName = component.getParentProperty();
		if ( parentPropertyName == null ) {
			parentSetter = null;
			parentGetter = null;
		}
		else {
			PropertyAccessor pa = PropertyAccessorFactory.getPropertyAccessor( null );
			parentSetter = pa.getSetter( componentClass, parentPropertyName );
			parentGetter = pa.getGetter( componentClass, parentPropertyName );
		}

		if ( hasCustomAccessors || !Environment.useReflectionOptimizer() ) {
			fastClass = null;
			optimizer = null;
		}
		else {
			fastClass = ReflectHelper.getFastClass( componentClass );
			optimizer = ReflectHelper.getBulkBean( componentClass, getterNames, setterNames, propTypes, fastClass );
			if (optimizer==null) fastClass = null;
		}

	}

	public Class getMappedClass() {
		return componentClass;
	}
	
	public Object[] getPropertyValues(Object component) throws HibernateException {
		if ( optimizer != null ) {
			try {
				return optimizer.getPropertyValues( component );
			}
			catch ( Throwable t ) {
				throw new PropertyAccessException( t,
						ReflectHelper.PROPERTY_GET_EXCEPTION,
						false,
						componentClass,
						ReflectHelper.getPropertyName( t, optimizer ) 
					);
			}
		}
		else {
			return super.getPropertyValues(component);
		}
	}

	public void setPropertyValues(Object component, Object[] values)
			throws HibernateException {

		if ( optimizer != null ) {
			try {
				optimizer.setPropertyValues( component, values );
				return;
			}
			catch ( Throwable t ) {
				throw new PropertyAccessException( t,
						ReflectHelper.PROPERTY_SET_EXCEPTION,
						true,
						componentClass,
						ReflectHelper.getPropertyName( t, optimizer ) 
					);
			}
		}
		else {
			super.setPropertyValues(component, values);
		}

	}
	
	public Object getParent(Object component) {
		return parentGetter.get( component );
	}
	
	public boolean hasParentProperty() {
		return parentGetter!=null;
	}
	
	public boolean isMethodOf(Method method) {
		for ( int i=0; i<propertySpan; i++ ) {
			final Method getterMethod = getters[i].getMethod();
			if ( getterMethod!=null && getterMethod.equals(method) ) return true;
		}
		return false;
	}
	
	public void setParent(Object component, Object parent, SessionFactoryImplementor factory) {
		parentSetter.set(component, parent, factory);
	}
	
	protected Instantiator buildInstantiator(Component component) {
		if ( component.isEmbedded() && ReflectHelper.isAbstractClass( component.getComponentClass() ) ) {
			return new ProxiedInstantiator( component );
		}
		else {
			return new PojoInstantiator( component, fastClass );
		}
	}

	protected Getter buildGetter(Component component, Property prop) {
		return prop.getGetter( component.getComponentClass() );
	}

	protected Setter buildSetter(Component component, Property prop) {
		return prop.getSetter( component.getComponentClass() );
	}

	private static class ProxiedInstantiator implements Instantiator {
		private final Class proxiedClass;
		private final Class proxyClass;
		private final Factory factory;

		public ProxiedInstantiator(Component component) {
			proxiedClass = component.getComponentClass();
			proxyClass = buildProxyClass();
			factory = buildFactory();
		}

		public Object instantiate(Serializable id) {
			throw new AssertionFailure( "ProxiedInstantiator can only be used to instantiate component" );
		}

		public Object instantiate() {
			try {
				return factory.newInstance(
						new Callback[] { new PassThroughInterceptor( proxyClass.getName() ), NoOp.INSTANCE }
				);
			}
			catch ( Throwable t ) {
				throw new HibernateException( "Unable to instantiate proxy instance" );
			}
		}

		public boolean isInstance(Object object) {
			return proxiedClass.isInstance( object );
		}

		private Class buildProxyClass() {
			Enhancer en = new Enhancer();
			en.setUseCache( false );
			en.setInterceptDuringConstruction( false );
			en.setUseFactory( true );
			en.setCallbackTypes( CALLBACK_TYPES );
			en.setCallbackFilter( FINALIZE_FILTER );
			if ( proxiedClass.isInterface() ) {
				en.setInterfaces( new Class[] { proxiedClass } );
			}
			else {
				en.setSuperclass( proxiedClass );
			}
			return en.createClass();

		}
		private Factory buildFactory() {
			try {
				return ( Factory ) proxyClass.newInstance();
			}
			catch ( Throwable t ) {
				throw new HibernateException( "Unable to build CGLIB Factory instance" );
			}
		}
	}

	private static final CallbackFilter FINALIZE_FILTER = new CallbackFilter() {
		public int accept(Method method) {
			if ( method.getParameterTypes().length == 0 && method.getName().equals("finalize") ){
				return 1;
			}
			else {
				return 0;
			}
		}
	};

	private static final Class[] CALLBACK_TYPES = new Class[] { MethodInterceptor.class, NoOp.class };

	private static class PassThroughInterceptor implements MethodInterceptor {
		private HashMap data = new HashMap();
		private final String proxiedClassName;

		public PassThroughInterceptor(String proxiedClassName) {
			this.proxiedClassName = proxiedClassName;
		}

		public Object intercept(
				Object obj,
		        Method method,
		        Object[] args,
		        MethodProxy proxy) throws Throwable {
			String name = method.getName();
			if ( "toString".equals( name ) ) {
				return proxiedClassName + "@" + System.identityHashCode( obj );
			}
			else if ( "equals".equals( name ) ) {
				return args[0] instanceof Factory && ( ( Factory ) args[0] ).getCallback( 0 ) == this
						? Boolean.TRUE
			            : Boolean.FALSE;
			}
			else if ( "hashCode".equals( name ) ) {
				return new Integer( System.identityHashCode( obj ) );
			}
			boolean hasGetterSignature = method.getParameterTypes().length == 0 && method.getReturnType() != null;
			boolean hasSetterSignature = method.getParameterTypes().length == 1 && ( method.getReturnType() == null || method.getReturnType() == void.class );
			if ( name.startsWith( "get" ) && hasGetterSignature ) {
				String propName = name.substring( 3 );
				return data.get( propName );
			}
			else if ( name.startsWith( "is" ) && hasGetterSignature ) {
				String propName = name.substring( 2 );
				return data.get( propName );
			}
			else if ( name.startsWith( "set" ) && hasSetterSignature) {
				String propName = name.substring( 3 );
				data.put( propName, args[0] );
				return null;
			}
			else {
				// todo : what else to do here?
				return null;
			}
		}
	}
}
