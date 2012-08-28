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
 * VClassification.
 * <p/>
 * User: raedler, weiler
 * Date: 27.08.2006
 * Time: 23:13:07
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: VClassification.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.2
 */
public interface VClassification extends VDataReference {

    /**
     * Returns the type of the class.
     *
     * @return The type of the class.
     */
    public String getType();

    /**
     * Sets the type of the class.
     *
     * @param type The type of the class.
     */
    public void setType(String type);
}