//$Id: BatchSize.java 5922 2005-02-26 00:57:39Z epbernard $
package org.hibernate.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.FIELD;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;

/**
 * Batch size for SQL loading
 *
 * @author Emmanuel Bernard
 */
@Target({TYPE, METHOD, FIELD}) @Retention(RUNTIME)
public @interface BatchSize {
	/** Strictly positive integer */
	int size();
}
