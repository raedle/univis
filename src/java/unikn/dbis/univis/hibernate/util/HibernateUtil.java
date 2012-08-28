/*
 * Copyright 2005-2006 UniVis Explorer development team.
 *
 * This file is part of UniVis Explorer
 * (http://phobos22.inf.uni-konstanz.de/univis).
 *
 * UniVis Explorer is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * Please see COPYING for the complete licence.
 */
package unikn.dbis.univis.hibernate.util;

import org.hibernate.SessionFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.dialect.Dialect;
import org.hibernate.cfg.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import unikn.dbis.univis.hibernate.TransactionCallback;

/**
 * TODO: document me!!!
 * <p/>
 * <code>HibernateUtil</code>.
 * <p/>
 * User: raedler, weiler
 * Date: 07.04.2006
 * Time: 23:20:10
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: HibernateUtil.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.1
 */
public class HibernateUtil {

    // The logger to log info, error and other occuring messages
    // or exceptions.
    public static final transient Log LOG = LogFactory.getLog(HibernateUtil.class);

    private static final Configuration cfg;

    private static final SessionFactory sessionFactory;

    static {
        // Create the SessionFactory from the hibernate.cfg.xml
        try {
            cfg = new Configuration().configure();
            sessionFactory = cfg.buildSessionFactory();
        }
        catch (HibernateException he) {

            // Make sure you log the exception, as it might be swallowed
            LOG.error("Initial SessionFactory creation failed.", he);

            throw new ExceptionInInitializerError(he);
        }
    }

    public static Dialect getDialect() {
        return cfg.buildSettings().getDialect();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void execute(TransactionCallback callback) throws RuntimeException {
        Session session = sessionFactory.openSession();
        Transaction trx = session.beginTransaction();

        // Call the exucute method of the callback.
        callback.execute(session, trx);

        trx.commit();
        session.close();
    }
}