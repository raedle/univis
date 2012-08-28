//$Id: XMethod.java 9569 2006-03-07 21:12:50Z nusco $
package org.hibernate.reflection;

/**
 * Represent an invokable method
 *
 * The underlying layer does not guaranty that xProperty == xMethod
 * if the underlying artefact is the same
 * However xProperty.equals(xMethod) is supposed to return true
 *
 * @author Emmanuel Bernard
 */
public interface XMethod extends XMember {}
