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

import org.jdesktop.swingx.JXLoginDialog;
import org.jdesktop.swingx.JXLoginPanel;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import unikn.dbis.univis.explorer.VExplorer;
import unikn.dbis.univis.explorer.VSplashScreen;
import unikn.dbis.univis.images.VImageDummy;
import unikn.dbis.univis.hibernate.util.HibernateUtil;

import java.awt.event.WindowAdapter;
import java.io.InputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * TODO: document me!!!
 * <p/>
 * <code>VLogin</code>.
 * <p/>
 * User: raedler
 * Date: 24.10.2006
 * Time: 12:39:27
 *
 * @author Roman R&auml;dle
 * @version $Id$
 * @since UniVis Explorer 0.3
 */
public class VLogin {

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = HibernateUtil.getSessionFactory().openSession().connection();
        }
        connection.commit();

        return connection;
    }

    public VLogin() {
        //super(new VLoginService(), new VPasswordStore(), new VUserNameStore());

        Session session = HibernateUtil.getSessionFactory().openSession();

        final JXLoginPanel.JXLoginFrame frame = JXLoginPanel.showLoginFrame(new VLoginService());
        frame.setAlwaysOnTop(true);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent e) {
                //make sure the login was successful
                if (frame.getStatus() == JXLoginPanel.Status.SUCCEEDED) {
                    new VExplorer().setVisible(true);
                }
            }
        });
    }

    public static void main(String[] args) {

        InputStream imageStream = null;
        try {
            imageStream = VImageDummy.class.getResource("splash_screen.png").openStream();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }

        final VSplashScreen splashScreen = new VSplashScreen(imageStream);

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new VLogin();

                    if (splashScreen != null) {
                        splashScreen.destroy();
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                    System.exit(100);
                }
            }
        });
    }
}
