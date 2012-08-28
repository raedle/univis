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
 * TODO: document me!!!
 * <p/>
 * <code>Filterable</code>.
 * <p/>
 * User: raedler, weiler
 * Date: 29.08.2006
 * Time: 00:26:21
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: Selectable.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.2
 */
public interface Selectable {

    /**
     * Returns whether the object is selected or not.
     *
     * @return Whether the object is selected or not.
     */
    public boolean isSelected();

    /**
     * Sets whether the object should be selected or not.
     *
     * @param selected Whether the object is selected or not.
     */
    public void setSelected(boolean selected);
}
