//$Id: AssertFalseValidator.java 8194 2005-09-18 23:26:49Z epbernard $
package org.hibernate.validator;

import java.io.Serializable;


/**
 * Check if a given object is false or not
 *
 * @author Gavin King
 */
public class AssertFalseValidator implements Validator<AssertFalse>, Serializable {

	public boolean isValid(Object value) {
		return !(Boolean) value;
	}

	public void initialize(AssertFalse parameters) {
	}

}
