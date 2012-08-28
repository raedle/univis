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
package unikn.dbis.univis.sql;

import unikn.dbis.univis.meta.VCube;
import unikn.dbis.univis.message.MessageResolver;
import unikn.dbis.univis.icon.VCubeIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Set;

/**
 * TODO: document me!!!
 * <p/>
 * <code>CubeChooser</code>.
 * <p/>
 * User: raedler, weiler
 * Date: 29.08.2006
 * Time: 13:00:51
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: CubeChooser.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.2
 */
public class CubeChooser extends JDialog {

    // The selected cube.
    private VCube cube;

    /**
     * TODO: document me!!!
     *
     * Creates a modeless dialog without a title with the
     * specified <code>Frame</code> as its owner.  If <code>owner</code>
     * is <code>null</code>, a shared, hidden frame will be set as the
     * owner of the dialog.
     * <p/>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     * <p/>
     * NOTE: This constructor does not allow you to create an unowned
     * <code>JDialog</code>. To create an unowned <code>JDialog</code>
     * you must use either the <code>JDialog(Window)</code> or
     * <code>JDialog(Dialog)</code> constructor with an argument of
     * <code>null</code>.
     *
     * @param owner the <code>Frame</code> from which the dialog is displayed
     * @throws java.awt.HeadlessException if <code>GraphicsEnvironment.isHeadless()</code>
     *                                    returns <code>true</code>.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see javax.swing.JComponent#getDefaultLocale
     */
    public CubeChooser(Component owner, Set<VCube> cubes) {
        super(getOwnerFrame(owner), "Cube Selection", true);

        setLayout(new GridLayout(0, 1, 4, 4));

        for (final VCube cube : cubes) {
            JButton b = new JButton(MessageResolver.getMessage("data_reference." + cube.getI18nKey()), new VCubeIcon(cube.getColor()));
            b.addActionListener(new ActionListener() {
                /**
                 * Invoked when an action occurs.
                 */
                public void actionPerformed(ActionEvent e) {
                    CubeChooser.this.cube = cube;
                    dispose();
                }
            });
            add(b);
        }

        pack();

        Frame ownerFrame = getOwnerFrame(owner);
        Dimension d = ownerFrame.getSize();
        int x = (int) (d.getWidth() / 2) - (getWidth() / 2);
        int y = (int) (d.getHeight() / 2) - (getHeight() / 2);

        Point p = SwingUtilities.convertPoint(ownerFrame, x, y, this);
        setBounds(p.x, p.y, getWidth(), getHeight());

        setVisible(true);
    }

    private VCube getSelectedCube() {
        return cube;
    }

    /**
     * Returns the frame that is the frame used for modal action.
     *
     * @param owner The component that owns the dialog.
     * @return The frame that will be used for modal action.
     */
    private static Frame getOwnerFrame(Component owner) {
        Frame frame = null;
        for (Component c = owner; c != null; c = c.getParent()) {
            if (c instanceof Frame) {
                frame = (Frame) c;
                break;
            }
        }
        return frame;
    }

    /**
     * Shows the cube chooser for cube selection.
     *
     * @param owner The component that owns the dialog.
     * @param cubes The cubes that should be shown on the dialog.
     * @return The selected cube.
     */
    public static VCube showCubeChooser(Component owner, Set<VCube> cubes) {
        CubeChooser cubeChooser = new CubeChooser(owner, cubes);
        return cubeChooser.getSelectedCube();
    }
}
