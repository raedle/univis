//$Id: Table.java 9609 2006-03-12 21:45:27Z epbernard $
package org.hibernate.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Complementary information to a table either primary or secondary
 *
 * @author Emmanuel Bernard
 */
@Target({TYPE}) @Retention(RUNTIME)
public @interface Table {
	/**
	 * name of the targeted table
	 */
	String appliesTo();

	/**
	 * Indexes
	 */
	Index[] indexes() default {};

}
