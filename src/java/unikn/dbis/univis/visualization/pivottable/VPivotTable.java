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
package unikn.dbis.univis.visualization.pivottable;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.util.Set;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.table.TableColumn;

import table.model.renderer.SimpleRenderer;
import unikn.dbis.univis.dnd.VDataReferenceFlavor;
import unikn.dbis.univis.meta.*;
import unikn.dbis.univis.sql.CubeChooser;
import unikn.dbis.univis.visualization.Visualizable;
//import unikn.dbis.univis.pivot.view.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * TODO: document me!!!
 * <p/>
 * The <code>VPivotTable</code> organizes the areas to drop dimensions for X-/Y-Axis
 * and calculation of the pivot table data for pivot table visualization.
 * <p/>
 * User: herb, raedler
 * Date: 28.08.2006
 * Time: 17:06:14
 *
 * @author Roman R&auml;dle
 * @author Marion Herb
 * @version $Id: VPivotTable.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.2
 */
public class VPivotTable extends JPanel implements Visualizable, DropTargetListener {

    // The logger to log info, error and other occuring messages
    // or exceptions.
    public static final transient Log LOG = LogFactory.getLog(VPivotTable.class);

    // SQL neu generieren und ausgeführen und Pivottabelle neu zeichnen? 
    boolean selectionChanged = false;

    private VCube cube;
    private VMeasure measure;
    private VFunction function;

    private JScrollPane tableScrollPane;

    JPanel panelFirstRow;
    JPanel panelSecondRow;
    JPanel panelThirdRow;
    JTextArea dropAreaX;
    JTextArea dropAreaY;
    JTextArea dropAreaMeasure;

    VTableSorter tableSorter;
    JTable pivotTable;

    JScrollBar bar;

    String textInXYBox = "Drop Element(s)!";
    String textInMeasureBox = "Choose Measure!";

    Vector<VDimension> xAxisDimensions = new Vector<VDimension>();
    Vector<VDimension> yAxisDimensions = new Vector<VDimension>();
    Vector<VDimension> measureNodes = new Vector<VDimension>();

    VPivotTableData uniData;

    public VPivotTable() {
        setBorder(new EtchedBorder());
        setPreferredSize(new Dimension(1000, 700));
        setLayout(new BorderLayout());
        setBackground(Color.white);

        /* *** First Row *** */
        JLabel jlabelMeasure = new JLabel("Measure:");
        jlabelMeasure.setVisible(false);
        JLabel jlabelX = new JLabel("X-Axis Elements:");
        JLabel forVerticalSpace = new JLabel(" ");
        panelFirstRow = new JPanel(new VTableLayout(3, 2, 3, 3));

        dropAreaMeasure = new JTextArea(textInMeasureBox);
        dropAreaMeasure.setToolTipText(textInMeasureBox);
        dropAreaMeasure.setForeground(Color.LIGHT_GRAY);
        dropAreaMeasure.setEditable(false);
        dropAreaMeasure.setPreferredSize(new Dimension(100, 20));
        dropAreaMeasure.setBorder(BorderFactory.createEtchedBorder(Color.WHITE, Color.LIGHT_GRAY));
        dropAreaMeasure.setVisible(false);

        dropAreaX = new JTextArea(textInXYBox);
        dropAreaX.setToolTipText(textInXYBox);
        dropAreaX.setForeground(Color.LIGHT_GRAY);
        dropAreaX.setEditable(false);
        dropAreaX.setPreferredSize(new Dimension(400, 20));
        dropAreaX.setBorder(BorderFactory.createEtchedBorder(Color.WHITE, Color.LIGHT_GRAY));

        panelFirstRow.add(jlabelMeasure);
        panelFirstRow.add(jlabelX);
        panelFirstRow.add(dropAreaMeasure);
        panelFirstRow.add(dropAreaX);
        panelFirstRow.add(forVerticalSpace);
        add(panelFirstRow, BorderLayout.NORTH);

        /* *** Second Row *** */
        JLabel jlabelY1 = new JLabel("Y-Axis");
        JLabel jlabelY2 = new JLabel("Elements:");
        panelSecondRow = new JPanel(new VTableLayout(1, 2, 3, 3));

        dropAreaY = new JTextArea(textInXYBox);
        dropAreaY.setToolTipText(textInXYBox);
        dropAreaY.setForeground(Color.LIGHT_GRAY);
        dropAreaY.setEditable(false);
        dropAreaY.setLineWrap(true);
        dropAreaY.setWrapStyleWord(true);
        dropAreaY.setPreferredSize(new Dimension(100, 400));
        dropAreaY.setBorder(BorderFactory.createEtchedBorder(Color.WHITE, Color.LIGHT_GRAY));

        JPanel panelY = new JPanel(new VTableLayout(3, 1, 1, 1));
        panelY.add(jlabelY1);
        panelY.add(jlabelY2);
        panelY.add(dropAreaY);
        panelSecondRow.add(panelY);
        add(panelSecondRow, BorderLayout.WEST);

        new DropTarget(dropAreaX, DnDConstants.ACTION_COPY_OR_MOVE, this);
        new DropTarget(dropAreaY, DnDConstants.ACTION_COPY_OR_MOVE, this);
    }

    /**
     * Called when the drag operation has terminated with a drop on
     * the operable part of the drop site for the <code>DropTarget</code>
     * registered with this listener.
     * <p/>
     * This method is responsible for undertaking
     * the transfer of the data associated with the
     * gesture. The <code>DropTargetDropEvent</code>
     * provides a means to obtain a <code>Transferable</code>
     * object that represents the data object(s) to
     * be transfered.<P>
     * From this method, the <code>DropTargetListener</code>
     * shall accept or reject the drop via the
     * acceptDrop(int dropAction) or rejectDrop() methods of the
     * <code>DropTargetDropEvent</code> parameter.
     * <p/>
     * Subsequent to acceptDrop(), but not before,
     * <code>DropTargetDropEvent</code>'s getTransferable()
     * method may be invoked, and data transfer may be
     * performed via the returned <code>Transferable</code>'s
     * getTransferData() method.
     * <p/>
     * At the completion of a drop, an implementation
     * of this method is required to signal the success/failure
     * of the drop by passing an appropriate
     * <code>boolean</code> to the <code>DropTargetDropEvent</code>'s
     * dropComplete(boolean success) method.
     * <p/>
     * Note: The data transfer should be completed before the call  to the
     * <code>DropTargetDropEvent</code>'s dropComplete(boolean success) method.
     * After that, a call to the getTransferData() method of the
     * <code>Transferable</code> returned by
     * <code>DropTargetDropEvent.getTransferable()</code> is guaranteed to
     * succeed only if the data transfer is local; that is, only if
     * <code>DropTargetDropEvent.isLocalTransfer()</code> returns
     * <code>true</code>. Otherwise, the behavior of the call is
     * implementation-dependent.
     * <p/>
     *
     * @param dtde the <code>DropTargetDropEvent</code>
     */

    public void drop(DropTargetDropEvent dtde) {
        Object o = null;
        try {
            if (dtde.getTransferable().isDataFlavorSupported(VDataReferenceFlavor.COMBINATION_FLAVOR)) {
                o = dtde.getTransferable().getTransferData(VDataReferenceFlavor.COMBINATION_FLAVOR);
            }

            if (o == null) {
                dtde.rejectDrop();
                dtde.dropComplete(false);
            }
        }
        catch (UnsupportedFlavorException ufe) {
            dtde.rejectDrop();
            dtde.dropComplete(false);
            /*if (LOG.isErrorEnabled()) {
               LOG.error(ufe.getMessage(), ufe);
           } */
        }
        catch (IOException ioe) {
            dtde.rejectDrop();
            dtde.dropComplete(false);
            /*if (LOG.isErrorEnabled()) {
               LOG.error(ioe.getMessage(), ioe);
           } */
        }

        if (o instanceof VCombination) {
            VCombination combination = (VCombination) o;

            VDimension dimension = combination.getDimension();

            if (cube == null) {
                Set<VCube> supportedCubes = dimension.getSupportedCubes();
                if (supportedCubes.size() > 1) {
                    cube = CubeChooser.showCubeChooser(VPivotTable.this, supportedCubes);
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
            }

            if (measure == null) {
                measure = combination.getMeasure();
            }

            if (function == null) {
                function = combination.getFunction();
            }

            addDimension(dimension, dtde);
            setAncestorsDropped(dimension, true);
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

    /**
     * Called while a drag operation is ongoing, when the mouse pointer enters
     * the operable part of the drop site for the <code>DropTarget</code>
     * registered with this listener.
     *
     * @param dtde the <code>DropTargetDragEvent</code>
     */

    public void dragEnter(DropTargetDragEvent dtde) {
        // empty
    }

    /**
     * Called when a drag operation is ongoing, while the mouse pointer is still
     * over the operable part of the drop site for the <code>DropTarget</code>
     * registered with this listener.
     *
     * @param dtde the <code>DropTargetDragEvent</code>
     */

    public void dragOver(DropTargetDragEvent dtde) {
        // empty
    }

    /**
     * Called if the user has modified
     * the current drop gesture.
     * <p/>
     *
     * @param dtde the <code>DropTargetDragEvent</code>
     */

    public void dropActionChanged(DropTargetDragEvent dtde) {
        // empty
    }

    /**
     * Called while a drag operation is ongoing, when the mouse pointer has
     * exited the operable part of the drop site for the
     * <code>DropTarget</code> registered with this listener.
     *
     * @param dte the <code>DropTargetEvent</code>
     */

    public void dragExit(DropTargetEvent dte) {
        // empty
    }

    public void addDimension(VDimension dimension, DropTargetDropEvent dtde) {

        if (dtde.getSource() == dropAreaX.getDropTarget()) {

            if (xAxisDimensions.size() < 1) { // noch kein X-Achsen-Element spezifiziert

                if (LOG.isDebugEnabled()) {
                    LOG.debug("GUI: dropped as column value [" + dimension + "] Root: " + dimension.getBlueprint());
                }

                xAxisDimensions.add(dimension);

                // Shows the dropped dimensions in x drop area box.
                dropAreaX.setForeground(Color.BLACK);

                StringBuilder sb = new StringBuilder();
                for (VDimension d : xAxisDimensions) {
                    sb.append(d).append("\n");
                }
                dropAreaX.setText(sb.toString());

                selectionChanged = true;
            }
            else { // es wurde schon X-Achsen-Element festgelegt --> momentan nur 1 ausgewertet
                if (LOG.isErrorEnabled()) {
                    LOG.error("Currently only one Element for X-Axis allowed.");
                }
                selectionChanged = false;
            }

        } // end of dropAreaX (DropTarget)
        else if (dtde.getSource() == dropAreaY.getDropTarget()) {

            if (!yAxisDimensions.contains(dimension)) { // nur wenn noch nicht enthalten

                if (LOG.isDebugEnabled()) {
                    LOG.debug("GUI: dropped as row value [" + dimension + "] Root: " + dimension.getBlueprint());
                }

                yAxisDimensions.add(dimension);

                // Shows the dropped dimensions in y drop area box.
                dropAreaY.setForeground(Color.BLACK);

                StringBuilder sb = new StringBuilder();
                for (VDimension d : yAxisDimensions) {
                    sb.append(d).append("\n");
                }
                dropAreaY.setText(sb.toString());

                selectionChanged = true;
            }
            else {
                selectionChanged = false;
            }
        } // end of dropAreaY (DropTarget)

        if (selectionChanged && (xAxisDimensions.size() > 0 || yAxisDimensions.size() > 0)) {
            createPivotTable(false);
        }
    }

    /*
    * boolean flag legt fest welche Ansicht gezeigt wird
    * Entweder Pivottabelle oder Tabelle ohne Totalling
    */
    private void createPivotTable(boolean dataOnly) {

        // Removes the scroll pane that contains the pivottable.
        if (tableScrollPane != null) {
            remove(tableScrollPane);
        }

        // JTable anlegen mit Daten sowie Ueberschriften
        uniData = new VPivotTableData(cube, measure, function);
        Vector vectorData = uniData.getPivottableData(xAxisDimensions, yAxisDimensions);
        Vector vectorHeader = uniData.getPivottableHeader(xAxisDimensions, yAxisDimensions);

        // Sind denn ueberhaupt Daten zurueckgekommen?
        boolean dataGiven = ((vectorData != null) && (vectorData.size() > 0));
        boolean headerDataGiven = ((vectorHeader != null && vectorHeader.size() > 0));

        if (!dataGiven) {
            System.out.println("keine Daten!");
        }
        if (!headerDataGiven) {
            System.out.println("keine Ueberschriften!");
        }
        if (dataGiven & headerDataGiven) {

            VPivotTableModel modelOrigin = new VPivotTableModel(vectorData, vectorHeader);

            if (dataOnly) {
                tableSorter = new VTableSorter(modelOrigin);
                pivotTable = new JTable(tableSorter);
                tableSorter.setTableHeader(pivotTable.getTableHeader());
            }
            else {
                VEnvelopeTableModel modelEnv = new VEnvelopeTableModel(modelOrigin);
                pivotTable = new JTable(modelEnv);
                modelEnv.setTableHeader(pivotTable.getTableHeader());
            }

            SimpleRenderer renderer = new VNumberRenderer(false);
            pivotTable.setDefaultRenderer(String.class, renderer);
            // reine Number-Klasse hat Doubles nicht gefaerbt!
//            pivotTable.setDefaultRenderer(Number.class, renderer);
            pivotTable.setDefaultRenderer(Integer.class, renderer);
            pivotTable.setDefaultRenderer(Long.class, renderer);
            pivotTable.setDefaultRenderer(Double.class, renderer);

            // spezieller CellRenderer für letzte Total-Spalte
            TableColumn col = pivotTable.getColumnModel().getColumn(vectorHeader.size() - 1);
            col.setCellRenderer(new VTotalCellRenderer());

            /* *** Second Row --> Content Area *** */
            pivotTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

            //Set up tool tips for column headers.
//            pivotTable.getTableHeader().setToolTipText(
//                    "Click to specify sorting; Control-Click to specify secondary sorting");

            /* *** Second Row --> Content Area *** */
            pivotTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            tableScrollPane = new JScrollPane(pivotTable);
            tableScrollPane.setVerticalScrollBar(new JScrollBar());

            add(tableScrollPane, BorderLayout.CENTER);

            validate();
            repaint();
        }
    }

    ActionListener al_createTable = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            boolean dataOnly = false;
            createPivotTable(dataOnly);
        }
    };

    /* deletes Textfields and most of all JTable */
    ActionListener al_reset = new ActionListener() {
        public void actionPerformed(ActionEvent e) {

        }
    };

    ActionListener al_dataViewTable = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            boolean dataOnly = true;
            createPivotTable(dataOnly);
        }
    };

    public void clear() {

        if (!xAxisDimensions.isEmpty()) {
            for (VDimension dimension : xAxisDimensions) {
                setAncestorsDropped(dimension, false);
            }
        }
        if (!yAxisDimensions.isEmpty()) {
            for (VDimension dimension : yAxisDimensions) {
                setAncestorsDropped(dimension, false);
            }
        }

        xAxisDimensions.clear();
        yAxisDimensions.clear();
        measureNodes.clear();
        dropAreaX.setForeground(Color.LIGHT_GRAY);
        dropAreaX.setToolTipText(textInXYBox);
        dropAreaX.setText(textInXYBox);
        dropAreaY.setForeground(Color.LIGHT_GRAY);
        dropAreaY.setToolTipText(textInXYBox);
        dropAreaY.setText(textInXYBox);
        dropAreaMeasure.setForeground(Color.LIGHT_GRAY);
        dropAreaMeasure.setToolTipText(textInMeasureBox);
        dropAreaMeasure.setText(textInMeasureBox);

        if (tableScrollPane != null) {
            remove(tableScrollPane);
        }

        validate();
        repaint();
    }

    public void undo() {
        throw new UnsupportedOperationException("The undo functionality isn't supported by " + getClass().getName());
    }

    public void redo() {
        throw new UnsupportedOperationException("The redo functionality isn't supported by " + getClass().getName());
    }

    public void zoomIn() {
        throw new UnsupportedOperationException("The zoom into visualization isn't supported by " + getClass().getName());
    }

    public void zoomOut() {
        throw new UnsupportedOperationException("The zoom out of visualization isn't supported by " + getClass().getName());
    }

    public void rotateRight() {
        throw new UnsupportedOperationException("The clockwise rotation of the visualization isn't supported by " + getClass().getName());
    }

    public void rotateLeft() {
        throw new UnsupportedOperationException("The anticlockwise rotation of the visualization isn't supported by " + getClass().getName());
    }

    public void setMoveable(boolean movable) {
        throw new UnsupportedOperationException("The moveing of the visualization isn't supported by " + getClass().getName());
    }

    public boolean isMoveable() {
        throw new UnsupportedOperationException("The visualization isn't moveable at this time. See " + getClass().getName());
    }
}
