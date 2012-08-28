//$Id: Type.java 6357 2005-04-06 23:41:13Z epbernard $
package org.hibernate.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;

/**
 * hibernate type
 * @author Emmanuel Bernard
 */
@Target({FIELD,METHOD}) @Retention(RUNTIME)
public @interface Type {
	String type();
	Parameter[] parameters() default {};
}