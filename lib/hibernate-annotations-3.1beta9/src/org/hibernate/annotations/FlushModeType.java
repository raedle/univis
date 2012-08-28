package org.hibernate.annotations;

/**
 * Enumeration extending javax.persistence flush modes.
 *
 * @author Carlos Gonz�lez-Cadenas
 */

public enum FlushModeType {
	/** see {@link org.hibernate.FlushMode.ALWAYS} */
	ALWAYS,
	/** see {@link org.hibernate.FlushMode.AUTO} */
	AUTO,
	/** see {@link org.hibernate.FlushMode.COMMIT} */
	COMMIT,
	/** see {@link org.hibernate.FlushMode.NEVER} */
	NEVER
}