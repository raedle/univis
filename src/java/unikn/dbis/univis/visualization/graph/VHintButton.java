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
package unikn.dbis.univis.visualization.graph;

import unikn.dbis.univis.icon.VIcons;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

/**
 * TODO: document me!!!
 * <p/>
 * <code>VHintButton</code>.
 * <p/>
 * User: raedler, weiler
 * Date: 04.07.2006
 * Time: 23:53:54
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: VHintButton.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.1
 */
public class VHintButton extends JButton {

    /**
     * Creates a button with an icon.
     *
     * @param icon the Icon image to display on the button
     */
    public VHintButton(Icon icon) {
        super(icon);

        setBackground(Color.WHITE);
        setBorderPainted(false);

        setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
    }
}