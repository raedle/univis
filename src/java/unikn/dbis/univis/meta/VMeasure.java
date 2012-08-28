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
 * The <code>VDataReference</code> contains information about
 * the data of the object that references this object.
 * <p/>
 * User: raedler, weiler
 * Date: 26.08.2006
 * Time: 15:55:56
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: VMeasure.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.2
 */
public interface VMeasure extends VDataReference, Selectable {

    /**
     * Returns the name of the measure.
     *
     * @return The name of the measure.
     */
    public String getColumn();

    /**
     * Sets the name of the measure.
     *
     * @param measure The name of the measure.
     */
    public void setColumn(String measure);
}
