package org.hibernate.cfg;

import javax.persistence.Column;
import javax.persistence.JoinColumn;

import org.hibernate.mapping.KeyValue;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.Table;

/**
 * Property holder abstract property containers from their direct implementation
 *
 * @author Emmanuel Bernard
 */
public interface PropertyHolder {
	String getClassName();

	Table getTable();

	void addProperty(Property prop);

	KeyValue getIdentifier();

	PersistentClass getPersistentClass();

	boolean isComponent();

	String getPath();

	/**
	 * return null if the column is not overridden, or an array of column if true
	 */
	Column[] getOverriddenColumn(String propertyName);

	/**
	 * return null if the column is not overridden, or an array of column if true
	 */
	JoinColumn[] getOverriddenJoinColumn(String propertyName);

	String getEntityName();

	void addProperty(Property prop, Ejb3Column[] columns);
}
