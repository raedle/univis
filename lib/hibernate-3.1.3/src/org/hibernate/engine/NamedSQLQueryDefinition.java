//$Id: NamedSQLQueryDefinition.java 9258 2006-02-10 08:03:49Z maxcsaucdk $
package org.hibernate.engine;

import java.util.List;
import java.util.Map;

import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.loader.custom.SQLQueryReturn;
import org.hibernate.loader.custom.SQLQueryScalarReturn;

/**
 * Definition of a named native SQL query, defined
 * in the mapping metadata.
 * 
 * @author Max Andersen
 */
public class NamedSQLQueryDefinition extends NamedQueryDefinition {

	private SQLQueryReturn[] queryReturns;
	private SQLQueryScalarReturn[] scalarReturns;
	private final List querySpaces;
	private final boolean callable;
	private String resultSetRef;

	/** backward compatibility: to be removed after HA 3.2beta5*/
	public NamedSQLQueryDefinition(
		String query,
		SQLQueryReturn[] queryReturns,
		SQLQueryScalarReturn[] scalarReturns,
		List querySpaces,
		boolean cacheable,
		String cacheRegion,
		Integer timeout,
		Integer fetchSize,
		FlushMode flushMode,
		Map parameterTypes,
		boolean callable
	) {
		this(
				query,
				queryReturns,
				scalarReturns,
				querySpaces,
				cacheable,
				cacheRegion,
				timeout,
				fetchSize,
				flushMode,
				null,
				false,
				null,
				parameterTypes,
				callable
		);
	}

	public NamedSQLQueryDefinition(
		String query,
		SQLQueryReturn[] queryReturns,
		SQLQueryScalarReturn[] scalarReturns,
		List querySpaces,
		boolean cacheable, 
		String cacheRegion,
		Integer timeout,
		Integer fetchSize,
		FlushMode flushMode,
		CacheMode cacheMode,
		boolean readOnly,
		String comment,
		Map parameterTypes,
		boolean callable
	) {
		super(
				query.trim(), /* trim done to workaround stupid oracle bug that cant handle whitespaces before a { in a sp */
				cacheable,
				cacheRegion,
				timeout,
				fetchSize,
				flushMode,
				cacheMode,
				readOnly,
				comment,
				parameterTypes
		);
		this.queryReturns = queryReturns;
		this.scalarReturns = scalarReturns;
		this.querySpaces = querySpaces;
		this.callable = callable;
	}

	public NamedSQLQueryDefinition(
		String query,
		String resultSetRef,
		List querySpaces,
		boolean cacheable,
		String cacheRegion,
		Integer timeout,
		Integer fetchSize,
		FlushMode flushMode,
		Map parameterTypes,
		boolean callable
	) {
		this(
				query,
				resultSetRef,
				querySpaces,
				cacheable,
				cacheRegion,
				timeout,
				fetchSize,
				flushMode,
				null,
				false,
				null,
				parameterTypes,
				callable
		);
	}

	public NamedSQLQueryDefinition(
		String query,
		String resultSetRef,
		List querySpaces,
		boolean cacheable,
		String cacheRegion,
		Integer timeout,
		Integer fetchSize,
		FlushMode flushMode,
		CacheMode cacheMode,
		boolean readOnly,
		String comment,
		Map parameterTypes,
		boolean callable
	) {
		super(
				query.trim(), /* trim done to workaround stupid oracle bug that cant handle whitespaces before a { in a sp */
				cacheable,
				cacheRegion,
				timeout,
				fetchSize,
				flushMode,
				cacheMode,
				readOnly,
				comment,
				parameterTypes
		);
		this.resultSetRef = resultSetRef;
		this.querySpaces = querySpaces;
		this.callable = callable;
	}

	public SQLQueryReturn[] getQueryReturns() {
		return queryReturns;
	}

	public SQLQueryScalarReturn[] getScalarQueryReturns() {
		return scalarReturns;
	}

	public List getQuerySpaces() {
		return querySpaces;
	}

	public boolean isCallable() {
		return callable;
	}

	public String getResultSetRef() {
		return resultSetRef;
	}
}