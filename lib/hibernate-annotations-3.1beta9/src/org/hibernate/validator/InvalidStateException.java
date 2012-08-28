//$Id: InvalidStateException.java 7852 2005-08-11 19:45:28Z epbernard $
package org.hibernate.validator;

/**
 * Thrown when the bean has violated one or several of its constraints
 * You can get the violation details in getInvalidValues()
 *
 * @author Gavin King
 */
public class InvalidStateException extends RuntimeException {

	private final InvalidValue[] invalidValues;

	public InvalidStateException(InvalidValue[] invalidValues) {
		this( invalidValues, invalidValues[0].getBeanClass().getName() );
	}

	public InvalidStateException(InvalidValue[] invalidValues, String className) {
		super( "validation failed for: " + className );
		this.invalidValues = invalidValues;
	}

	public InvalidValue[] getInvalidValues() {
		return invalidValues;
	}

}
