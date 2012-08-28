// $Id: JDBCContext.java 9240 2006-02-08 22:47:34Z steveebersole $
package org.hibernate.jdbc;

import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.SQLException;

import javax.transaction.TransactionManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.ConnectionReleaseMode;
import org.hibernate.HibernateException;
import org.hibernate.Interceptor;
import org.hibernate.SessionException;
import org.hibernate.Transaction;
import org.hibernate.TransactionException;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.exception.JDBCExceptionHelper;
import org.hibernate.transaction.CacheSynchronization;
import org.hibernate.transaction.TransactionFactory;
import org.hibernate.util.JTAHelper;

/**
 * Acts as the mediary between "entity-mode related" sessions in terms of
 * their interaction with the JDBC data store.
 *
 * @author Steve Ebersole
 */
public class JDBCContext implements Serializable, ConnectionManager.Callback {

	// TODO : make this the factory for "entity mode related" sessions;
	// also means making this the target of transaction-synch and the
	// thing that knows how to cascade things between related sessions
	//
	// At that point, perhaps this thing is a "SessionContext", and
	// ConnectionManager is a "JDBCContext"?  A "SessionContext" should
	// live in the impl package...

	private static final Log log = LogFactory.getLog( JDBCContext.class );

	public static interface Context extends TransactionFactory.Context {
		/**
		 * We cannot rely upon this method being called! It is only
		 * called if we are using Hibernate Transaction API.
		 */
		public void afterTransactionBegin(Transaction tx);
		public void beforeTransactionCompletion(Transaction tx);
		public void afterTransactionCompletion(boolean success, Transaction tx);
		public ConnectionReleaseMode getConnectionReleaseMode();
		public boolean isAutoCloseSessionEnabled();
	}

	private Context owner;
	private ConnectionManager connectionManager;
	private transient boolean isTransactionCallbackRegistered;
	private transient Transaction hibernateTransaction;

	public JDBCContext(Context owner, Connection connection, Interceptor interceptor) {
		this.owner = owner;
		this.connectionManager = new ConnectionManager(
		        owner.getFactory(),
		        this,
		        owner.getConnectionReleaseMode(),
		        connection,
		        interceptor
			);

		final boolean registerSynchronization = owner.isAutoCloseSessionEnabled()
		        || owner.isFlushBeforeCompletionEnabled()
		        || owner.getConnectionReleaseMode() == ConnectionReleaseMode.AFTER_TRANSACTION;
		if ( registerSynchronization ) {
			registerSynchronizationIfPossible();
		}
	}

	/**
	 * Private constructor used exclusively for custom serialization...
	 *
	 */
	private JDBCContext() {
	}


	// ConnectionManager.Callback implementation ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	public void connectionOpened() {
		if ( !isTransactionCallbackRegistered ) {
			// If there is no current transaction callback registered
			// when we obtain a connection, try to register one now.
			// Note that this is not going to handle the case of
			// multiple-transactions-per-connection when the user is
			// manipulating transactions (need to use Hibernate txn)
			registerSynchronizationIfPossible();
		}

		if ( owner.getFactory().getStatistics().isStatisticsEnabled() ) {
			owner.getFactory().getStatisticsImplementor().connect();
		}
	}

	public void connectionCleanedUp() {
		if ( !isTransactionCallbackRegistered ) {
			afterTransactionCompletion( false, null );
			// Note : success = false, because we don't know the outcome of the transaction
		}
	}

	public SessionFactoryImplementor getFactory() {
		return owner.getFactory();
	}

	public ConnectionManager getConnectionManager() {
		return connectionManager;
	}

	public Connection borrowConnection() {
		return connectionManager.borrowConnection();
	}
	
	public Connection connection() throws HibernateException {
		if ( owner.isClosed() ) {
			throw new SessionException( "Session is closed" );
		}

		return connectionManager.getConnection();
	}

	public boolean registerCallbackIfNecessary() {
		if ( isTransactionCallbackRegistered ) {
			return false;
		}
		else {
			isTransactionCallbackRegistered = true;
			return true;
		}

	}

	public boolean registerSynchronizationIfPossible() {
		if ( isTransactionCallbackRegistered ) return true;
		TransactionManager tm = owner.getFactory().getTransactionManager();
		if ( tm == null ) {
			return false;
		}
		else {
			try {
				javax.transaction.Transaction tx = tm.getTransaction();
				if ( JTAHelper.isTransactionInProgress(tx) ) {
					tx.registerSynchronization( new CacheSynchronization(owner, this, tx, null) );
					isTransactionCallbackRegistered = true;
					log.debug("successfully registered Synchronization");
					return true;
				}
				else {
					log.debug("no active transaction, could not register Synchronization");
					return false;
				}
			}
			catch (Exception e) {
				throw new TransactionException( "could not register synchronization with JTA TransactionManager", e );
			}
		}
	}
	
	public boolean isTransactionInProgress() {
		boolean isHibernateTransactionActive = hibernateTransaction!=null && 
				hibernateTransaction.isActive();
		return isHibernateTransactionActive || JTAHelper.isTransactionInProgress( owner.getFactory() );
	}

	public Transaction getTransaction() throws HibernateException {
		if (hibernateTransaction==null) {
			hibernateTransaction = owner.getFactory().getSettings()
					.getTransactionFactory()
					.createTransaction( this, owner );
		}
		return hibernateTransaction;
	}
	
	public void beforeTransactionCompletion(Transaction tx) {
		log.trace( "before transaction completion" );
		owner.beforeTransactionCompletion(tx);
	}
	
	/**
	 * We cannot rely upon this method being called! It is only
	 * called if we are using Hibernate Transaction API.
	 */
	public void afterTransactionBegin(Transaction tx) {
		log.trace( "after transaction begin" );
		owner.afterTransactionBegin(tx);
	}

	public void afterTransactionCompletion(boolean success, Transaction tx) {
		log.trace( "after transaction completion" );

		if ( getFactory().getStatistics().isStatisticsEnabled() ) {
			getFactory().getStatisticsImplementor().endTransaction(success);
		}

		connectionManager.afterTransaction();

		isTransactionCallbackRegistered = false;
		hibernateTransaction = null;
		owner.afterTransactionCompletion(success, tx);
	}
	
	/**
	 * Called after executing a query outside the scope of
	 * a Hibernate or JTA transaction
	 */
	public void afterNontransactionalQuery(boolean success) {
		log.trace( "after autocommit" );
		try {
			// check to see if the connection is in auto-commit 
			// mode (no connection means aggressive connection
			// release outside a JTA transaction context, so MUST
			// be autocommit mode)
			boolean isAutocommit = connectionManager.isAutoCommit();

			connectionManager.afterTransaction();
			
			if ( isAutocommit ) {
				owner.afterTransactionCompletion(success, null);
			}
		}
		catch (SQLException sqle) {
			throw JDBCExceptionHelper.convert( 
					owner.getFactory().getSQLExceptionConverter(),
					sqle,
					"could not inspect JDBC autocommit mode"
				);
		}
	}


	// serialization ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	private void writeObject(ObjectOutputStream oos) throws IOException {
		// isTransactionCallbackRegistered denotes whether any Hibernate
		// Transaction has registered as a callback against this
		// JDBCContext; only one such callback is allowed.  Directly
		// serializing this value causes problems with JDBCTransaction,
		// or really any Transaction impl where the callback is local
		// to the Transaction instance itself, since that Transaction
		// is not serialized along with the JDBCContext.  Thus we
		// handle that fact here explicitly...
		oos.defaultWriteObject();
		boolean deserHasCallbackRegistered = isTransactionCallbackRegistered
				&& ! owner.getFactory().getSettings().getTransactionFactory()
				.areCallbacksLocalToHibernateTransactions();
		oos.writeBoolean( deserHasCallbackRegistered );
	}

	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		ois.defaultReadObject();
		isTransactionCallbackRegistered = ois.readBoolean();
	}


	// custom serialization ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	/**
	 * Custom serialization routine used during serialization of a
	 * Session/PersistenceContext for increased performance.
	 *
	 * @param oos The stream to which we should write the serial data.
	 * @throws IOException
	 */
	public void serialize(ObjectOutputStream oos) throws IOException {
		boolean deserHasCallbackRegistered = isTransactionCallbackRegistered
				&& ! owner.getFactory().getSettings().getTransactionFactory()
				.areCallbacksLocalToHibernateTransactions();
		oos.writeBoolean( deserHasCallbackRegistered );
		connectionManager.serialize( oos );
	}

	/**
	 * Custom deserialization routine used during deserialization of a
	 * Session/PersistenceContext for increased performance.
	 *
	 * @param ois The stream from which to read the entry.
	 * @throws IOException
	 */
	public static JDBCContext deserialize(
			ObjectInputStream ois,
	        Context context,
	        Interceptor interceptor) throws IOException {
		JDBCContext jdbcContext = new JDBCContext();
		jdbcContext.owner = context;
		jdbcContext.isTransactionCallbackRegistered = ois.readBoolean();
		jdbcContext.connectionManager = ConnectionManager.deserialize(
				ois,
				context.getFactory(),
		        interceptor,
		        context.getConnectionReleaseMode(),
		        jdbcContext
		);
		return jdbcContext;
	}
}
