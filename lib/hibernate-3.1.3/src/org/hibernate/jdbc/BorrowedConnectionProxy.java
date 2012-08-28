package org.hibernate.jdbc;

import org.hibernate.HibernateException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;

/**
 * A proxy for <i>borrowed</i> connections which funnels all requests back
 * into the ConnectionManager from which it was borrowed to be properly
 * handled (in terms of connection release modes).
 * <p/>
 * Note: the term borrowed here refers to connection references obtained
 * via {@link org.hibernate.Session#connection()} for application usage.
 *
 * @author Steve Ebersole
 */
public class BorrowedConnectionProxy implements InvocationHandler {

	private final ConnectionManager connectionManager;
	private boolean useable = true;

	public BorrowedConnectionProxy(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if ( "close".equals( method.getName() ) ) {
			connectionManager.releaseBorrowedConnection();
			return null;
		}
		// should probably no-op commit/rollback here, at least in JTA scenarios
		if ( !useable ) {
			throw new HibernateException( "connnection proxy not usable after transaction completion" );
		}
		try {
			return method.invoke( connectionManager.getConnection(), args );
		}
		catch( InvocationTargetException e ) {
			throw e.getTargetException();
		}
	}

	public static Connection generateProxy(ConnectionManager connectionManager) {
		BorrowedConnectionProxy handler = new BorrowedConnectionProxy( connectionManager );
		return ( Connection ) Proxy.newProxyInstance(
				Connection.class.getClassLoader(),
		        new Class[] { Connection.class },
		        handler
		);
	}

	public static void renderUnuseable(Connection connection) {
		if ( connection != null && Proxy.isProxyClass( connection.getClass() ) ) {
			InvocationHandler handler = Proxy.getInvocationHandler( connection );
			if ( BorrowedConnectionProxy.class.isAssignableFrom( handler.getClass() ) ) {
				( ( BorrowedConnectionProxy ) handler ).useable = false;
			}
		}
	}
}
