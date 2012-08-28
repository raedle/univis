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
package unikn.dbis.univis.icon;

import javax.swing.*;
import java.awt.*;

/**
 * TODO: document me!!!
 * <p/>
 * VisualCube.
 * <p/>
 * User: raedler, weiler
 * Date: 08.04.2006
 * Time: 19:19:30
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: VCubeIcon.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.1
 */
public class VCubeIcon implements Icon {

    private Color cubeColor;

    public VCubeIcon(Color cubeColor) {
        this.cubeColor = cubeColor;
    }

    /**
     * Draw the icon at the specified location.  Icon implementations
     * may use the Component argument to get properties useful for
     * painting, e.g. the foreground or background color.
     */
    public void paintIcon(Component c, Graphics g, int x, int y) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = 10;
        int height = 10;
        x += 0;
        y += 6;

        g.setColor(cubeColor);
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);

        g.setColor(cubeColor);
        Polygon poly1 = new Polygon();
        poly1.addPoint(x, y);
        poly1.addPoint(x + 5, y - 3);
        poly1.addPoint(x + width + 5, y - 3);
        poly1.addPoint(x + width, y);
        g.fillPolygon(poly1);
        g.setColor(Color.BLACK);
        g.drawPolygon(poly1);

        g.setColor(cubeColor);
        Polygon poly2 = new Polygon();
        poly2.addPoint(x + width, y);
        poly2.addPoint(x + width + 5, y - 3);
        poly2.addPoint(x + width + 5, y - 3 + height);
        poly2.addPoint(x + width, y + height);
        g.fillPolygon(poly2);
        g.setColor(Color.BLACK);
        g.drawPolygon(poly2);
    }

    /**
     * Returns the icon's width.
     *
     * @return an int specifying the fixed width of the icon.
     */
    public int getIconWidth() {
        return 15;
    }

    /**
     * Returns the icon's height.
     *
     * @return an int specifying the fixed height of the icon.
     */
    public int getIconHeight() {
        return 20;
    }
}
