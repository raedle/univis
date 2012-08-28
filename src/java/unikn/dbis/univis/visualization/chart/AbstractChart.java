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

import unikn.dbis.univis.visualization.AbstractRenderer;
import unikn.dbis.univis.visualization.Renderable;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.title.Title;
import org.jfree.data.general.Dataset;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO: document me!!!
 * <p/>
 * <code>AbstractChart</code>.
 * <p/>
 * User: raedler, weiler
 * Date: 10.05.2006
 * Time: 14:53:16
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: AbstractChart.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.1
 */
public abstract class AbstractChart<T extends Dataset> extends AbstractRenderer implements Renderable {

    // Chart itself.
    private JFreeChart chart;

    // The dataset of the chart.
    private T dataset;

    // String for headline.
    private String chartName;

    // The font of the legend.
    private Font legendFont = new Font("Tahoma", Font.PLAIN, 10);

    // Arraylist for subtitles.
    private List<Title> subtitleList = new ArrayList<Title>();

    /**
     * The abstract class for an easy chart creation.
     *
     * @param chartName The name of the chart.
     * @param dataset   The specific dataset.
     */
    protected AbstractChart(String chartName, T dataset) {
        this.chartName = chartName;
        this.dataset = dataset;
    }

    /**
     * Returns the dataset of the chart.
     *
     * @return The dataset of the chart.
     */
    public T getDataset() {
        return dataset;
    }

    /**
     * Returns the name of the chart.
     *
     * @return The name of the chart.
     */
    public String getChartName() {
        return chartName;
    }

    /**
     * Returns the font of the legend.
     *
     * @return The font of the legend.
     */
    public Font getLegendFont() {
        return legendFont;
    }

    /**
     * Returns the chart.
     *
     * @return The chart.
     */
    public JFreeChart getChart() {
        return chart;
    }

    /**
     * Sets the subtitels and the legend.
     *
     * @param total  The total amount of the chart.
     * @param legend The legend of the chart
     */
    public void setSubtitles(int total, LegendTitle legend) {

        String totalName = "Total: " + total;
        TextTitle totalTitle = new TextTitle(totalName);
        subtitleList.add(totalTitle);
        if (getItemsCount() < 15) {
            subtitleList.add(legend);
        }
        chart.setSubtitles(subtitleList);
    }

    /**
     * Renders the component that should be displayed
     * at screen.
     *
     * @return The component that should be displayed
     *         at screen.
     */
    public Component renderComponent() {

        chart = createChart();

        LegendTitle legend = chart.getLegend();
        if (legend != null) {
            legend.setItemFont(getLegendFont());
        }

        int total = createTotal();

        setSubtitles(total, legend);

        plot();

        VChartPanel chartPanel = new VChartPanel(chart, true);

        chartPanel.setMouseZoomable(true);

        return chartPanel;
    }

    /**
     * Returns the JFreeChart.
     *
     * @return JFreeChart.
     */
    protected abstract JFreeChart createChart();

    /**
     * Returns the total amount of the chart.
     *
     * @return Total amount of the chart.
     */
    protected abstract int createTotal();

    /**
     * Makes the plot.
     */
    protected abstract void plot();

    /**
     * Gets the number of Items in the chart.
     */
    protected abstract int getItemsCount();

    /**
     * @return X value of the chart.
     */
    public int getX() {
        return 0;
    }

    /**
     * @return Y value of the chart.
     */
    public int getY() {
        return 0;
    }
}