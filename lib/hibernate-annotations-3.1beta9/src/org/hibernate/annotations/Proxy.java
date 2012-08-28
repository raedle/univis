//$Id: Proxy.java 8107 2005-09-06 10:19:07Z epbernard $
package org.hibernate.annotations;

import static java.lang.annotation.ElementType.TYPE;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;

/**
 * Lazy and proxy configuration of a particular class
 *
 * @author Emmanuel Bernard
 */
@Target(TYPE) @Retention(RUNTIME)
public @interface Proxy {
	/**
	 * Whether this class is lazy or not (default to true)
	 */
	boolean lazy() default true;

	/**
	 * Proxy class or interface used. Default entity class name.
	 */
	Class proxyClass() default void.class;
}
