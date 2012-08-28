//$Id: TypeDefs.java 6612 2005-04-29 18:49:20Z oneovthafew $
package org.hibernate.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.PACKAGE;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;

/**
 * Type definition array
 * @author Emmanuel Bernard
 */
@Target({TYPE, PACKAGE}) @Retention(RUNTIME)
public @interface TypeDefs {
	TypeDef[] value();
}
