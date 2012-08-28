//$Id: Pattern.java 8587 2005-11-16 18:05:00Z epbernard $
package org.hibernate.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 * The annotated element must follow the regexp pattern
 *
 * @author Gavin King
 */
@Documented
@ValidatorClass(PatternValidator.class)
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface Pattern {
	/** regular expression */
	String regex();

	/** regular expression processing flags */
	int flags() default 0;

	String message() default "{validator.pattern}";
}
