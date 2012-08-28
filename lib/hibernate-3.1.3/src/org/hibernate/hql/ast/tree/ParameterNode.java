// $Id: ParameterNode.java 8513 2005-11-02 18:47:40Z steveebersole $
package org.hibernate.hql.ast.tree;

import org.hibernate.param.ParameterSpecification;

/**
 * Implementation of ParameterNode.
 *
 * @author Steve Ebersole
 */
public class ParameterNode extends HqlSqlWalkerNode implements DisplayableNode {
	private ParameterSpecification parameterSpecification;

	public ParameterSpecification getHqlParameterSpecification() {
		return parameterSpecification;
	}

	public void setHqlParameterSpecification(ParameterSpecification parameterSpecification) {
		this.parameterSpecification = parameterSpecification;
	}

	public String getDisplayText() {
		return "{" + ( parameterSpecification == null ? "???" : parameterSpecification.renderDisplayInfo() ) + "}";
	}
}
