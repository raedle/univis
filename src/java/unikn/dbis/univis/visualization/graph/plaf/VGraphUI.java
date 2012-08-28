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
package unikn.dbis.univis.visualization.graph.plaf;

import org.jgraph.plaf.basic.BasicGraphUI;
import org.jfree.chart.editor.ChartEditor;
import org.jfree.chart.editor.ChartEditorManager;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;

import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

import unikn.dbis.univis.visualization.graph.VHintButton;
import unikn.dbis.univis.visualization.graph.VGraphCell;
import unikn.dbis.univis.visualization.graph.VLegend;
import unikn.dbis.univis.visualization.chart.VChartPanel;
import unikn.dbis.univis.icon.VIcons;

/**
 * TODO: document me!!!
 * <p/>
 * <code>VGraphUI</code>.
 * <p/>
 * User: raedler, weiler
 * Date: 22.05.2006
 * Time: 23:02:10
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: VGraphUI.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.1
 */
public class VGraphUI extends BasicGraphUI {

    /**
     * Creates the listener responsible for calling the correct handlers based
     * on mouse events, and to select invidual cells.
     */
    protected MouseListener createMouseListener() {

        return new MouseHandler() {

            /**
             * Invoked when a mouse button has been pressed on a component.
             *
             //@Override
             public void mousePressed(MouseEvent e) {

             Object o = graph.getFirstCellForLocation(e.getX(), e.getY());

             if (o instanceof VGraphCell) {

             VGraphCell cell = (VGraphCell) o;

             JPopupMenu menu = new JPopupMenu();

             VChartPanel chartPanel = (VChartPanel) cell.getUserObject();

             if (chartPanel.isShowPopUp()) {

             LegendItemCollection collect = chartPanel.getChart().getPlot().getLegendItems();
             JMenu first = new JMenu("1-39");
             int checker = 0;

             for (Iterator iter = collect.iterator(); iter.hasNext();) {
             LegendItem item = (LegendItem) iter.next();
             checker++;
             first.add(new JMenuItem(item.getLabel()));
             if ((checker % 40) == 0) {
             menu.add(first);

             first = new JMenu("" + checker + "-" + (checker + 39));
             }
             if (!iter.hasNext()) {
             menu.add(first);
             }
             }

             menu.show(graph, e.getX(), e.getY());
             }
             }

             super.mousePressed(e);
             }
             */

            /**
             * Invoked when a mouse button has been pressed on a component.
             */
            @Override
            public void mousePressed(MouseEvent e) {

                Object o = graph.getFirstCellForLocation(e.getX(), e.getY());

                if (SwingUtilities.isLeftMouseButton(e) && e.isAltDown()) {
                    if (o instanceof VGraphCell) {

                        VGraphCell cell = (VGraphCell) o;

                        JPopupMenu menu = new JPopupMenu();

                        VChartPanel chartPanel = (VChartPanel) cell.getUserObject();

                        LegendItemCollection collect = chartPanel.getChart().getPlot().getLegendItems();
                        JMenu first = new JMenu("1-39");
                        int checker = 0;

                        for (Iterator iter = collect.iterator(); iter.hasNext();) {
                            LegendItem item = (LegendItem) iter.next();
                            checker++;
                            first.add(new JMenuItem(item.getLabel()));
                            if ((checker % 40) == 0) {
                                menu.add(first);

                                first = new JMenu("" + checker + "-" + (checker + 39));
                            }
                            if (!iter.hasNext()) {
                                menu.add(first);
                            }
                        }

                        menu.show(graph, e.getX(), e.getY());
                    }
                }

                super.mousePressed(e);

                if (o != null && o instanceof VGraphCell) {

                    VGraphCell cell = (VGraphCell) o;

                    o = cell.getUserObject();

                    if (o != null && o instanceof VChartPanel) {
                        VChartPanel chart = (VChartPanel) o;

                        for (MouseListener l : chart.getMouseListeners()) {
                            l.mousePressed(e);
                        }
                    }
                }
            }

            // Event may be null when called to cancel the current operation.
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                Object o = graph.getFirstCellForLocation(e.getX(), e.getY());

                if (o != null && o instanceof VGraphCell) {

                    VGraphCell cell = (VGraphCell) o;

                    o = cell.getUserObject();

                    if (o != null && o instanceof VChartPanel) {
                        VChartPanel chart = (VChartPanel) o;

                        for (MouseListener l : chart.getMouseListeners()) {
                            l.mouseReleased(e);
                        }
                    }
                }
            }

            /**
             * Invoked when the mouse has been clicked on a component.
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                Object o = graph.getFirstCellForLocation(e.getX(), e.getY());

                if (o != null && o instanceof VGraphCell) {

                    VGraphCell cell = (VGraphCell) o;

                    o = cell.getUserObject();

                    if (o != null && o instanceof VChartPanel) {
                        VChartPanel chart = (VChartPanel) o;

                        if (SwingUtilities.isRightMouseButton(e)) {
                            JPopupMenu menu = chart.createPopupMenu(true, true, true, true);

                            menu.show(graph, e.getX(), e.getY());
                        }

                        /*
                        for (MouseListener l : chart.getMouseListeners()) {
                            System.out.println("LISTENS CLI");
                            l.mouseClicked(e);
                        }
                        */
                    }
                }
            }

            /*
            // Event may be null when called to cancel the current operation.
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                Object[] cells = graphSelectionModel.getSelectionCells();

                Rectangle2D bounds = graph.getCellBounds(cells);

                if (bounds != null) {
                    Rectangle2D b2 = graph.toScreen((Rectangle2D) bounds.clone());
                    graph.scrollRectToVisible(new Rectangle((int) b2.getX(), (int) b2.getY(), (int) b2.getWidth(), (int) b2.getHeight()));
                }
            }
            */

            /**
             * Invoked when the mouse pointer has been moved on a component (with no
             * buttons down).
             */
            @Override
            public void mouseMoved(MouseEvent e) {

                if (graph.isMoveable()) {
                    super.mouseMoved(e);
                }

                Object o = graph.getFirstCellForLocation(e.getX(), e.getY());

                if (o != null && o instanceof VGraphCell) {

                    selectedCell = (VGraphCell) o;

                    Rectangle2D bounds = graph.getCellBounds(selectedCell);

                    menu.show(graph, (int) (bounds.getX() + bounds.getWidth()), (int) bounds.getY() + (int) (bounds.getHeight() - menu.getHeight()));
                }
            }
        };
    }

    private final JPopupMenu menu = new JPopupMenu();

    private VGraphCell selectedCell;

    public VGraphUI() {

        VHintButton zoomIn = new VHintButton(VIcons.ZOOM_IN);
        menu.add(zoomIn);
        zoomIn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                VGraphCell cell = selectedCell;

                Object o = cell.getUserObject();

                if (o != null && o instanceof VChartPanel) {
                    VChartPanel chart = (VChartPanel) o;

                    chart.zoomInBoth(0, 0);
                    graph.repaint();
                }
            }
        });

        VHintButton zoomOut = new VHintButton(VIcons.ZOOM_OUT);
        menu.add(zoomOut);
        zoomOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                VGraphCell cell = selectedCell;

                Object o = cell.getUserObject();

                if (o != null && o instanceof VChartPanel) {
                    VChartPanel chart = (VChartPanel) o;

                    chart.zoomOutBoth(0, 0);
                    graph.repaint();
                }
            }
        });

        VHintButton settings = new VHintButton(VIcons.APPLICATION_FORM_EDIT);
        menu.add(settings);
        settings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                VGraphCell cell = selectedCell;

                Object o = cell.getUserObject();

                if (o != null && o instanceof VChartPanel) {
                    VChartPanel chart = (VChartPanel) o;

                    ChartEditor editor = ChartEditorManager.getChartEditor(chart.getChart());
                    int result = JOptionPane.showConfirmDialog(graph.getParent(), editor, "Chart_Properties", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    if (result == JOptionPane.OK_OPTION) {
                        editor.updateChart(chart.getChart());
                        graph.repaint();
                    }
                }
            }
        });

        VHintButton legend = new VHintButton(VIcons.BRICKS);
        menu.add(legend);
        legend.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             */
            public void actionPerformed(ActionEvent e) {

                VGraphCell cell = selectedCell;

                Object o = cell.getUserObject();

                if (o != null && o instanceof VChartPanel) {
                    VChartPanel chart = (VChartPanel) o;

                    VLegend legend = new VLegend(chart.getChart());

                    JPopupMenu menu = new JPopupMenu();
                    menu.add(legend);

                    menu.show(graph, 0, 0);
                }
            }
        });
    }
}
