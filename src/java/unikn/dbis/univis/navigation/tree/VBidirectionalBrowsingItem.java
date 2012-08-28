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
package unikn.dbis.univis.navigation.tree;

import unikn.dbis.univis.meta.VDimension;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * TODO: document me!!!
 * <p/>
 * <code>VBidirectionalBrowsingItem</code>.
 * <p/>
 * User: raedler
 * Date: 04.10.2006
 * Time: 14:30:10
 *
 * @author Roman R&auml;dle
 * @version $Id: VBidirectionalBrowsingItem.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.3
 */
public class VBidirectionalBrowsingItem extends JComponent {

    public enum Orientation {
        LEFT, RIGHT
    }

    private VIdCheckBox checkBox;

    /**
     * TODO: document me!!!
     *
     * @param
     */
    public VBidirectionalBrowsingItem(final VDimension dimension, final Long id, String text) {
        this(dimension, id, null, text);
    }

    /**
     * Creates a <code>JMenuItem</code> with no set text or icon.
     */
    public VBidirectionalBrowsingItem(final VDimension dimension, final Long id, final Long parentId, String text) {
        setLayout(new BorderLayout());

        checkBox = new VIdCheckBox(dimension, id, parentId, text);

        add(checkBox, BorderLayout.CENTER);

        if (parentId != null) {
            add(new Arrow(Orientation.LEFT), BorderLayout.WEST);
            add(new Arrow(Orientation.RIGHT), BorderLayout.EAST);
        }
    }

    public VIdCheckBox getCheckBox() {
        return checkBox;
    }

    class Arrow extends JComponent {

        private Polygon triangle;

        public Arrow(Orientation orientation) {

            switch (orientation) {
                case LEFT:
                    triangle = new Polygon(new int[]{0, 10, 10}, new int[]{5, 0, 10}, 3);
                    break;
                case RIGHT:
                    triangle = new Polygon(new int[]{10, 0, 0}, new int[]{5, 0, 10}, 3);
                    break;
            }
        }

        /**
         * Calls the UI delegate's paint method, if the UI delegate
         * is non-<code>null</code>.  We pass the delegate a copy of the
         * <code>Graphics</code> object to protect the rest of the
         * paint code from irrevocable changes
         * (for example, <code>Graphics.translate</code>).
         * <p/>
         * If you override this in a subclass you should not make permanent
         * changes to the passed in <code>Graphics</code>. For example, you
         * should not alter the clip <code>Rectangle</code> or modify the
         * transform. If you need to do these operations you may find it
         * easier to create a new <code>Graphics</code> from the passed in
         * <code>Graphics</code> and manipulate it. Further, if you do not
         * invoker super's implementation you must honor the opaque property,
         * that is
         * if this component is opaque, you must completely fill in the background
         * in a non-opaque color. If you do not honor the opaque property you
         * will likely see visual artifacts.
         * <p/>
         * The passed in <code>Graphics</code> object might
         * have a transform other than the identify transform
         * installed on it.  In this case, you might get
         * unexpected results if you cumulatively apply
         * another transform.
         *
         * @param g the <code>Graphics</code> object to protect
         * @see #paint
         * @see javax.swing.plaf.ComponentUI
         */
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.fillPolygon(triangle);
        }

        /**
         * Returns the current width of this component.
         * This method is preferable to writing
         * <code>component.getBounds().width</code>, or
         * <code>component.getSize().width</code> because it doesn't cause any
         * heap allocations.
         *
         * @return the current width of this component
         */
        public int getWidth() {
            return 10;
        }

        /**
         * Returns the current height of this component.
         * This method is preferable to writing
         * <code>component.getBounds().height</code>, or
         * <code>component.getSize().height</code> because it doesn't cause any
         * heap allocations.
         *
         * @return the current height of this component
         */
        public int getHeight() {
            return checkBox.getHeight();
        }

        /**
         * Stores the width/height of this component into "return value"
         * <code>rv</code> and returns <code>rv</code>.
         * If <code>rv</code> is <code>null</code> a new <code>Dimension</code>
         * object is allocated.  This version of <code>getSize</code>
         * is useful if the caller wants to avoid allocating a new
         * <code>Dimension</code> object on the heap.
         *
         * @param rv the return value, modified to the component's size
         * @return <code>rv</code>
         */
        public Dimension getSize(Dimension rv) {
            return new Dimension(20, checkBox.getHeight());
        }

        /**
         * If the <code>preferredSize</code> has been set to a
         * non-<code>null</code> value just returns it.
         * If the UI delegate's <code>getPreferredSize</code>
         * method returns a non <code>null</code> value then return that;
         * otherwise defer to the component's layout manager.
         *
         * @return the value of the <code>preferredSize</code> property
         * @see #setPreferredSize
         * @see javax.swing.plaf.ComponentUI
         */
        public Dimension getPreferredSize() {
            return new Dimension(20, checkBox.getHeight());
        }
    }
}
