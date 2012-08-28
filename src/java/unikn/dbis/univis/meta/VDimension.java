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

import java.util.Set;

/**
 * The <code>VDimension</code> is the side of a cube that contains
 * informations about the cube cell data.
 * <p/>
 * User: raedler, weiler
 * Date: 07.04.2006
 * Time: 15:28:03
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: VDimension.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.1
 */
public interface VDimension extends VDataReference {

    /**
     * Whether the dimension is a summable dimension which
     * could be used for dragging into the visualization
     * or not.
     *
     * @return Whether the dimension is a summable dimension
     *         or not.
     */
    public Boolean isDragable();

    /**
     * Sets whether the dimension is a summable dimension
     * which could be used for dragging into the visualization
     * or not.
     *
     * @param summable Whether the dimension is a summable
     *                 dimension or not.
     */
    public void setDragable(Boolean summable);

    /**
     * Flag when the dimension is dropped into the graph.
     *
     * @return Whether the dimension is dropped or not.
     */
    public boolean isDropped();

    /**
     * Sets whether the dimension is dropped or not in the graph.
     *
     * @param dropped Whether the dimension is dropped or not in the graph.
     */
    public void setDropped(boolean dropped);

    /**
     * Returns whether the dimension contains data for parentable
     * usage or not.
     *
     * @return Whether the dimension contains parentable data or
     *         not.
     */
    public Boolean isDependent();


    /**
     * Sets whether the dimension contains data for parentable
     * usage or not.
     *
     * @param parentable Whether the dimension contains parentable
     *                   data or not.
     */
    public void setDependent(Boolean parentable);

    /**
     * Returns whether the dimension is visible or unvisible.
     *
     * @return Whether the dimension is visible or unvisible.
     */
    public Boolean isVisible();

    /**
     * Sets whether the dimension is visible or unvisible.
     *
     * @param visible Whether the dimension is visible or unvisible.
     */
    public void setVisible(Boolean visible);

    /**
     * Returns the cubes that supports this dimension.
     *
     * @return The cubes that supports this dimension.
     */
    public Set<VCube> getSupportedCubes();

    /**
     * Sets the cubes that supports this dimension.
     *
     * @param supportedCubes The cubes that supports this
     *                       dimension.
     */
    public void setSupportedCubes(Set<VCube> supportedCubes);

    /**
     * Returns the identifiers of the selected values.
     *
     * @return The identifiers of the selected values.
     */
    public Set<Filterable> getSelections();

    /**
     * Sets the identifiers of the selected values.
     *
     * @param selections The identifiers of the selected
     *                   values.
     */
    public void setSelections(Set<Filterable> selections);

    /**
     * Returns the size of the selected values.
     *
     * @return The size of the selected values.
     */
    public int getSelectionSize();

    /**
     * Returns whether the selection of the dimension was changed
     * or not.
     *
     * @return Whether the selection of the dimension was changed
     *         or not.
     */
    public boolean isSelectionChanged();

    /**
     * Sets whether the selection of the dimension was changed
     * or not.
     *
     * @param selectionChanged Whether the selection of the dimension
     *                         was changed or not.
     */
    public void setSelectionChanged(boolean selectionChanged);

    /**
     * Returns the <code>VDimension</code> that is close to the
     * cube.
     *
     * @return The blueprint that is close to the cube.
     */
    public VDimension getBlueprint();
}