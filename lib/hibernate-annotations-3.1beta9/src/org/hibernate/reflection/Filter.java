//$Id: Filter.java 9316 2006-02-22 20:47:31Z epbernard $
package org.hibernate.reflection;

/**
 * Filter properties
 *
 * @author Emmanuel Bernard
 */
public interface Filter {
	boolean returnStatic();
	boolean returnTransient();
}
