//$Id: Text.java 7885 2005-08-12 20:20:12Z oneovthafew $
package org.hibernate.lucene;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
/**
 * Specifies that a property of an entity is a Lucene
 * text field
 */
public @interface Text {
	/**
	 * The field name
	 */
	String name() default "";
}
