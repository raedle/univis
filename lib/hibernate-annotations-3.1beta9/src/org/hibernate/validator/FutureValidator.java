//$Id: FutureValidator.java 8588 2005-11-16 18:34:08Z epbernard $
package org.hibernate.validator;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Check that a given date is in the future, and apply the same restriction
 * at the DB level
 *
 * @author Emmanuel Bernard
 */
public class FutureValidator implements Validator<Future>, Serializable {

	public void initialize(Future parameters) {
	}

	public boolean isValid(Object value) {
		if ( value == null ) return true;
		if ( value instanceof String ) {
			try {
				Date date = DateFormat.getTimeInstance().parse( (String) value );
				return date.after( new Date() );
			}
			catch (ParseException nfe) {
				return false;
			}
		}
		else if ( value instanceof Date ) {
			Date date = (Date) value;
			return date.after( new Date() );
		}
		else if ( value instanceof Calendar ) {
			Calendar cal = (Calendar) value;
			return cal.after( Calendar.getInstance() );
		}
		else {
			return false;
		}
	}
}
