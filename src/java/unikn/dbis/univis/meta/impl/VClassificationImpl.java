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

import unikn.dbis.univis.meta.VClassification;
import unikn.dbis.univis.meta.VDataReference;

/**
 * TODO: document me!!!
 * <p/>
 * VClassificationImpl.
 * <p/>
 * User: raedler, weiler
 * Date: 27.08.2006
 * Time: 23:14:11
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: VClassificationImpl.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.2
 */
public class VClassificationImpl extends VDataReferenceImpl implements VClassification, VDataReference {

    // The type of the class.
    private String type;

    /**
     * Returns the type of the class.
     *
     * @return The type of the class.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the class.
     *
     * @param type The type of the class.
     */
    public void setType(String type) {
        this.type = type;
    }
}