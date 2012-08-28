//$Id: PostInsertIdentityPersister.java 5699 2005-02-13 11:50:11Z oneovthafew $
package org.hibernate.id;

import org.hibernate.persister.entity.EntityPersister;

/**
 * A persister that may have an identity assigned by execution of 
 * a SQL <tt>INSERT</tt>.
 * @author Gavin King
 */
public interface PostInsertIdentityPersister extends EntityPersister {
		
	public String getSelectByUniqueKeyString(String propertyName);
	public String getIdentitySelectString();
	public String[] getRootTableKeyColumnNames();
	
}
