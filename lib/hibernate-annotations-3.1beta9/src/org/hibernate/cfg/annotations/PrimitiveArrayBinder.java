//$Id: PrimitiveArrayBinder.java 8071 2005-09-02 15:22:29Z epbernard $
package org.hibernate.cfg.annotations;

import org.hibernate.mapping.Collection;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.PrimitiveArray;

/**
 * @author Emmanuel Bernard
 */
public class PrimitiveArrayBinder extends ArrayBinder {
	@Override
	protected Collection createCollection(PersistentClass persistentClass) {
		return new PrimitiveArray( persistentClass );
	}
}
