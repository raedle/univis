//$Id: Valid.java 7942 2005-08-18 01:28:33Z oneovthafew $
package org.hibernate.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 * Enables recursive validation of an associated object 
 *
 * @author Gavin King
 */
@Documented
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface Valid {}
