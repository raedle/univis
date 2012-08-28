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
package unikn.dbis.univis.explorer.about;

import javax.swing.*;
import java.awt.*;

import unikn.dbis.univis.util.ComponentUtilities;
import unikn.dbis.univis.images.VImageDummy;
import unikn.dbis.univis.message.Internationalizable;

/**
 * TODO: document me!!!
 * <p/>
 * <code>AboutPanel</code>.
 * <p/>
 * User: raedler
 * Date: 17.10.2006
 * Time: 19:34:02
 *
 * @author Roman R&auml;dle
 * @version $Id: AboutPanel.java 342 2006-10-17 23:12:42Z raedler $
 * @since UniVis Explorer 0.3.1.0
 */
public class AboutPanel extends JDialog implements Internationalizable {

    private JTabbedPane tabbedPane;

    public AboutPanel(Frame owner) throws HeadlessException {
        super(owner, "Über", true);

        tabbedPane = new JTabbedPane();

        tabbedPane.addTab("About", new JLabel(new ImageIcon(VImageDummy.class.getResource("splash_screen.png"))));
        tabbedPane.addTab("Detail", new ApplicationData());

        add(tabbedPane);

        pack();

        ComponentUtilities.centerComponentOnScreen(this);

        setVisible(true);
    }

    /**
     * {@inheritDoc}
     */
    public void internationalize() {
        // empty
    }
}
