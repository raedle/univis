//$Id: AssertTrueValidator.java 8194 2005-09-18 23:26:49Z epbernard $
package org.hibernate.validator;

import java.io.Serializable;


/**
 * Check whether an element is true or not
 *
 * @author Gavin King
 */
public class AssertTrueValidator implements Validator<AssertTrue>, Serializable {

	public boolean isValid(Object value) {
		return (Boolean) value;
	}

	public void initialize(AssertTrue parameters) {
	}

}
