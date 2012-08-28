//$Id: FilterDef.java 8768 2005-12-06 17:47:44Z epbernard $
package org.hibernate.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Filter definition
 *
 * @author Matthew Inger
 * @author Emmanuel Bernard
 */
@Target({TYPE,PACKAGE}) @Retention(RUNTIME)
public @interface FilterDef {
	String  name();
	String defaultCondition() default "";
	ParamDef[] parameters() default {};
}
