//$Id: AssertFalse.java 8587 2005-11-16 18:05:00Z epbernard $
package org.hibernate.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 * The annotated property has to be false.
 *
 * @author Gavin King
 */
@Documented
@ValidatorClass(AssertFalseValidator.class)
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface AssertFalse {
	String message() default "{validator.assertFalse}";
}
