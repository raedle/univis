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
package unikn.dbis.univis.visualization.graph;

import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.CellView;
import org.jgraph.graph.GraphModel;
import org.jgraph.graph.VertexView;

/**
 * TODO: document me!!!
 * <p/>
 * <code>MyCellViewFactory</code>.
 * <p/>
 * User: raedler, weiler
 * Date: 12.04.2006
 * Time: 00:03:05
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: VCellViewFactory.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.1
 */
public class VCellViewFactory extends DefaultCellViewFactory {

    /**
     * Constructs a view for the specified cell and associates it
     * with the specified object using the specified CellMapper.
     *
     * @param cell reference to the object in the model
     */
    public CellView createView(GraphModel model, Object cell) {

        CellView view;

        if (model.isPort(cell)) {
            view = createPortView(cell);
        }
        else if (model.isEdge(cell)) {
            view = createEdgeView(cell);
        }
        else {
            view = createVertexView(cell);
        }

        return view;
    }

    /**
     * Constructs a VertexView view for the specified object.
     */
    @Override
    protected VertexView createVertexView(Object cell) {
        return new VVertexView(cell);
    }
}