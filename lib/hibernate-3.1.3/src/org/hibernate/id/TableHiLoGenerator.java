//$Id: TableHiLoGenerator.java 6253 2005-03-30 17:12:54Z epbernard $
package org.hibernate.id;

import java.io.Serializable;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.type.Type;
import org.hibernate.util.PropertiesHelper;

/**
 * <b>hilo</b><br>
 * <br>
 * An <tt>IdentifierGenerator</tt> that returns a <tt>Long</tt>, constructed using
 * a hi/lo algorithm. The hi value MUST be fetched in a seperate transaction
 * to the <tt>Session</tt> transaction so the generator must be able to obtain
 * a new connection and commit it. Hence this implementation may not
 * be used  when the user is supplying connections. In this
 * case a <tt>SequenceHiLoGenerator</tt> would be a better choice (where
 * supported).<br>
 * <br>
 * Mapping parameters supported: table, column, max_lo
 *
 * @see SequenceHiLoGenerator
 * @author Gavin King
 */

public class TableHiLoGenerator extends TableGenerator {

	/**
	 * The max_lo parameter
	 */
	public static final String MAX_LO = "max_lo";

	private long hi;
	private int lo;
	private int maxLo;
	private Class returnClass;

	private static final Log log = LogFactory.getLog(TableHiLoGenerator.class);

	public void configure(Type type, Properties params, Dialect d) {
		super.configure(type, params, d);
		maxLo = PropertiesHelper.getInt(MAX_LO, params, Short.MAX_VALUE);
		lo = maxLo + 1; // so we "clock over" on the first invocation
		returnClass = type.getReturnedClass();
	}

	public synchronized Serializable generate(SessionImplementor session, Object obj) 
	throws HibernateException {
        if (maxLo < 2) {
			//keep the behavior consistent even for boundary usages
			int val = ( (Integer) super.generate(session, obj) ).intValue();
			return IdentifierGeneratorFactory.createNumber( val, returnClass );
		}
		if (lo>maxLo) {
			int hival = ( (Integer) super.generate(session, obj) ).intValue();
			lo = (hival == 0) ? 1 : 0;
			hi = hival * (maxLo+1);
			log.debug("new hi value: " + hival);
		}

		return IdentifierGeneratorFactory.createNumber( hi + lo++, returnClass );

	}

}
