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

import org.jfree.data.category.CategoryDataset;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.renderer.category.AreaRenderer;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import unikn.dbis.univis.visualization.Renderable;

/**
 * TODO: document me!!!
 * <p/>
 * <code>AreaChart</code>.
 * <p/>
 * User: raedler, weiler
 * Date: 10.05.2006
 * Time: 14:17:42
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: AreaChart.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.1
 */
public class AreaChart extends AbstractChart<CategoryDataset> implements Renderable {

    // Description of the xAxis.
    private String xAxis;

    // Total amount of the chart.
    private Integer total;

    /**
     * A pie chart with the chart name as name of the chart and
     * the dataset that contains the data of the pie chart.
     *
     * @param chartName Headline of the chart.
     * @param dataset   Dataset of the Chart.
     */
    public AreaChart(String chartName, CategoryDataset dataset, String xAxis) {
        super(chartName, dataset);
        this.xAxis = xAxis;
    }

    /**
     * Returns the JFreeChart.
     *
     * @return JFreeChart.
     */
    protected JFreeChart createChart() {
        return ChartFactory.createAreaChart(getChartName(), "", xAxis, getDataset(), PlotOrientation.VERTICAL, true, false, false);
    }

    /**
     * Returns the total amount of the chart.
     *
     * @return Total amount of the chart.
     */
    protected int createTotal() {
        if (total != null) {
            return total;
        }

        total = 0;
        for (int i = getDataset().getRowCount() - 1; i >= 0; i--) {
            total += getDataset().getValue(i, 0).intValue();
        }
        return total;
    }

    /**
     * Makes the plot.
     */
    protected void plot() {
        CategoryPlot plot = (CategoryPlot) getChart().getPlot();
        CategoryAxis axis = plot.getDomainAxis();
        plot.setForegroundAlpha(0.5f);
        axis.setTickLabelsVisible(false);
        plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
        AreaRenderer renderer = (AreaRenderer) plot.getRenderer();
        renderer.setLegendItemLabelGenerator(new LabelGenerator(createTotal()));
        plot.setNoDataMessage("No data available");
        plot.getDomainAxis().setLabelFont(getLegendFont());
        plot.getRangeAxis().setLabelFont(getLegendFont());
    }

    /**
     * Gets the number of Items in the chart.
     */
    protected int getItemsCount() {
        return getDataset().getRowCount();
    }
}
