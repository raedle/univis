//$Id: SecondPass.java 8066 2005-09-01 23:29:42Z epbernard $
package org.hibernate.cfg;

import java.io.Serializable;

import org.hibernate.MappingException;

/**
 * Second pass operation
 *
 * @author Emmanuel Bernard
 */
public interface SecondPass extends Serializable {

	void doSecondPass(java.util.Map persistentClasses, java.util.Map inheritedMetas)
				throws MappingException;

}
