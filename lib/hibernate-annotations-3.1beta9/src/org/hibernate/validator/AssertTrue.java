//$Id: AssertTrue.java 8587 2005-11-16 18:05:00Z epbernard $
package org.hibernate.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 * The annotated element has to be true
 *
 * @author Gavin King
 */
@Documented
@ValidatorClass(AssertTrueValidator.class)
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface AssertTrue {
	String message() default "{validator.assertTrue}";
}
