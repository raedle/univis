//$Id: NotYetImplementedException.java 8071 2005-09-02 15:22:29Z epbernard $
package org.hibernate.cfg;

import org.hibernate.MappingException;

/**
 * Mapping not yet implemented
 *
 * @author Emmanuel Bernard
 */
public class NotYetImplementedException extends MappingException {

	public NotYetImplementedException(String msg, Throwable root) {
		super( msg, root );
	}

	public NotYetImplementedException(Throwable root) {
		super( root );
	}

	public NotYetImplementedException(String s) {
		super( s );
	}

}
