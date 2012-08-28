//$Id: ParamDef.java 6328 2005-04-03 23:37:29Z epbernard $
package org.hibernate.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * A parameter definition
 *
 * @author Emmanuel Bernard
 */
@Target({}) @Retention(RUNTIME)
public @interface ParamDef {
	String name();
	String type();
}
