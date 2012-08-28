//$Id: Sort.java 6803 2005-05-16 14:57:03Z epbernard $
package org.hibernate.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Collection sort
 * (Java level sorting)
 * @author Emmanuel Bernard
 */
@Target({METHOD, FIELD}) @Retention(RUNTIME)
public @interface Sort {
	/**
	 * sort type
	 */
	SortType type() default SortType.UNSORTED;
	/**
	 * Sort comparator implementation
	 */
	//TODO find a way to use Class<Comparator>
	Class comparator() default void.class;
}
