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
package unikn.dbis.univis.visualization;

/**
 * TODO: document me!!!
 * <p/>
 * <code>Visualizable</code>.
 * <p/>
 * User: raedler, weiler
 * Date: 30.08.2006
 * Time: 00:43:35
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: Visualizable.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.2
 */
public interface Visualizable {
    public void clear();
    public void undo();
    public void redo();
    public void zoomIn();
    public void zoomOut();
    public void rotateRight();
    public void rotateLeft();
    public void setMoveable(boolean movable);
    public boolean isMoveable();
}
