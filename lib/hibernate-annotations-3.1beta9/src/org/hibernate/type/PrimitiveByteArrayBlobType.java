//$Id: PrimitiveByteArrayBlobType.java 7642 2005-07-25 06:43:41Z epbernard $
package org.hibernate.type;

/**
 * Map a byte[] to a Blob
 *
 * @author Emmanuel Bernard
 */
public class PrimitiveByteArrayBlobType extends ByteArrayBlobType {
	public Class getReturnedClass() {
		return byte[].class;
	}

	protected Object wrap(byte[] bytes) {
		return bytes;
	}

	protected byte[] unWrap(Object bytes) {
		return ( byte[] ) bytes;
	}
}
