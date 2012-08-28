//$Id: PersistentClassConstraint.java 7852 2005-08-11 19:45:28Z epbernard $
package org.hibernate.validator;

import org.hibernate.mapping.PersistentClass;

/**
 * Interface implemented by the validator
 * when a constraint may be represented in the
 * hibernate metadata
 *
 * @author Gavin King
 */
public interface PersistentClassConstraint {
	/**
	 * Apply the constraint in the hibernate metadata
	 *
	 * @param persistentClass
	 */
	public void apply(PersistentClass persistentClass);
}
