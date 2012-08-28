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
package unikn.dbis.univis.exception;

import unikn.dbis.univis.util.ComponentUtilities;
import unikn.dbis.univis.icon.VIcons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;

/**
 * TODO: document me!!!
 * <p/>
 * <code>VExceptionDialog</code>.
 * <p/>
 * User: raedler, weiler
 * Date: 21.05.2006
 * Time: 00:17:07
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: VExceptionDialog.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.1
 */
public class VExceptionDialog extends JDialog {

    private JButton close;
    private JTextArea exceptionArea;
    private JLabel label;
    private JButton openDetails;
    private JScrollPane scrollPane;

    private Exception exception;
    private boolean opened = true;

    /**
     * Creates a non-modal dialog without a title with the
     * specified <code>Frame</code> as its owner.  If <code>owner</code>
     * is <code>null</code>, a shared, hidden frame will be set as the
     * owner of the dialog.
     * <p/>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     *
     * @param owner the <code>Frame</code> from which the dialog is displayed
     * @throws java.awt.HeadlessException if GraphicsEnvironment.isHeadless()
     *                                    returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see javax.swing.JComponent#getDefaultLocale
     */
    public VExceptionDialog(Frame owner, Exception e) throws HeadlessException {
        super(owner, "Exception");

        this.exception = e;

        setModal(true);
        setLayout(new BorderLayout());

        initComponents();

        /*
        JPanel top = new JPanel(new BorderLayout());

        JLabel label = new JLabel(VIcons.DELETE);
        label.setText(e.getMessage());

        JButton openDetails = new JButton("<< Details");

        top.add(label, BorderLayout.CENTER);
        top.add(openDetails, BorderLayout.SOUTH);

        JTextArea exceptionArea = new JTextArea();

        StringBuilder builder = new StringBuilder();
        for (StackTraceElement element : e.getStackTrace()) {
            builder.append(element.toString()).append(System.getProperty("line.separator"));
        }

        exceptionArea.setText(builder.toString());

        final JScrollPane scrollPane = new JScrollPane(exceptionArea);

        openDetails.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
            public void actionPerformed(ActionEvent e) {
                if (opened) {
                    getContentPane().remove(scrollPane);
                    opened = false;

                    pack();
                    setSize(400, (int) getSize().getHeight());
                }
                else {
                    getContentPane().add(scrollPane, BorderLayout.CENTER);
                    opened = true;

                    setSize(400, 300);
                }

                validate();
                repaint();

                //ComponentUtilities.centerComponentOnScreen(VExceptionDialog.this);
            }
        });

        getContentPane().add(top, BorderLayout.NORTH);

        exceptionArea.setCaretPosition(1);
        */

        pack();
        setSize(400, (int) getSize().getHeight());

        ComponentUtilities.centerComponentOnScreen(this);

        setVisible(true);
    }

    private void initComponents() {
        label = new javax.swing.JLabel();
        close = new javax.swing.JButton();
        openDetails = new javax.swing.JButton();
        scrollPane = new javax.swing.JScrollPane();
        exceptionArea = new javax.swing.JTextArea();

        label.setText(exception.getLocalizedMessage());

        close.setText("Schlie\u00dfen");

        openDetails.setText(">> Details");

        exceptionArea.setColumns(20);
        exceptionArea.setRows(5);
        scrollPane.setViewportView(exceptionArea);

        StringBuilder builder = new StringBuilder();
        for (StackTraceElement element : exception.getStackTrace()) {
            builder.append(element.toString()).append(System.getProperty("line.separator"));
        }
        exceptionArea.setText(builder.toString());
        exceptionArea.setCaretPosition(1);

        final JLabel label2 = new JLabel();

        final GroupLayout layout = new GroupLayout(getContentPane());
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.LEADING)
                        .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(layout.createParallelGroup(GroupLayout.LEADING)
                                .add(label)
                                .add(GroupLayout.TRAILING, layout.createSequentialGroup()
                                        .add(openDetails)
                                        .addPreferredGap(LayoutStyle.RELATED)
                                        .add(close))
                                .add(GroupLayout.TRAILING, scrollPane, GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))
                        .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.LEADING)
                        .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(label)
                        .add(44, 44, 44)
                        .add(layout.createParallelGroup(GroupLayout.BASELINE)
                                .add(close)
                                .add(openDetails))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(scrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                        .addContainerGap())
        );

        openDetails.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             */
            public void actionPerformed(ActionEvent e) {
                if (opened) {
                    layout.removeLayoutComponent(scrollPane);
                    opened = false;

                    openDetails.setText("<< Details");

                    pack();
                    setSize(400, (int) getSize().getHeight());
                }
                else {
                    layout.replace(label2, scrollPane);
                    opened = true;

                    openDetails.setText(">> Details");

                    setSize(400, 300);
                }

                validate();
                repaint();
            }
        });

        close.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             */
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}