//$Id: DiscriminatorFormula.java 7165 2005-06-16 19:07:15Z epbernard $
package org.hibernate.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Discriminator formula
 * To be placed at the root entity.
 *
 * @see Formula
 * @author Emmanuel Bernard
 */
@Target({TYPE}) @Retention(RUNTIME)
public @interface DiscriminatorFormula {
	String value();
}
