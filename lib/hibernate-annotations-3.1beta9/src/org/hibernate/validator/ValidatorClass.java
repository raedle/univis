//$Id: ValidatorClass.java 7884 2005-08-12 20:06:24Z oneovthafew $
package org.hibernate.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;


/**
 * Link between an constraint annotation and it's validator implementation
 *
 * @author Gavin King
 */
@Documented
@Target({ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface ValidatorClass {
	Class<? extends Validator> value();
}
