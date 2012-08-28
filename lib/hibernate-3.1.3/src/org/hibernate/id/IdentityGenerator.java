//$Id: IdentityGenerator.java 6112 2005-03-17 21:09:13Z oneovthafew $
package org.hibernate.id;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.engine.SessionImplementor;


/**
 * The IdentityGenerator for autoincrement/identity key generation.
 * <br><br>
 * Indicates to the <tt>Session</tt> that identity (ie. identity/autoincrement
 * column) key generation should be used.
 *
 * @author Christoph Sturm
 */
public class IdentityGenerator extends AbstractPostInsertGenerator {

	protected String getSQL(PostInsertIdentityPersister persister) {
		return persister.getIdentitySelectString();
	}
	
	protected Serializable getResult(SessionImplementor session, ResultSet rs, Object object, PostInsertIdentityPersister persister) 
	throws SQLException {
		return IdentifierGeneratorFactory.getGeneratedIdentity( rs, persister.getIdentifierType() );
	}
}






