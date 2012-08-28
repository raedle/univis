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

import unikn.dbis.univis.meta.VFunction;
import unikn.dbis.univis.meta.VDataReference;
import unikn.dbis.univis.meta.Selectable;

/**
 * TODO: document me!!!
 * <p/>
 * VFunctionImpl.
 * <p/>
 * User: raedler, weiler
 * Date: 27.08.2006
 * Time: 23:13:07
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: VFunctionImpl.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.2
 */
public class VFunctionImpl extends VDataReferenceImpl implements VFunction, Selectable, VDataReference {

    // The name of the definition.
    private String definition;

    // Whether the definition is selected or not.
    private boolean selected;

    /**
     * Returns the name of the definition.
     *
     * @return The name of the definition.
     */
    public String getDefinition() {
        return definition;
    }

    /**
     * Sets the name of the definition.
     *
     * @param definition The name of the definition.
     */
    public void setDefinition(String definition) {
        this.definition = definition;
    }

    /**
     * Returns whether the object is selected or not.
     *
     * @return Whether the object is selected or not.
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Sets whether the object should be selected or not.
     *
     * @param selected Whether the object is selected or not.
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
