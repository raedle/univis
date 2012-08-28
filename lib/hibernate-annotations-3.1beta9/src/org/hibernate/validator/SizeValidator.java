//$Id: SizeValidator.java 8194 2005-09-18 23:26:49Z epbernard $
package org.hibernate.validator;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.io.Serializable;

/**
 * Check the size range according to the element
 *
 * @author Gavin King
 */
public class SizeValidator implements Validator<Size>, Serializable {
	private int max;
	private int min;

	public void initialize(Size parameters) {
		max = parameters.max();
		min = parameters.min();
	}

	public boolean isValid(Object value) {
		if ( value == null ) return true;
		int length;
		if ( value.getClass().isArray() ) {
			length = Array.getLength( value );
		}
		else if ( value instanceof Collection ) {
			length = ( (Collection) value ).size();
		}
		else if ( value instanceof Map ) {
			length = ( (Map) value ).size();
		}
		else {
			return false;
		}
		return length >= min && length <= max;
	}

}
