//$Id: Columns.java 6805 2005-05-16 17:43:09Z epbernard $
package org.hibernate.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import javax.persistence.Column;

/**
 * Support an array of columns. Useful for component user types mappings
 * @author Emmanuel Bernard
 */
@Target({METHOD, FIELD}) @Retention(RUNTIME)
public @interface Columns {
	Column[] columns();
}
