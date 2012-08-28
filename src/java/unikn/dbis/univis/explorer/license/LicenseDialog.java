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
package unikn.dbis.univis.explorer.license;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.io.IOException;

import unikn.dbis.univis.util.ComponentUtilities;
import unikn.dbis.univis.message.MessageResolver;
import unikn.dbis.univis.system.Constants;

/**
 * TODO: document me!!!
 * <p/>
 * <code>LicenseDialog</code>.
 * <p/>
 * User: raedler
 * Date: 18.10.2006
 * Time: 00:57:12
 *
 * @author Roman R&auml;dle
 * @version $Id: LicenseDialog.java 342 2006-10-17 23:12:42Z raedler $
 * @since UniVis Explorer 0.3.1.0
 */
public class LicenseDialog extends JDialog {

    // The logger for logging messages like info, error, debug... messages.
    private final static Log LOG = LogFactory.getLog(LicenseDialog.class);

    public LicenseDialog(Frame owner) throws HeadlessException {
        super(owner, "License", true);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(new LicenseEditorPane());
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(scrollPane);

        JButton close = new JButton(MessageResolver.getMessage(Constants.CLOSE));
        close.addActionListener(new ActionListener() {

            /**
             * {@inheritDoc}
             */
            public void actionPerformed(ActionEvent e) {
                LicenseDialog.this.dispose();
            }
        });

        add(Box.createRigidArea(new Dimension(0, 5)));

        close.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(close);

        prepareSize();

        ComponentUtilities.centerComponentOnScreen(this);

        setVisible(true);
    }

    private void prepareSize() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int height = (int) screenSize.getHeight();
        int width = (int) (height / 1.3);

        if (height > 650) {
            height = 600;
            width = (int) (height / 1.3);
        }

        setSize(new Dimension(width, height));
    }

    protected class LicenseEditorPane extends JEditorPane {

        public LicenseEditorPane() {
            setEditable(false);

            URL license = getClass().getResource("license.txt");

            try {
                setPage(license);
            }
            catch (IOException ioe) {
                if (LOG.isErrorEnabled()) {
                    LOG.error(ioe.getMessage(), ioe);
                }
            }
        }
    }
}
