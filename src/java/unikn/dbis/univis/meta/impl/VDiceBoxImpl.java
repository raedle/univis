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
package unikn.dbis.univis.meta.impl;

import unikn.dbis.univis.meta.VDiceBox;

/**
 * The <code>VDiceBoxImpl</code> contains the cubes.
 * <p/>
 * User: raedler, weiler
 * Date: 09.04.2006
 * Time: 01:56:32
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: VDiceBoxImpl.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.1
 * @see VHierarchyImpl
 * @see VDiceBox
 */
public class VDiceBoxImpl extends VHierarchyImpl implements VDiceBox {

    // ##############################################################################
    // Interface implementations.
    // ##############################################################################

    // The name of the dice box.
    private String name;

    /**
     * Returns the name of the dice box.
     *
     * @return The name of the dice box.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the dice box.
     *
     * @param name The name of the dice box.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the dice box which contains the
     * cubes.
     *
     * @return The name of the dice box.
     */
    @Override
    public String toString() {
        return getName();
    }
}