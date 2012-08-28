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

/**
 * The <code>VDiceBox</code> contains the cubes.
 * <p/>
 * User: raedler, weiler
 * Date: 09.04.2006
 * Time: 01:57:53
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: VDiceBox.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.1
 * @see VHierarchy
 */
public interface VDiceBox extends VHierarchy {

    /**
     * Returns the name of the dice box.
     *
     * @return The name of the dice box.
     */
    public String getName();

    /**
     * Sets the name of the dice box.
     *
     * @param name The name of the dice box.
     */
    public void setName(String name);

    /**
     * Returns the name of the dice box which contains the
     * cubes.
     *
     * @return The name of the dice box.
     */
    public String toString();
}