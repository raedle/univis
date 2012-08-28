//$Id: CustomLoader.java 9162 2006-01-27 23:40:32Z steveebersole $
package org.hibernate.loader.custom;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.QueryException;
import org.hibernate.ScrollableResults;
import org.hibernate.engine.QueryParameters;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.loader.CollectionAliases;
import org.hibernate.loader.EntityAliases;
import org.hibernate.loader.Loader;
import org.hibernate.persister.collection.CollectionPersister;
import org.hibernate.persister.entity.Loadable;
import org.hibernate.type.Type;
import org.hibernate.type.TypeFactory;
import org.hibernate.util.ArrayHelper;

/**
 * Extension point for loaders which use a SQL
 * result set with "unexpected" column aliases.
 * 
 * @author Gavin King
 */
public class CustomLoader extends Loader {
	
	private final Type[] resultTypes;	
	private final Loadable[] persisters;
	private final CollectionPersister[] collectionPersisters;
	private final CustomQuery customQuery;
	private Type[] discoveredTypes;
	private String[] discoveredColumnAliases;
	private ResultSetMetaData metaData;

	public CustomLoader(
			CustomQuery customQuery,
			SessionFactoryImplementor factory) {
		super(factory);
		this.customQuery = customQuery;
		
		String[] collectionRoles = customQuery.getCollectionRoles();
		if ( collectionRoles==null ) {
			collectionPersisters = null;
		}
		else {
			int length = collectionRoles.length;
			collectionPersisters = new CollectionPersister[length];
			for ( int i=0; i<length; i++ ) {
				collectionPersisters[i] = factory.getCollectionPersister( collectionRoles[i] );
			}
		}
		
		String[] entityNames = customQuery.getEntityNames();
		persisters = new Loadable[entityNames.length];
		for ( int i=0; i<entityNames.length; i++ ) {
			persisters[i] = (Loadable) factory.getEntityPersister( entityNames[i] );
		}
		
		Type[] scalarTypes = customQuery.getScalarTypes();
		
		resultTypes = new Type[ entityNames.length + ( scalarTypes==null ? 0 : scalarTypes.length ) ];
		System.arraycopy( scalarTypes, 0, resultTypes, 0, scalarTypes.length );
		for ( int i=0; i<entityNames.length; i++ ) {
			resultTypes[i + scalarTypes.length] = TypeFactory.manyToOne( entityNames[i] );
		}
	}

	protected String getSQLString() {
		return customQuery.getSQL();
	}

	protected Loadable[] getEntityPersisters() {
		return persisters;
	}

	protected LockMode[] getLockModes(Map lockModesMap) {
		return customQuery.getLockModes();
	}

	protected CollectionPersister[] getCollectionPersisters() {
		return collectionPersisters;
	}

	protected int[] getCollectionOwners() {
		return customQuery.getCollectionOwner();
	}
	
	protected int[] getOwners() {
		return customQuery.getEntityOwners();
	}
	
	public Set getQuerySpaces() {
		return customQuery.getQuerySpaces();
	}
	
	protected String getQueryIdentifier() {
		return customQuery.getSQL();
	}

	public List list(
			SessionImplementor session,
	        QueryParameters queryParameters) throws HibernateException {
		return list(session, queryParameters, customQuery.getQuerySpaces(), resultTypes);
	}

	public ScrollableResults scroll(
			final QueryParameters queryParameters,
			final SessionImplementor session) throws HibernateException {
		return scroll(queryParameters, resultTypes, null, session);
	}
	
	protected void autoDiscoverTypes(ResultSet rs) {
		try {
			metaData = rs.getMetaData();
			
			Type[] scalarTypes = customQuery.getScalarTypes();
			if ( scalarTypes.length == 0 ) {
				// no types specifed, so auto-discover everything
				int columnCount = metaData.getColumnCount();
				discoveredTypes = new Type[columnCount];
				discoveredColumnAliases = new String[columnCount];
				for ( int i = 1; i <= columnCount; i++ ) {
					discoveredTypes[i-1] = getHibernateType( i );
					discoveredColumnAliases[i-1] = metaData.getColumnName(i);
				}
			}
			else {
				String[] scalarColumnAliases = customQuery.getScalarColumnAliases();
				discoveredTypes = new Type[scalarTypes.length];
				discoveredColumnAliases = new String[scalarTypes.length];
				for ( int i = 0; i < scalarTypes.length; i++ ) {
					discoveredColumnAliases[i] = scalarColumnAliases[i];
					if ( scalarTypes[i] == null ) {
						String alias = scalarColumnAliases[i];
						int column = rs.findColumn(alias);
						discoveredTypes[i] = getHibernateType( column );
					}
					else {
						discoveredTypes[i] = scalarTypes[i];		 													
					}
				}
			}
		}
		catch (SQLException e) {
			throw new HibernateException("Exception while trying to autodiscover types.", e);
		}
	}

	private Type getHibernateType(int columnPos) throws SQLException {
		int columnType = metaData.getColumnType( columnPos );
		int scale = metaData.getScale( columnPos );
		int precision = metaData.getPrecision( columnPos );
		return TypeFactory.heuristicType(
				getFactory().getDialect().getHibernateTypeName(
						columnType,
				        precision,
				        precision,
				        scale
				)
		);
	}
	
	protected Object getResultColumnOrRow(
			Object[] row,
	        ResultSet rs,
	        SessionImplementor session) throws SQLException, HibernateException {
		Type[] scalarTypes = discoveredTypes==null ? customQuery.getScalarTypes() : discoveredTypes;
		String[] scalarColumnAliases = discoveredColumnAliases==null ? customQuery.getScalarColumnAliases() : discoveredColumnAliases;

		final Object[] resultRow;
		if ( scalarTypes!=null && scalarTypes.length>0 ) {
			//all scalar results appear first
			resultRow = new Object[scalarTypes.length + row.length];
			for ( int i=0; i<scalarTypes.length; i++ ) {
				resultRow[i] = scalarTypes[i].nullSafeGet(rs, scalarColumnAliases[i], session, null);
			}
			//then entity results
			System.arraycopy(row, 0, resultRow, scalarTypes.length, row.length);
		}
		else {
			resultRow = row;
		}
		
		return resultRow.length == 1 ? resultRow[0] : resultRow;		
	}

//	protected Object getResultColumnOrRow(
//			Object[] row,
//	        ResultSet rs,
//	        SessionImplementor session) throws SQLException, HibernateException {
//		if ( persisters.length == 1 ) {
//			return row[row.length - 1];
//		}
//		else {
//			return row;
//		}
//	}

	protected EntityAliases[] getEntityAliases() {
		return customQuery.getEntityAliases();
	}

	protected CollectionAliases[] getCollectionAliases() {
		return customQuery.getCollectionAliases();
	}
	
	public int[] getNamedParameterLocs(String name) throws QueryException {
		Object loc = customQuery.getNamedParameterBindPoints().get(name);
		if (loc == null) {
			throw new QueryException(
					"Named parameter does not appear in Query: " + 
					name, 
					customQuery.getSQL()
			);
		}
		if (loc instanceof Integer) {
			return new int[] { ( (Integer) loc ).intValue() };
		}
		else {
			return ArrayHelper.toIntArray( (List) loc );
		}
	}

}
