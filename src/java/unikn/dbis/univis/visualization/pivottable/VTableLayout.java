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

import java.awt.*;

/**
 * @author Christian Gruen
 */
public class VTableLayout implements LayoutManager {
    int rows;
    int cols;
    int insetX;
    int insetY;
    int width = 0;
    int height = 0;
    int[] posX, posY;

    /**
     * Creates a grid layout with the specified number of rows and
     * columns. When displayed, the grid has the minimum size.
     *
     * @param rows number of rows
     * @param cols number of columns
     */
    public VTableLayout(int rows, int cols) {
        this(rows, cols, 0, 0);
    }

    /**
     * Creates a grid layout with the specified number of rows and
     * columns. When displayed, the grid has the minimum size.
     *
     * @param rows   number of rows
     * @param cols   number of columns
     * @param insetX horizontal inset size
     * @param insetY vertical inset size
     */
    public VTableLayout(int rows, int cols, int insetX, int insetY) {
        this.rows = rows;
        this.cols = cols;
        this.insetX = insetX;
        this.insetY = insetY;
        posX = new int[cols];
        posY = new int[rows];
    }

    /**
     * Adds the specified component with the specified name to
     * the layout.
     *
     * @param name the component name
     * @param comp the component to be added
     */
    public void addLayoutComponent(String name, Component comp) {
    }

    /**
     * Removes the specified component from the layout.
     *
     * @param comp the component to be removed.
     */
    public void removeLayoutComponent(Component comp) {
    }

    /**
     * Determines the preferred size of the container argument using
     * this grid layout.
     *
     * @param parent the layout container
     * @return the preferred dimensions for painting the container
     */
    public Dimension preferredLayoutSize(Container parent) {
        synchronized (parent.getTreeLock()) {
            Insets insets = parent.getInsets();
            int nrComponents = parent.getComponentCount();

            int maxW = 0;
            int maxH = 0;
            for (int i = 0; i < cols; i++) {
                posX[i] = maxW;
                int w = maxW;
                int h = 0;

                for (int j = 0; j < rows; j++) {
                    int n = j * cols + i;
                    if (n >= nrComponents) break;

                    Component comp = parent.getComponent(n);
                    Dimension dim = comp.getPreferredSize();

                    if (maxW < w + dim.width) maxW = w + dim.width;

                    if (posY[j] < h) posY[j] = h;
                    else h = posY[j];

                    h += dim.height;
                }
                if (maxH < h) maxH = h;
            }
            width = insets.left + maxW + (cols - 1) * insetX + insets.right;
            height = insets.top + maxH + (rows - 1) * insetY + insets.bottom;

            return new Dimension(width, height);
        }
    }

    /**
     * Determines the minimum size of the container argument using
     * this grid layout.
     *
     * @param parent the layout container
     * @return the preferred dimensions for painting the container
     */
    public Dimension minimumLayoutSize(Container parent) {
        return preferredLayoutSize(parent);
    }

    /**
     * Lays out the specified container using this layout.
     *
     * @param parent the layout container
     */
    public void layoutContainer(Container parent) {
        preferredLayoutSize(parent);

        synchronized (parent.getTreeLock()) {
            Insets insets = parent.getInsets();
            int nrComponents = parent.getComponentCount();
            for (int j = 0; j < rows; j++) {
                for (int i = 0; i < cols; i++) {
                    int n = j * cols + i;
                    if (n >= nrComponents) return;

                    Dimension compSize = parent.getComponent(n).getPreferredSize();

                    int x = insets.left + posX[i] + i * insetX;
                    int y = insets.top + posY[j] + j * insetY;
                    int w = compSize.width > 0 ? compSize.width :
                            width - insets.left - insets.right;
                    int h = compSize.height > 0 ? compSize.height :
                            height - insets.top - insets.bottom;
                    parent.getComponent(n).setBounds(x, y, w, h);
                }
            }
        }
    }
}
