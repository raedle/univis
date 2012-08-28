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

import java.util.Vector;
import javax.swing.table.AbstractTableModel;

/**
 * TODO: document me!!!
 * <p/>
 * The <code>VPivotTableModel</code> specifies the methods the JTable will use
 * to interrogate a tabular data model.
 * <p/>
 * User: herb
 * Date: 28.08.2006
 * Time: 17:55:13
 *
 * @author Marion Herb
 * @version $Id: VPivotTableModel.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.2
 */
public class VPivotTableModel extends AbstractTableModel {

    private Vector data;
    private Vector columnNames;

    public VPivotTableModel(Vector data, Vector columnNames) {
        super();
        this.data = data;
        this.columnNames = columnNames;

        for (int rows = 0; rows < getRowCount(); rows++) {
            double totalHoriz = 0;
            for (int cols = 0; cols < getColumnCount(); cols++) {
                // nur fuer Zahlen -> Strings ausschliessen
                if (getColumnClass(cols).equals(Double.class)) {
                    double currValue = (Double) getValueAt(rows, cols);
                    totalHoriz += currValue;
                }
                else if (getColumnClass(cols).equals(Long.class)) {
                    long currValue = (Long) getValueAt(rows, cols);
                    totalHoriz += currValue;
                }
                else if (getColumnClass(cols).equals(Integer.class)) {
                    int currValue = (Integer) getValueAt(rows, cols);
                    totalHoriz += currValue;
                }
            }
            // jetzt eine Zeile durch: Einfuegen des Total-Wertes am Ende (letzte Spalte)
            ((Vector) this.data.elementAt(rows)).insertElementAt(new Double(totalHoriz), getColumnCount());
        }
        columnNames.addElement("TOTAL");
    }


    public int getColumnCount() {
        return columnNames.size();
    }

    public int getRowCount() {
        // hier eigentlich noch Subtotals abziehen
        return data.size();
    }

    public String getColumnName(int col) {
        return columnNames.elementAt(col).toString();
    }


    public Object getValueAt(int row, int col) {
        Object value = ((Vector) this.data.elementAt(row)).elementAt(col);
        return value;
    }

    /*
      * JTable uses this method to determine the default renderer/
      * editor for each cell. If we have Boolean values in a cell
      * a check box would be vreated rather than just containing
      * text ("true"/"false")
      */
    public Class getColumnClass(int column) {
        Class klasse = getValueAt(0, column).getClass();
        return klasse;
    }

    /*
      * Don't need to implement this method unless your table's editable.
      */
    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
//		if (col < 2) {
//		return false;
//		} else {
//		return true;
//		}
        return false;
    }

    /*
      * Don't need to implement this method unless your table's
      * data can change.
      */
    public void setRowAt(Object value, int row) {
//		((Vector)this.data.elementAt(row)).addElement(value);
//		fireTableRowsInserted(row, 1);
    }

    /*
      *
      */
    public void setValueAt(Object value, int row, int col) {
        // wenn cols gesetzt
        if (col > 0) {
            ((Vector) data.elementAt(row)).setElementAt(value, col);
        }
        else { // sonst ganzen Vektor einfügen
            ((Vector) data.elementAt(row)).addElement(value);
        }
        fireTableRowsInserted(row, col);
    }

    private void printDebugData() {
//		int numRows = getRowCount();
//		int numCols = getColumnCount();
//		
//		for (int i=0; i < numRows; i++) {
//			System.out.print("    row " + i + ":");
//			for (int j=0; j < numCols; j++) {
//				System.out.print("  " + ((Vector)data.elementAt(i)).elementAt(j));
//				//System.out.print("  " + data[i][j]);
//			}
//			System.out.println();
//		}
//		System.out.println("--------------------------");
    }
}
