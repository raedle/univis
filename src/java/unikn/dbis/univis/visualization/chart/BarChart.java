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
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import unikn.dbis.univis.visualization.Renderable;

/**
 * TODO: document me!!!
 * <p/>
 * <code>BarChart</code>.
 * <p/>
 * User: raedler, weiler
 * Date: 10.05.2006
 * Time: 15:14:42
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: BarChart.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.1
 */
public class BarChart extends AbstractChart<CategoryDataset> implements Renderable {

    // Description of the xAxis.
    private String xAxis;

    // The type of the chart - vertical or horizontal
    private ChartType chartType;

    // Total amount of the chart.
    private Integer total;

    /**
     * A bar chart with the chart name as name of the chart and
     * the dataset that contains the data of the pie chart.
     *
     * @param chartName Headline of the chart.
     * @param dataset   Dataset of the Chart.
     */
    public BarChart(String chartName, CategoryDataset dataset, String xAxis, ChartType chartType) {
        super(chartName, dataset);
        this.xAxis = xAxis;
        this.chartType = chartType;
    }

    /**
     * @return JFreeChart as BarChart3D.
     */
    protected JFreeChart createChart() {
        if (ChartType.BAR_CHART_HORIZONTAL.equals(chartType)) {
            return ChartFactory.createBarChart3D(getChartName(), "", xAxis, getDataset(), PlotOrientation.HORIZONTAL, true, false, false);
        }
        else if (ChartType.BAR_CHART_VERTICAL.equals(chartType)) {
            return ChartFactory.createBarChart3D(getChartName(), "", xAxis, getDataset(), PlotOrientation.VERTICAL, true, false, false);
        }
        else {
            return null;
        }
    }

    /**
     * @return total amount of the chart.
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
     * The number of values of the chart.
     *
     * @return The number of values of the chart.
     */
    protected int countValues() {
        return getDataset().getRowCount();
    }

    /**
     * starts the Bar3DChart.
     */
    public void plot() {
        CategoryPlot plot = (CategoryPlot) getChart().getPlot();
        CategoryAxis axis = plot.getDomainAxis();
        axis.setTickLabelsVisible(false);
        BarRenderer3D renderer = (BarRenderer3D) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        renderer.setLegendItemLabelGenerator(new LabelGenerator(createTotal()));
        plot.setNoDataMessage("No data available");
        plot.getDomainAxis().setLabelFont(getLegendFont());
        plot.getRangeAxis().setLabelFont(getLegendFont());
        plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
    }

    /**
     * Gets the number of Items in the chart.
     */
    protected int getItemsCount() {
        return getDataset().getRowCount();
    }
}