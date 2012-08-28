//$Id: PrimitiveCharacterArrayClobType.java 7786 2005-08-08 23:35:02Z oneovthafew $
package org.hibernate.type;


/**
 * Map a char[] to a Clob
 *
 * @author Emmanuel Bernard
 */
public class PrimitiveCharacterArrayClobType extends CharacterArrayClobType {
	public Class returnedClass() {
		return char[].class;
	}
}
