//$Id: ValidateEventListener.java 8194 2005-09-18 23:26:49Z epbernard $
package org.hibernate.validator.event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.io.Serializable;

import org.hibernate.AssertionFailure;
import org.hibernate.EntityMode;
import org.hibernate.cfg.Configuration;
import org.hibernate.event.Initializable;
import org.hibernate.event.PreInsertEvent;
import org.hibernate.event.PreInsertEventListener;
import org.hibernate.event.PreUpdateEvent;
import org.hibernate.event.PreUpdateEventListener;
import org.hibernate.mapping.Component;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.property.Getter;
import org.hibernate.property.PropertyAccessor;
import org.hibernate.property.PropertyAccessorFactory;
import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidStateException;
import org.hibernate.validator.InvalidValue;

/**
 * Before insert and update, executes the validator framework
 *
 * @author Emmanuel Bernard
 */
public class ValidateEventListener implements PreInsertEventListener, PreUpdateEventListener, Initializable {
	private boolean isInitialized;
	private Map<Class, ValidatableElement> validators = new HashMap<Class, ValidatableElement>();

	/**
	 * initialize the validators, any non significant validators are not kept
	 */
	@SuppressWarnings("unchecked")
	public synchronized void initialize(final Configuration cfg) {
		if ( !isInitialized ) {
			Iterator<PersistentClass> classes = (Iterator<PersistentClass>) cfg.getClassMappings();
			while ( classes.hasNext() ) {
				PersistentClass clazz = classes.next();
				final Class mappedClass = clazz.getMappedClass();
				ClassValidator validator = new ClassValidator( mappedClass );
				ValidatableElement element = new ValidatableElement( mappedClass, validator );
				addSubElement( clazz.getIdentifierProperty(), element );
				Iterator properties = clazz.getPropertyIterator();
				while ( properties.hasNext() ) {
					addSubElement( (Property) properties.next(), element );
				}
				if ( element.subElements.size() != 0 || element.validator.hasValidationRules() ) {
					validators.put( mappedClass, element );
				}
			}
			isInitialized = true;
		}
	}

	@SuppressWarnings("unchecked")
	private void addSubElement(Property property, ValidatableElement element) {
		if ( property != null && property.isComposite() && ! property.isBackRef() ) {
			Component component = (Component) property.getValue();
			if ( component.isEmbedded() ) return;
			PropertyAccessor accessor = PropertyAccessorFactory.getPropertyAccessor( property, EntityMode.POJO );
			Getter getter = accessor.getGetter( element.clazz, property.getName() );
			ClassValidator validator = new ClassValidator( getter.getReturnType() );
			ValidatableElement subElement = new ValidatableElement( getter.getReturnType(), validator, getter );
			Iterator properties = component.getPropertyIterator();
			while ( properties.hasNext() ) {
				addSubElement( (Property) properties.next(), subElement );
			}
			if ( subElement.getSubElements().size() != 0 || subElement.validator.hasValidationRules() ) {
				element.addSubElement( subElement );
			}
		}
	}

	@SuppressWarnings("unchecked")
	protected void validate(Object entity, EntityMode mode) {
		if ( entity == null || ! EntityMode.POJO.equals( mode ) ) return;
		ValidatableElement element;
		if ( isInitialized ) {
			element = validators.get( entity.getClass() );
		}
		else {
			throw new AssertionFailure( "Validator event not initialized" );
		}
		if (element == null) return; //no validation to do
		List<InvalidValue> consolidatedInvalidValues = new ArrayList<InvalidValue>();
		validateSubElements( element, entity, consolidatedInvalidValues );
		consolidatedInvalidValues.size();
		InvalidValue[] invalidValues = element.validator == null ?
				null :
				element.validator.getInvalidValues( entity );
		for ( InvalidValue invalidValue : invalidValues ) {
			consolidatedInvalidValues.add( invalidValue );
		}
		if ( consolidatedInvalidValues.size() > 0 ) {
			throw new InvalidStateException(
					consolidatedInvalidValues.toArray( new InvalidValue[]{} ), entity.getClass().getName()
				);
		}
	}

	@SuppressWarnings("unchecked")
	private void validateSubElements(
			ValidatableElement element, Object entity, List<InvalidValue> consolidatedInvalidValues
	) {
		if (element != null) {
			for ( ValidatableElement subElement : element.subElements ) {
				Object component = subElement.getter.get( entity );
				InvalidValue[] invalidValues = subElement.validator.getInvalidValues( component );
				for ( InvalidValue invalidValue : invalidValues ) {
					consolidatedInvalidValues.add( invalidValue );
				}
				validateSubElements( subElement, component, consolidatedInvalidValues );
			}
		}
	}

	public boolean onPreInsert(PreInsertEvent event) {
		validate( event.getEntity(), event.getSource().getEntityMode() );
		return false;
	}

	public boolean onPreUpdate(PreUpdateEvent event) {
		validate( event.getEntity(), event.getSource().getEntityMode() );
		return false;
	}

	private static class ValidatableElement implements Serializable {
		private Class clazz;
		private ClassValidator validator;
		private Getter getter;
		private Collection<ValidatableElement> subElements = new ArrayList<ValidatableElement>();

		public ValidatableElement(Class clazz, ClassValidator validator) {
			this.clazz = clazz;
			this.validator = validator;
		}

		public ValidatableElement(Class clazz, ClassValidator validator, Getter getter) {
			this( clazz, validator );
			this.getter = getter;
		}

		public void addSubElement(ValidatableElement subValidatableElement) {
			subElements.add( subValidatableElement );
		}

		public Collection<ValidatableElement> getSubElements() {
			return this.subElements;
		}
	}
}
