//$Id: Filter.java 8768 2005-12-06 17:47:44Z epbernard $
package org.hibernate.annotations;

import static java.lang.annotation.ElementType.TYPE;

import static java.lang.annotation.ElementType.METHOD;

import static java.lang.annotation.ElementType.FIELD;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;

/**
 * Add filters to an entity or a collection
 *
 * @author Emmanuel Bernard
 * @author Matthew Inger
 * @author Magnus Sandberg
 */
@Target({TYPE, METHOD, FIELD}) @Retention(RUNTIME)
public @interface Filter {
	String name();
	String condition() default "";
}
