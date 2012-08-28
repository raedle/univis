//$Id: Indexed.java 7884 2005-08-12 20:06:24Z oneovthafew $
package org.hibernate.lucene;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
/**
 * Specifies that an entity is to be indexed by Lucene
 */
public @interface Indexed {
	/**
	 * The filename of the index
	 */
	String index() default "";
}
