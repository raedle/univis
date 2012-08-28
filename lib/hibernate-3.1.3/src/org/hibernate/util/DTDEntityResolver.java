//$Id: DTDEntityResolver.java 9334 2006-02-24 16:57:32Z steveebersole $
//Contributed by Markus Meissner
package org.hibernate.util;

import java.io.InputStream;
import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

public class DTDEntityResolver implements EntityResolver, Serializable {

	private static final Log log = LogFactory.getLog( DTDEntityResolver.class );

	private static final String HIBERNATE_NAMESPACE = "http://hibernate.sourceforge.net/";
	private static final String LOCAL_NAMESPACE = "file://";

	public InputSource resolveEntity(String publicId, String systemId) {
		if ( systemId != null ) {
			log.debug( "trying to resolve system-id [" + systemId + "]" );
			if ( systemId.startsWith( HIBERNATE_NAMESPACE ) ) {
				log.debug( "recognized hibernate namespace; attempting to resolve on classpath under org/hibernate/" );
				String path = "org/hibernate/" + systemId.substring( HIBERNATE_NAMESPACE.length() );
				InputStream dtdStream = resolveInHibernateNamespace( path );
				if ( dtdStream == null ) {
					log.debug( "unable to locate [" + systemId + "] on classpath" );
					if ( systemId.substring( HIBERNATE_NAMESPACE.length() ).indexOf( "2.0" ) > -1 ) {
						log.error( "Don't use old DTDs, read the Hibernate 3.x Migration Guide!" );
					}
				}
				else {
					log.debug( "located [" + systemId + "] in classpath" );
					InputSource source = new InputSource( dtdStream );
					source.setPublicId( publicId );
					source.setSystemId( systemId );
					return source;
				}
			}
			else if ( systemId.startsWith( LOCAL_NAMESPACE ) ) {
				log.debug( "recognized local namespace; attempting to resolve on classpath" );
				String path = systemId.substring( LOCAL_NAMESPACE.length() );
				InputStream stream = resolveInLocalNamespace( path );
				if ( stream == null ) {
					log.debug( "unable to locate [" + systemId + "] on classpath" );
				}
				else {
					log.debug( "unable to locate [" + systemId + "] on classpath" );
					InputSource source = new InputSource( stream );
					source.setPublicId( publicId );
					source.setSystemId( systemId );
					return source;
				}
			}
		}
		// use default behavior
		return null;
	}

	protected InputStream resolveInHibernateNamespace(String path) {
		return this.getClass().getClassLoader().getResourceAsStream( path );
	}

	protected InputStream resolveInLocalNamespace(String path) {
		// attempt to find local stuff on the context classloader first...
		InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream( path );
		if ( stream == null ) {
			// next try our classloader
			stream = getClass().getClassLoader().getResourceAsStream( path );
		}
		return stream;
	}
}
