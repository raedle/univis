//$Id: FilterDefs.java 9218 2006-02-06 09:16:01Z maxcsaucdk $
package org.hibernate.annotations;

import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Array of filter definitions
 *
 * @author Matthew Inger
 * @author Emmanuel Bernard
 */
@Target({PACKAGE, TYPE}) @Retention(RUNTIME)
public @interface FilterDefs {
	FilterDef[] value();
}
