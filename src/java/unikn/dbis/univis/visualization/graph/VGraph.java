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

import org.jgraph.JGraph;
import org.jgraph.graph.*;

import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.AbstractDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.category.CategoryDataset;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.*;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.io.IOException;
import java.sql.*;
import java.text.MessageFormat;

import unikn.dbis.univis.visualization.chart.*;
import unikn.dbis.univis.visualization.graph.plaf.VGraphUI;
import unikn.dbis.univis.visualization.Visualizable;
import unikn.dbis.univis.dnd.VDataReferenceFlavor;
import unikn.dbis.univis.meta.VDimension;
import unikn.dbis.univis.meta.VDataReference;
import unikn.dbis.univis.meta.VCombination;
import unikn.dbis.univis.meta.VCube;
import unikn.dbis.univis.explorer.VExplorer;
import unikn.dbis.univis.sql.VQuery;
import unikn.dbis.univis.sql.CubeChangeException;
import unikn.dbis.univis.sql.CubeChooser;
import unikn.dbis.univis.message.MessageResolver;

import javax.swing.*;

import com.jgraph.layout.JGraphFacade;
import com.jgraph.layout.tree.JGraphTreeLayout;

/**
 * TODO: document me!!!
 * <p/>
 * <code>VGraph</code>.
 * <p/>
 * User: raedler, weiler
 * Date: 12.04.2006
 * Time: 00:02:10
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: VGraph.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.1
 */
public class VGraph extends JGraph implements Visualizable {

    // The logger to log info, error and other occuring messages
    // or exceptions.
    public static final transient Log LOG = LogFactory.getLog(VGraph.class);

    // Different Objects for the graph.
    private GraphModel model = new DefaultGraphModel();
    private GraphLayoutCache cache = new GraphLayoutCache(model, new VCellViewFactory());
    private JGraphFacade facade = new JGraphFacade(cache);
    private JGraphTreeLayout layout = new JGraphTreeLayout();
    private VGraphCell root = null;

    private VHistoryList<VGraphCell> cellHistory = new VHistoryList<VGraphCell>();

    // Datasets for creating Charts.
    private AbstractDataset dataset;

    // Strings for different topics.
    private ChartType chartType = ChartType.BAR_CHART_HORIZONTAL;
    private String rootHeadLine;
    private String xAxis = "Studenten";

    // Int values for different topics.
    private int cellsize = 300;

    // The <code>VQuery</code> to get the sql statements to perform
    // exploring.
    private VQuery queryHistory = new VQuery();

    private List<VDimension> dimensions = new ArrayList<VDimension>();

    /**
     * Creates a new VGraph with drop target action and a
     * tree layout cache.
     */
    public VGraph() {
        setUI(new VGraphUI());

        setDropTarget(new DropTarget(this, DnDConstants.ACTION_COPY, new VGraphDropTargetListener()));

        setModel(model);
        setGraphLayoutCache(cache);
        setEditable(false);
        setMoveable(false);
        setSelectionEnabled(true);

        layout.setOrientation(SwingConstants.NORTH);
    }

    /**
     * @return a DefaultGraphCell
     */
    public VGraphCell createVertex(String chartName, String id) {

        AbstractChart chart;
        if (ChartType.BAR_CHART_VERTICAL.equals(chartType) || ChartType.BAR_CHART_HORIZONTAL.equals(chartType)) {
            chart = new BarChart(chartName, (CategoryDataset) dataset, xAxis, chartType);
        }
        else if (ChartType.AREA_CHART.equals(chartType)) {
            chart = new AreaChart(chartName, (CategoryDataset) dataset, xAxis);
        }
        else if (ChartType.RING_CHART.equals(chartType)) {
            chart = new RingChart(chartName, (PieDataset) dataset);
        }
        else {
            chart = new PieChart(chartName, (PieDataset) dataset);
        }

        // Create vertex with the given name
        VGraphCell cell = new VGraphCell(chart);
        cell.setCellId(id);

        // Set bounds
        GraphConstants.setBounds(cell.getAttributes(), new Rectangle2D.Double(chart.getX(), chart.getY(), cellsize, cellsize));

        // Add a Floating Port
        cell.addPort(new Point2D.Double(500, 0));
        cell.addPort(new Point2D.Double(500, 1000));
        cell.addPort(new Point2D.Double(0, 500));
        cell.addPort(new Point2D.Double(1000, 500));

        return cell;
    }

    /**
     * @param source Source where the edge is starting.
     * @param target Target where the edge is ending.
     */
    public void createEdges(VGraphCell source, VGraphCell target) {
        DefaultEdge edge = new DefaultEdge();
        if (layout.getOrientation() == SwingConstants.NORTH) {
            edge.setSource(source.getChildAt(1));
            edge.setTarget(target.getChildAt(0));
        }
        else if (layout.getOrientation() == SwingConstants.WEST) {
            edge.setSource(source.getChildAt(3));
            edge.setTarget(target.getChildAt(2));
        }
        else if (layout.getOrientation() == SwingConstants.SOUTH) {
            edge.setSource(source.getChildAt(0));
            edge.setTarget(target.getChildAt(1));

        }
        else if (layout.getOrientation() == SwingConstants.EAST) {
            edge.setSource(source.getChildAt(2));
            edge.setTarget(target.getChildAt(3));
        }
        GraphConstants.setLineEnd(edge.getAttributes(), GraphConstants.ARROW_CLASSIC);
        GraphConstants.setEndFill(edge.getAttributes(), true);
        cache.insert(edge);
    }

    public void createEdges(VGraphCell target, String targetId) {

        String sourceId = "";
        if (targetId.lastIndexOf("_") != -1) {
            sourceId = targetId.substring(0, targetId.lastIndexOf("_"));
        }

        VGraphCell source = root;
        for (VGraphCell cell : cellHistory.getLastOfHistory()) {
            if (sourceId.equals(cell.getCellId())) {
                source = cell;
                break;
            }
        }

        createEdges(source, target);
    }

    /**
     * @param result The given resultset from the sql action.
     * @throws SQLException
     */
    public void fillChartData(VDimension dimension, ResultSet result, ResultSet testResult) throws SQLException {

        layout.setAlignment(SwingConstants.CENTER);

        ResultSetMetaData data = result.getMetaData();
        int idPos = data.getColumnCount();
        int namePos = idPos - 1;
        int bufferPos = namePos - 1;

        List<String> testList = new ArrayList<String>();

        while (testResult.next()) {
            testList.add(testResult.getString(1));
        }

        List<String> helpList = new ArrayList<String>(testList);

        if (root == null) {

            cellHistory.historize();

            if (ChartType.BAR_CHART_VERTICAL.equals(chartType) || ChartType.BAR_CHART_HORIZONTAL.equals(chartType) || ChartType.AREA_CHART.equals(chartType))
            {
                dataset = new DefaultCategoryDataset();

                while (result.next()) {
                    ((DefaultCategoryDataset) dataset).addValue(result.getInt(1), result.getString(namePos + 1), "");
                }
            }
            else {
                dataset = new DefaultPieDataset();

                while (result.next()) {
                    ((DefaultPieDataset) dataset).setValue(result.getString(namePos + 1), result.getInt(1));
                }
            }

            root = createVertex(rootHeadLine, "");
            root.setCellId("root");
            cache.insert(root);
            cellHistory.add(root);
        }
        else {
            cellHistory.historize();

            String buffer = "";
            if (ChartType.BAR_CHART_VERTICAL.equals(chartType) || ChartType.BAR_CHART_HORIZONTAL.equals(chartType) || ChartType.AREA_CHART.equals(chartType))
            {
                while (result.next()) {

                    String currentValue = result.getString(idPos);

                    if (!buffer.equals(currentValue)) {

                        if (!result.isFirst()) {
                            if (!helpList.isEmpty()) {
                                for (String missing : helpList) {
                                    ((DefaultCategoryDataset) dataset).addValue(0, missing, "");
                                }
                            }
                        }

                        dataset = new DefaultCategoryDataset();
                        VGraphCell nextCell = createVertex(MessageResolver.getMessage("data_reference." + dimension.getI18nKey()) + " (" + result.getString(bufferPos) + ")", result.getString(idPos));
                        createEdges(nextCell, result.getString(idPos));
                        cache.insert(nextCell);
                        cellHistory.add(nextCell);
                        helpList = new ArrayList<String>(testList);
                    }

                    for (String available : testList) {
                        if (result.getString(namePos).equals(available)) {
                            helpList.remove(available);
                        }
                    }

                    ((DefaultCategoryDataset) dataset).addValue(result.getInt(1), result.getString(namePos), "");
                    buffer = currentValue;

                    if (result.isLast()) {
                        if (!helpList.isEmpty()) {
                            for (String missing : helpList) {
                                ((DefaultCategoryDataset) dataset).addValue(0, missing, "");
                            }
                        }
                    }
                }

            }
            else {
                while (result.next()) {

                    String currentValue = result.getString(idPos);

                    LOG.info(result.getString(2));

                    if (!buffer.equals(currentValue)) {

                        dataset = new DefaultPieDataset();

                        VGraphCell nextCell = createVertex(MessageResolver.getMessage("data_reference." + dimension.getI18nKey()) + " (" + result.getString(bufferPos) + ")", result.getString(idPos));
                        createEdges(nextCell, result.getString(idPos));
                        cache.insert(nextCell);
                        cellHistory.add(nextCell);
                    }

                    ((DefaultPieDataset) dataset).setValue(result.getString(namePos), result.getInt(1));

                    buffer = currentValue;
                }
            }
        }
        layout.run(facade);
        facade.setOrdered(true);
        Map nested = facade.createNestedMap(true, true);
        cache.edit(nested);
    }

    /**
     * Adds a <code>VDimension</code> to the visualization of the graph. This
     * method creates a sql statement to get the data that is needed to display
     * the cell(s).
     *
     * @param dimension The given VDimension from the drag & drop.
     * @throws SQLException The exception occurs while trying to get the data
     *                      for displaying on graph.
     */
    public void addDimension(VDimension dimension) throws SQLException {

        dimensions.add(dimension);

        Connection connection = VExplorer.getConnection();

        Statement statement = connection.createStatement();
        Statement statement2 = connection.createStatement();

        VDimension blueprint = searchBlueprint(dimension);

        String sql = queryHistory.createChartQuery(dimension, blueprint);
        String testSql = queryHistory.getItemCountSQL();

        ResultSet result = null;
        ResultSet testResult = null;
        try {
            result = statement.executeQuery(sql);
            testResult = statement2.executeQuery(testSql);
            rootHeadLine = MessageResolver.getMessage("data_reference." + dimension.getI18nKey());
            fillChartData(dimension, result, testResult);
        }
        catch (SQLException sqle) {
            queryHistory.historyBack();
            VExplorer.publishException(sqle);
            if (LOG.isErrorEnabled()) {
                LOG.error(sqle.getMessage(), sqle);
            }
        }
        finally {
            if (result != null) {
                result.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (testResult != null) {
                testResult.close();
            }

            if (statement2 != null) {
                statement2.close();
            }
        }
    }

    /**
     * @param dimension VDimension from the drag & drop.
     * @return VDimension which is the bluep of the Dimension
     */
    private VDimension searchBlueprint(VDimension dimension) {

        if (dimension.getChildren().size() == 0) {
            return dimension;
        }

        VDimension blueprint = null;
        for (VDataReference vDataReference : dimension.getChildren()) {
            blueprint = searchBlueprint((VDimension) vDataReference);
        }

        return blueprint;
    }

    public void clear() {
        root = null;

        model = new DefaultGraphModel();
        cache = new GraphLayoutCache(model, new VCellViewFactory());
        facade = new JGraphFacade(cache);
        layout = new JGraphTreeLayout();

        setModel(model);
        setGraphLayoutCache(cache);

        facade.setOrdered(true);

        for (VDimension dimension : dimensions) {
            setAncestorsDropped(dimension, false);
        }

        queryHistory.reset();
        cellHistory.reset();
    }

    public void undo() {
        cache.remove(cellHistory.getCurrent().toArray(), true, true);

        // Get last added dimension and remove it.
        VDimension dimension = dimensions.remove(dimensions.size() - 1);

        // Reset the last drag and dropped dimension.
        setAncestorsDropped(dimension, false);

        cellHistory.historyBack();
        queryHistory.historyBack();

        if (queryHistory.isEmpty()) {
            clear();
        }

        reloadGraph();
    }

    public void redo() {
        throw new UnsupportedOperationException("The redo functionality isn't supported by " + getClass().getName());
    }

    public void rotateRight() {
        if ((SwingConstants.NORTH) == getLayoutOrientation()) {
            setLayoutOrientation(SwingConstants.EAST);
        }
        else if ((SwingConstants.EAST) == getLayoutOrientation()) {
            setLayoutOrientation(SwingConstants.SOUTH);
        }
        else if ((SwingConstants.SOUTH) == getLayoutOrientation()) {
            setLayoutOrientation(SwingConstants.WEST);
        }
        else if ((SwingConstants.WEST) == getLayoutOrientation()) {
            setLayoutOrientation(SwingConstants.NORTH);
        }

        rotateGraph();
    }

    public void rotateLeft() {
        if ((SwingConstants.NORTH) == getLayoutOrientation()) {
            setLayoutOrientation(SwingConstants.WEST);
        }
        else if ((SwingConstants.WEST) == getLayoutOrientation()) {
            setLayoutOrientation(SwingConstants.SOUTH);
        }
        else if ((SwingConstants.SOUTH) == getLayoutOrientation()) {
            setLayoutOrientation(SwingConstants.EAST);
        }
        else if ((SwingConstants.EAST) == getLayoutOrientation()) {
            setLayoutOrientation(SwingConstants.NORTH);
        }

        rotateGraph();
    }

    private void rotateGraph() {
        Object cells[] = cache.getCells(false, true, false, false);
        Object edges[] = cache.getCells(false, false, false, true);

        reloadGraph();
        cache.remove(edges);

        for (Object cell1 : cells) {

            VGraphCell cell = (VGraphCell) cell1;
            if (!cell.toString().equals("root")) {
                createEdges(cell, cell.getCellId());
            }
        }
    }

    private double zoomScale = 1.0;

    public void zoomIn() {
        this.setScale(zoomScale += 0.05);
    }

    public void zoomOut() {
        this.setScale(zoomScale -= 0.05);
    }

    public void reloadGraph() {
        layout.run(facade);
        facade.setOrdered(true);
        Map nested = facade.createNestedMap(true, true);
        cache.edit(nested);
    }

    public void setLayoutOrientation(int orientation) {
        layout.setOrientation(orientation);
    }

    public int getLayoutOrientation() {
        return layout.getOrientation();
    }

    public VQuery getQueryHistory() {
        return queryHistory;
    }

    public VHistoryList<VGraphCell> getCellHistory() {
        return cellHistory;
    }

    public void setxAxis(String xAxis) {
        this.xAxis = xAxis;
    }

    public void setChartType(ChartType chartType) {
        this.chartType = chartType;
    }

    /**
     * The drop target listener for the <code>VGraph</code> that allows
     * to perform dimension agregation action.
     */
    public final class VGraphDropTargetListener extends DropTargetAdapter {

        /**
         * Action when a <code>VDimension<code> is dropped into the
         * graph.
         *
         * @param dtde The drop target event.
         */
        public void drop(DropTargetDropEvent dtde) {

            Object o = null;
            try {
                if (dtde.getTransferable().isDataFlavorSupported(VDataReferenceFlavor.COMBINATION_FLAVOR)) {
                    o = dtde.getTransferable().getTransferData(VDataReferenceFlavor.COMBINATION_FLAVOR);
                }
            }
            catch (UnsupportedFlavorException ufe) {
                dtde.rejectDrop();
                dtde.dropComplete(false);
                if (LOG.isErrorEnabled()) {
                    LOG.error(ufe.getMessage(), ufe);
                }
            }
            catch (IOException ioe) {
                dtde.rejectDrop();
                dtde.dropComplete(false);
                if (LOG.isErrorEnabled()) {
                    LOG.error(ioe.getMessage(), ioe);
                }
            }
            if (o instanceof VCombination) {
                VCombination combination = (VCombination) o;

                try {

                    VDimension dimension = combination.getDimension();

                    dtde.acceptDrop(DnDConstants.ACTION_COPY);

                    Set<VCube> supportedCubes = dimension.getSupportedCubes();

                    VCube cube;
                    if (!queryHistory.containsCube()) {
                        if (supportedCubes.size() > 1) {
                            cube = CubeChooser.showCubeChooser(VGraph.this, supportedCubes);
                        }
                        else {
                            cube = supportedCubes.iterator().next();
                        }

                        // Check whether a cube exists or a cube has been selected on the
                        // cube chooser. The chooser maybe interrupted with window close
                        // and then return null.
                        if (cube == null) {
                            return;
                        }

                        try {

                            if (cube.toString().equals("Studenten") || cube.toString().equals("Students")) {
                                xAxis = "Studenten";
                            }
                            else {
                                xAxis = "Euro";
                            }
                            queryHistory.setCube(cube);
                        }
                        catch (CubeChangeException e) {
                            // Do nothing if query history currently using a
                            // cube for exploration.
                        }
                    }

                    // Merge function [e.g. SUM({0})] with measure [e.g. cases]. E.g. merge result [SUM(cases)]
                    String cubeAttribute = MessageFormat.format(combination.getFunction().getDefinition(), combination.getMeasure().getColumn());
                    queryHistory.setCubeAttribute(cubeAttribute);

                    addDimension(dimension);
                    setAncestorsDropped(dimension, true);
                }
                catch (SQLException sqle) {
                    dtde.rejectDrop();
                    dtde.dropComplete(false);
                    if (LOG.isErrorEnabled()) {
                        LOG.error(sqle.getMessage(), sqle);
                    }
                }
            }
            dtde.dropComplete(true);
        }
    }

    private void setAncestorsDropped(VDimension dimension, boolean visibility) {

        dimension.getSelections().clear();
        dimension.setDropped(visibility);

        VDataReference dataReference = dimension.getParent();

        if (dataReference instanceof VDimension) {
            setAncestorsDropped((VDimension) dataReference, visibility);
        }
    }
}
