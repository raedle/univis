//$Id: Filters.java 6290 2005-04-02 22:50:48Z epbernard $
package org.hibernate.annotations;

import static java.lang.annotation.ElementType.TYPE;

import static java.lang.annotation.ElementType.METHOD;

import static java.lang.annotation.ElementType.FIELD;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;

/**
 * Add multiple filters to an entity or a collection
 * 
 * @author Emmanuel Bernard
 * @author Matthew Inger
 * @author Magnus Sandberg
 */
@Target({TYPE, METHOD, FIELD}) @Retention(RUNTIME)
public @interface Filters {
	Filter[] value();
}
