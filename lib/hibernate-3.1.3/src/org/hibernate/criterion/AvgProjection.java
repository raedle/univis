//$Id: AvgProjection.java 5684 2005-02-12 04:36:23Z oneovthafew $
package org.hibernate.criterion;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.type.Type;

/**
 * @author Gavin King
 */
public class AvgProjection extends AggregateProjection {

	public AvgProjection(String propertyName) {
		super("avg", propertyName);
	}
	
	public Type[] getTypes(Criteria criteria, CriteriaQuery criteriaQuery)
	throws HibernateException {
		return new Type[] { Hibernate.DOUBLE };
	}
}
