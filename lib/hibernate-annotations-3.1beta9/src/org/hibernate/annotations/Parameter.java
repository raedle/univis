//$Id: Parameter.java 6328 2005-04-03 23:37:29Z epbernard $
package org.hibernate.annotations;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;

/**
 * Parameter (basically key/value pattern)
 * 
 * @author Emmanuel Bernard
 */
@Target({}) @Retention(RUNTIME)
public @interface Parameter {
	String name();
	String value();
}
