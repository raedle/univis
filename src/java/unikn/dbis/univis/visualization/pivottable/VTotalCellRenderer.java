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

import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * @author Marion Herb
 *         <p/>
 *         This renderer extends a component.
 *         It is used each time a cell must be displayed.
 */
public class VTotalCellRenderer extends DefaultTableCellRenderer {

    /**
     * Default number format.
     */
    protected NumberFormat format = new DecimalFormat("#,###");

    public VTotalCellRenderer() {
        super();
        setBackground(Color.LIGHT_GRAY);
    }

    /**
     * Sets component's text.
     *
     * @param value cell value.
     */
    protected void setValue(Object value) {
        Font defaultFont = UIManager.getFont("Table.font");
        Font totalFont = new Font(defaultFont.getName(), defaultFont.getStyle() + Font.BOLD, defaultFont.getSize());
        setFont(totalFont);

        if (value == null) {
            super.setValue(value);
        }
        else {
            if (value instanceof Number) {
                setHorizontalAlignment(JLabel.RIGHT);
                setText(format.format(value));
            }
            else {
                setText(value.toString());
            }
        }
    }

}
