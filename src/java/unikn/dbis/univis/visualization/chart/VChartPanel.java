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
package unikn.dbis.univis.visualization.chart;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;

import javax.swing.*;
import java.util.Collection;

/**
 * TODO: document me!!!
 * <p/>
 * <code>VChartPanel</code>.
 * <p/>
 * User: raedler, weiler
 * Date: 25.05.2006
 * Time: 00:03:09
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: VChartPanel.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.1
 */
public class VChartPanel extends ChartPanel {

    // The legend items of the chart panel.
    private Collection<LegendItem> legendItems;

    /**
     * Constructs a panel that displays the specified chart.
     *
     * @param chart the chart.
     */
    public VChartPanel(JFreeChart chart) {
        super(chart);
    }

    /**
     * Constructs a panel containing a chart.
     *
     * @param chart     the chart.
     * @param useBuffer a flag controlling whether or not an off-screen buffer
     *                  is used.
     */
    public VChartPanel(JFreeChart chart, boolean useBuffer) {
        super(chart, useBuffer);
    }

    /**
     * Returns the legend items of the chart panal.
     *
     * @return The legend items.
     */
    public Collection<LegendItem> getLegendItems() {
        return legendItems;
    }

    /**
     * Sets the legend items of the chart panel.
     *
     * @param legendItems The legend items.
     */
    public void setLegendItems(Collection<LegendItem> legendItems) {
        this.legendItems = legendItems;
    }

    /**
     * Creates a popup menu for the panel.
     *
     * @param properties include a menu item for the chart property editor.
     * @param save       include a menu item for saving the chart.
     * @param print      include a menu item for printing the chart.
     * @param zoom       include menu items for zooming.
     * @return The popup menu.
     */
    @Override
    public JPopupMenu createPopupMenu(boolean properties, boolean save, boolean print, boolean zoom) {
        return super.createPopupMenu(properties, save, print, zoom);
    }
}