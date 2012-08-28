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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import table.model.EnvelopeTableModel;
import table.model.exception.GroupException;

/**
 * @author Marion Herb
 *         <p/>
 *         Wrapper
 */
public class VEnvelopeTableModel extends table.model.EnvelopeTableModel {

    private JTableHeader tableHeader;

    public VEnvelopeTableModel(TableModel source) {
        super(source);

        // User kann mehr Elemente droppen als angezeigt werden, zB Laender 2x
        // deswegen vectorData durchlaufen bis Zahl
        // --> alles davor sind Strings also Header-Zeilen
        int colNumber = this.originalModel.getColumnCount();
        int groupLevels = 0;
        for (int i = 0; i < colNumber; i++) {
            Object v = this.originalModel.getValueAt(0, i);
            Class myClass = v.getClass();
            if (myClass.equals(String.class)) {
                groupLevels++;
            }
            else {
                break;
            }
        }
//		System.out.println("------- Groupings: " + groupLevels);
//    	System.out.println("------- ColNumber: " + colNumber);

        // falls gar keine Werte in Y-Achse stehen -> NegativeArraySizeException
        int[] groupColumnIndexes;
        if (groupLevels == 0) {
            groupColumnIndexes = new int[0];
        }
        else {
            groupColumnIndexes = new int[groupLevels - 1];
        }
        boolean[]  groupColumnOrders;
        if (groupLevels == 0) {
            groupColumnOrders = new boolean[0];
        }
        else {
            groupColumnOrders = new boolean[groupLevels - 1];
        }

//    	int[] groupColumnIndexes = new int[groupLevels - 1];
//    	boolean[] groupColumnOrders = new boolean[groupLevels - 1];
        for (int i = 0; i < groupColumnIndexes.length; i++) {
            groupColumnIndexes[i] = i;
//			System.out.print("i=" + i + " groupColumnIndexes[" + i + "]=" + groupColumnIndexes[i]);
            groupColumnOrders[i] = true;
//			System.out.println(" groupColumnOrders[" + i + "]=" + groupColumnOrders[i]);
        }
        try {
            //modelEnv.setGroup(new int[]{0}, new boolean[]{true});
            //modelEnv.setGroupFunction(2, EnvelopeTableModel.GROUP_FUNCTION_SUM);
            //modelEnv.setGroupFunction(3, EnvelopeTableModel.GROUP_FUNCTION_SUM);

            setGroup(groupColumnIndexes, groupColumnOrders);

            for (int i = 0; i < colNumber; i++) {
                if (i < groupLevels) {
                    continue;
                }
//				System.out.println("setGroupFunction(" + i + ")");
                setGroupFunction(i, EnvelopeTableModel.GROUP_FUNCTION_SUM);
            }
        }
        catch (GroupException e) {
            e.printStackTrace();
        }

        // -> Gruppieren und Sortieren geht nie gleichzeitig!
//    	int[] sortColumnIndexes = new int[groupColumnIndexes.length + 1];
//    	boolean[] sortColumnOrders = new boolean[groupColumnIndexes.length + 1];    	
//    	for (int i = 0; i < sortColumnIndexes.length; i++) {
//    		sortColumnIndexes[i] = i;
//			System.out.print("i=" + i + " sortColumnIndexes[" + i + "]=" + sortColumnIndexes[i]);
//			sortColumnOrders[i] = true;
//			System.out.println(" sortColumnOrders[" + i + "]=" + sortColumnOrders[i]);
//		}
//		try {
//			setSort( sortColumnIndexes, sortColumnOrders );
//		} catch (SortException e) {
//			e.printStackTrace();
//		}
    }

    /*
      * aktuellen Wert aus Oberklasse table.model.EnvelopeTableModel holen
      * tatsächlich angezeigt wird dann der der hier berechnet wird
      * also evtl. Leerzeichen wenn sich die Ueberschrift in der Gruppierung wiederholt
      */
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value = super.getValueAt(rowIndex, columnIndex);

        // Empty Cell for repeated values
        if ((isString(value)) && (rowIndex > 0)) {// 1. Reihe wird niemals ausgegraut sein
            Object valuePrev = super.getValueAt(rowIndex - 1, columnIndex);
            if (valuePrev != null) {
                if (valuePrev.equals(value)) {
                    value = "";
                }
            }
            else { // vorhergehender Wert in Zelle darueber ist null -> subtotal
                // jetzt ueberpruefen ob Wert vor Subtotal-Zelle derselbe ist
                // wenn ja, dann nochmals ausgrauen!
                Object valuePrevPrev = super.getValueAt(rowIndex - 2, columnIndex);
                if ((valuePrevPrev != null) && (valuePrevPrev.equals(value))) {
                    value = "";
                }
            }
        }

        return value;
    }

    // checks whether Object contains String or not
    private boolean isString(Object value) {
        if (value == null) {
            return false;
        }
        Class myClass = value.getClass();
        return myClass.equals(String.class);
    }

    public void setTableHeader(JTableHeader tableHeader) {
        this.tableHeader = tableHeader;
        if (this.tableHeader != null) {
            this.tableHeader.addMouseListener(new MouseHandler());
        }
    }

    private class MouseHandler extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            JTableHeader h = (JTableHeader) e.getSource();
            TableColumnModel columnModel = h.getColumnModel();
            int viewColumn = columnModel.getColumnIndexAtX(e.getX());
            int column = columnModel.getColumn(viewColumn).getModelIndex();
//			System.out.println("Column clicked: " + column);
            if (column != -1) {

//				Sort oder Group - geht immer nur eins auf einmal
//				try {
//					setSort( new int[]{column}, new boolean[]{false} );
//				} catch (SortException e2) {
//					e2.printStackTrace();
//				}

//				int[] groupColumns = getGroupColumns();
//				boolean[] groupOrders = getGroupOrders();
//				groupOrders[column] = false;
//				try {
//					setGroup(groupColumns, groupOrders);
//				} catch (GroupException e1) {
//					e1.printStackTrace();
//				}

//	                int status = getSortingStatus(column);
//	                if (!e.isControlDown()) {
//	                    cancelSorting();
//	                }
//	                // Cycle the sorting states through {NOT_SORTED, ASCENDING, DESCENDING} or 
//	                // {NOT_SORTED, DESCENDING, ASCENDING} depending on whether shift is pressed. 
//	                status = status + (e.isShiftDown() ? -1 : 1);
//	                status = (status + 4) % 3 - 1; // signed mod, returning {-1, 0, 1}
//	                setSortingStatus(column, status);
            }
        }
    }
}
