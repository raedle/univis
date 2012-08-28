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

import unikn.dbis.univis.meta.*;
import unikn.dbis.univis.icon.VIconComponent;
import unikn.dbis.univis.icon.VIcons;
import unikn.dbis.univis.icon.VCubeFlagIcon;
import unikn.dbis.univis.icon.VCubeIcon;

import javax.swing.*;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.EventObject;

/**
 * TODO: document me!!!
 * <p/>
 * <code>VTreeCellEditor</code>.
 * <p/>
 * User: raedler, weiler
 * Date: 18.04.2006
 * Time: 18:19:00
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: VTreeCellEditor.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.1
 */
public class VTreeCellEditor extends AbstractCellEditor implements TreeCellEditor {

    private VTreeCellRenderer renderer;

    public VTreeCellEditor(VTreeCellRenderer renderer) {
        this.renderer = renderer;
    }

    /**
     * Returns the value contained in the editor.
     *
     * @return the value contained in the editor
     */
    public Object getCellEditorValue() {
        return "n/a";
    }

    /**
     * Returns true.
     *
     * @param e an event object
     * @return true
     */
    @Override
    public boolean isCellEditable(EventObject e) {
        if (e instanceof MouseEvent) {
            MouseEvent mouseEvent = (MouseEvent) e;

            if (SwingUtilities.isLeftMouseButton(mouseEvent)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sets an initial <I>value</I> for the editor.  This will cause
     * the editor to stopEditing and lose any partially edited value
     * if the editor is editing when this method is called. <p>
     * <p/>
     * Returns the component that should be added to the client's
     * Component hierarchy.  Once installed in the client's hierarchy
     * this component will then be able to draw and receive user input.
     *
     * @param tree       the JTree that is asking the editor to edit;
     *                   this parameter can be null
     * @param value      the value of the cell to be edited
     * @param isSelected true is the cell is to be renderer with
     *                   selection highlighting
     * @param expanded   true if the node is expanded
     * @param leaf       true if the node is a leaf node
     * @param row        the row index of the node being edited
     * @return the component for editing
     */
    public Component getTreeCellEditorComponent(final JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row) {

        Component rendererComponent = renderer.getTreeCellRendererComponent(tree, value, isSelected, expanded, leaf, row, false);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel label = new JLabel(tree.convertValueToText(value, isSelected, expanded, leaf, row, true));
        label.setFont(rendererComponent.getFont());
        label.setForeground(rendererComponent.getForeground());
        label.setBackground(rendererComponent.getBackground());

        panel.add(label, BorderLayout.CENTER);

        if (value instanceof DefaultMutableTreeNode) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;

            if (node.isLeaf()) {
                label.setIcon(renderer.getLeafIcon());
            }
            else {
                if (expanded) {
                    label.setIcon(renderer.getOpenIcon());
                }
                else {
                    label.setIcon(renderer.getClosedIcon());
                }
            }

            final Object o = node.getUserObject();

            if (o instanceof VDimension) {
                VDimension dimension = (VDimension) o;

                if (dimension.isDragable()) {
                    final VIconComponent icon = new VIconComponent(VIcons.FILTER, 8);
                    panel.add(icon, BorderLayout.EAST);
                    icon.addMouseListener(new MouseAdapter() {

                        /**
                         * Invoked when a mouse button has been pressed on a component.
                         */
                        @Override
                        public void mousePressed(MouseEvent e) {
                            VTree vtree = (VTree) tree;
                            MouseEvent converted = SwingUtilities.convertMouseEvent(icon, e, vtree);

                            vtree.showPopupMenu(converted.getPoint());
                        }
                    });
                }

                if (dimension.isDropped()) {
                    label.setForeground(Color.LIGHT_GRAY);
                    //label.setBackground(UIManager.getColor("Tree.background"));
                    label.setBackground(Color.RED);
                }

                int size = dimension.getSupportedCubes().size();
                if (size % 2 == 1) {
                    size = (size / 2) + 1;
                }
                else {
                    size = (size / 2);
                }

                JPanel flags = new JPanel(new GridLayout(2, size));
                flags.setBackground(Color.WHITE);

                for (VCube cube : dimension.getSupportedCubes()) {
                    JLabel flag = new JLabel(new VCubeFlagIcon(cube.getColor()));
                    flag.setVerticalAlignment(JLabel.CENTER);

                    flags.add(flag);
                }

                panel.add(flags, BorderLayout.WEST);
            }

            else if (o instanceof VDiceBox) {
                label.setIcon(VIcons.BRICK);
            }
            else if (o instanceof VCube) {
                label.setIcon(new VCubeIcon(((VCube) o).getColor()));
            }
            else if (o instanceof VClassification) {
                VClassification clazz = (VClassification) o;

                String type = clazz.getType();

                if ("dimension".equals(type)) {
                    label.setIcon(VIcons.CHART_ORGANISATION);
                }
                else if ("measure".equals(type)) {
                    label.setIcon(VIcons.COLOR_SWATCH);
                }
                else if ("function".equals(type)) {
                    label.setIcon(VIcons.BRICKS);
                }
            }
            else if (o instanceof VMeasure || o instanceof VFunction) {

                int preferredHeight = (int) rendererComponent.getPreferredSize().getHeight();

                final JCheckBox selection = new JCheckBox(tree.convertValueToText(value, isSelected, expanded, leaf, row, true));
                selection.setPreferredSize(new Dimension((int) selection.getPreferredSize().getWidth() + 1, preferredHeight));
                selection.setFont(label.getFont());
                selection.setBackground(label.getBackground());
                panel.add(selection, BorderLayout.CENTER);


                selection.addActionListener(new ActionListener() {
                    /**
                     * Invoked when an action occurs.
                     */
                    public void actionPerformed(ActionEvent e) {
                        VDataReference dataReference = (VDataReference) o;

                        dataReference = dataReference.getParent();

                        for (VDataReference child : dataReference.getChildren()) {
                            if (child instanceof Selectable) {
                                ((Selectable) child).setSelected(false);
                            }
                        }

                        ((Selectable) o).setSelected(((JCheckBox) e.getSource()).isSelected());

                        if (((JCheckBox) e.getSource()).isSelected()) {
                            if (o instanceof VMeasure) {
                                ((VTree) tree).setMeasure((VMeasure) o);
                            }
                            else if (o instanceof VFunction) {
                                ((VTree) tree).setFunction((VFunction) o);
                            }
                        }

                        SwingUtilities.invokeLater(new Runnable() {
                            /**
                             * When an object implementing interface <code>Runnable</code> is used
                             * to create a thread, starting the thread causes the object's
                             * <code>run</code> method to be called in that separately executing
                             * thread.
                             * <p/>
                             * The general contract of the method <code>run</code> is that it may
                             * take any action whatsoever.
                             *
                             * @see Thread#run()
                             */
                            public void run() {
                                tree.repaint();
                            }
                        });
                    }
                });

                if (o instanceof Selectable) {
                    boolean valueSelected = ((Selectable) o).isSelected();

                    selection.setSelected(valueSelected);
                }
            }
        }
        return panel;
    }
}