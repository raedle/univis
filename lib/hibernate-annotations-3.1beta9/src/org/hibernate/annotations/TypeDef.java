//$Id: TypeDef.java 6705 2005-05-05 09:49:04Z epbernard $
package org.hibernate.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Type definition
 *
 * @author Emmanuel Bernard
 */
@Target({TYPE,PACKAGE}) @Retention(RUNTIME)
public @interface TypeDef {
	String  name();
	Class typeClass();
	Parameter[] parameters() default {};
}
