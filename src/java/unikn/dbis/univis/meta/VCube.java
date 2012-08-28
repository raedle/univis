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
package unikn.dbis.univis.meta;

import java.awt.*;

/**
 * The <code>VCube</code> contains information about the OLAP cube.
 * <p/>
 * User: raedler, weiler
 * Date: 07.04.2006
 * Time: 15:27:44
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: VCube.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.1
 */
public interface VCube extends VDataReference {

    /**
     * Returns the color of the cube.
     *
     * @return The color of the cube.
     */
    public Color getColor();

    /**
     * Sets the color of the cube.
     *
     * @param color The color of the cube.
     */
    public void setColor(Color color);
}