//$Id: MetaAttribute.java 4561 2004-09-17 05:22:17Z maxcsaucdk $
package org.hibernate.mapping;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A meta attribute is a named value or values.
 * @author Gavin King
 */
public class MetaAttribute implements Serializable {
	private String name;
	private java.util.List values = new ArrayList();

	public MetaAttribute(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}	

	public java.util.List getValues() {
		return Collections.unmodifiableList(values);
	}

	public void addValue(String value) {
		values.add(value);
	}

	public String getValue() {
		if ( values.size()!=1 ) throw new IllegalStateException("no unique value");
		return (String) values.get(0);
	}

	public boolean isMultiValued() {
		return values.size()>1;
	}

	public String toString() {
		return "[" + name + "=" + values + "]";
	}
}
