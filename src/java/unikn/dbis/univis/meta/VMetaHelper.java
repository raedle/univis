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
 * <code>VMetaHelper</code>.
 * <p/>
 * User: raedler, weiler
 * Date: 05.07.2006
 * Time: 13:14:45
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: VMetaHelper.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.1
 */
public class VMetaHelper {

    /**
     * Returns true if the parent of the current data reference is a
     * <code>VDimension</code> else returns false.
     *
     * @param dataReference The data reference that gets the recursiceRemove.
     * @return Whether the parent is a <code>VDimension</code> or not.
     */
    public static boolean isParentADimension(VDataReference dataReference) {
        return dataReference.getParent() instanceof VDimension;
    }
}