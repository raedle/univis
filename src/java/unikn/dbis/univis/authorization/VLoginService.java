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
package unikn.dbis.univis.authorization;

import org.jdesktop.swingx.auth.LoginService;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import unikn.dbis.univis.hibernate.util.HibernateUtil;

/**
 * TODO: document me!!!
 * <p/>
 * <code>VLoginService</code>.
 * <p/>
 * User: raedler
 * Date: 24.10.2006
 * Time: 12:41:05
 *
 * @author Roman R&auml;dle
 * @version $Id$
 * @since UniVis Explorer 0.3
 */
public class VLoginService extends LoginService {

    public boolean authenticate(String username, char[] chars, String string1) throws Exception {
        SessionFactory sf = HibernateUtil.getSessionFactory();

        String password = String.valueOf(chars);

        Session session = sf.openSession();
        VUser user = (VUser) session.createQuery("from " + VUser.class.getName() + " as user where user.userName = '" + username + "' and user.password = '" + password + "'").uniqueResult();
        session.close();

        return user != null;
    }
}
