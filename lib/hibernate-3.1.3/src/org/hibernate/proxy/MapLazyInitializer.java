//$Id: MapLazyInitializer.java 5685 2005-02-12 07:19:50Z steveebersole $
package org.hibernate.proxy;

import java.io.Serializable;
import java.util.Map;

import org.hibernate.engine.SessionImplementor;

/**
 * Lazy initializer for "dynamic-map" entity representations.
 *
 * @author Gavin King
 */
public class MapLazyInitializer extends AbstractLazyInitializer implements Serializable {

	MapLazyInitializer(String entityName, Serializable id, SessionImplementor session) {
		super(entityName, id, session);
	}

	public Map getMap() {
		return (Map) getImplementation();
	}

	public Class getPersistentClass() {
		throw new UnsupportedOperationException("dynamic-map entity representation");
	}

}
