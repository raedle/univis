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

import org.jgraph.graph.VertexView;
import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.CellView;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.JGraph;

import javax.swing.*;
import java.awt.*;

/**
 * TODO: document me!!!
 * <p/>
 * <code>VVertexView</code>.
 * <p/>
 * User: raedler, weiler
 * Date: 12.04.2006
 * Time: 00:04:49
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: VVertexView.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.1
 */
public class VVertexView extends VertexView {

    private static CellViewRenderer cellRenderer = new VCellRenderer();

    /**
     * Constructs a vertex view for the specified model object and the specified
     * child views.
     *
     * @param cell reference to the model object
     */
    public VVertexView(Object cell) {
        super(cell);
    }

    /**
     * Returns a renderer for the class.
     */
    @Override
    public CellViewRenderer getRenderer() {
        return cellRenderer;
    }

    static class VCellRenderer implements CellViewRenderer {

        /**
         * Creates a new <code>JPanel</code> with a double buffer
         * and a flow layout.
         */
        public VCellRenderer() {

        }

        /**
         * Configure and return the renderer based on the passed in
         * components. The value is typically set from messaging the
         * graph with <code>convertValueToString</code>.
         * We recommend you check the value's class and throw an
         * illegal argument exception if it's not correct.
         *
         * @param graph   the graph that that defines the rendering context.
         * @param view    the view that should be rendered.
         * @param sel     whether the object is selected.
         * @param focus   whether the object has the focus.
         * @param preview whether we are drawing a preview.
         * @return the component used to render the value.
         */
        public Component getRendererComponent(JGraph graph, CellView view, boolean sel, boolean focus, boolean preview) {

            Component c = null;
            if (view.getCell() instanceof DefaultGraphCell) {
                Object o = ((DefaultGraphCell) view.getCell()).getUserObject();

                if (o instanceof Component) {
                    c = (Component) o;
                }
            }

            if (c != null) {
                return c;
            }
            else {
                JPanel panel = new JPanel(new BorderLayout());
                panel.add(new JLabel("n/a @see VVertexView"), BorderLayout.CENTER);

                return panel;
            }
        }
    }
}