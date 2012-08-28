//$Id: Email.java 8926 2005-12-26 14:07:33Z epbernard $
package org.hibernate.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.FIELD;

/**
 * The string has to be a well-formed email address
 * @author Emmanuel Bernard
 */
@Documented
@ValidatorClass(EmailValidator.class)
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface Email {
	String message() default "{validator.email}";
}
