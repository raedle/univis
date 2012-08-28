//$Id: HibernateProxy.java 4453 2004-08-29 07:31:03Z oneovthafew $
package org.hibernate.proxy;

import java.io.Serializable;

/**
 * Marker interface for entity proxies
 * @author Gavin King
 */
public interface HibernateProxy extends Serializable {
	public Object writeReplace();
	public LazyInitializer getHibernateLazyInitializer();
}







