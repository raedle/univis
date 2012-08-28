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

import unikn.dbis.univis.dnd.VDataReferenceFlavor;
import unikn.dbis.univis.meta.VDimension;
import unikn.dbis.univis.visualization.graph.VGraph;

import javax.swing.*;
import java.awt.dnd.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.*;
import java.io.IOException;

/**
 * TODO: document me!!!
 * <p/>
 * <code>VVisualization</code>.
 * <p/>
 * User: raedler, weiler
 * Date: 11.04.2006
 * Time: 18:25:13
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: VVisualization.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.1
 */
public class VVisualization extends JPanel {

    /**
     * Creates a new <code>JPanel</code> with a double buffer
     * and a flow layout.
     */
    public VVisualization(LayoutManager layoutManager) {
        super(layoutManager);
    }
}