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
package unikn.dbis.univis.hibernate;

import org.hibernate.Transaction;
import org.hibernate.Session;

/**
 * TODO: document me!!!
 * <p/>
 * <code>TransactionCallback</code>.
 * <p/>
 * User: raedler
 * Date: 28.09.2006
 * Time: 14:01:58
 *
 * @author Roman R&auml;dle
 * @version $Id: TransactionCallback.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.3
 */
public interface TransactionCallback {
    public abstract void execute(Session session, Transaction trx) throws RuntimeException;
}
