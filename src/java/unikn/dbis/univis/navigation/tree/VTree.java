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
import unikn.dbis.univis.meta.impl.VCombinationImpl;
import unikn.dbis.univis.icon.VIcons;
import unikn.dbis.univis.dnd.VDataReferenceFlavor;
import unikn.dbis.univis.explorer.VExplorer;
import unikn.dbis.univis.sql.dialect.UniVisDialect;
import unikn.dbis.univis.hibernate.util.HibernateUtil;
import unikn.dbis.univis.message.MessageResolver;
import unikn.dbis.univis.message.swing.VLabel;
import unikn.dbis.univis.navigation.filter.FilterPopupMenu;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import java.sql.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.dnd.*;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.*;

import org.hibernate.sql.QuerySelect;
import org.hibernate.sql.JoinFragment;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * TODO: document me!!!
 * <p/>
 * <code>VTree</code>.
 * <p/>
 * User: raedler, weiler
 * Date: 10.04.2006
 * Time: 17:13:54
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: VTree.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.1
 */
public class VTree extends JTree implements DragSourceListener, DragGestureListener {

    // The logger to log info, error and other occuring messages
    // or exceptions.
    public static final transient Log LOG = LogFactory.getLog(VTree.class);

    private JPopupMenu popupMenu = new JPopupMenu();

    private VLabel whatMeasureLabel = new VLabel();

    /**
     * Returns a <code>JTree</code> with a sample model.
     * The default model used by the tree defines a leaf node as any node
     * without children.
     *
     * @see javax.swing.tree.DefaultTreeModel#asksAllowsChildren
     */
    public VTree() {
        super(new Object[]{});

        VTreeCellRenderer renderer = new VTreeCellRenderer();
        setCellRenderer(renderer);
        setCellEditor(new VTreeCellEditor(renderer));
        setEditable(true);

        addMouseListener(new MouseAdapter() {

            /**
             * Invoked when a mouse button has been pressed on a component.
             */
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    setSelectionPath(getPathForLocation(e.getX(), e.getY()));
                }
            }
        });
    }

    /**
     * Returns a <code>JTree</code> with a sample model.
     * The default model used by the tree defines a leaf node as any node
     * without children.
     *
     * @see javax.swing.tree.DefaultTreeModel#asksAllowsChildren
     */
    public VTree(VDiceBox diceBox) {
        super(VTreeHelper.createDefaultTree(diceBox));

        VTreeCellRenderer renderer = new VTreeCellRenderer();
        setCellRenderer(renderer);
        setCellEditor(new VTreeCellEditor(renderer));
        setEditable(true);

        addMouseListener(new MouseAdapter() {

            /**
             * Invoked when a mouse button has been pressed on a component.
             */
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    setSelectionPath(getPathForLocation(e.getX(), e.getY()));
                }
            }
        });
    }

    /**
     * TODO: document me!!!
     *
     * @param p
     */
    public void showPopupMenu(Point p) {

        // Remove all items from popup menu.
        popupMenu.removeAll();

        Object o = getLastSelectedPathComponent();

        if (o instanceof DefaultMutableTreeNode) {

            DefaultMutableTreeNode node = (DefaultMutableTreeNode) o;

            Object userObject = node.getUserObject();

            if (userObject instanceof VDimension) {

                VDimension dimension = (VDimension) userObject;

                try {
                    Point p2 = new Point(p);
                    SwingUtilities.convertPointToScreen(p2, this);

                    final FilterItemContainer container = createFilterContainer(dimension, p2);

                    if (!container.isEmpty()) {
                        /*
                        JLabel header = new JLabel(MessageResolver.getMessage("data_reference." + dimension.getI18nKey()));
                        Font font = header.getFont();
                        header.setFont(new Font(font.getFontName(), Font.BOLD, font.getSize() + 2));
                        popupMenu.add(header);
                        popupMenu.add(new JPopupMenu.Separator());
                        */

                        popupMenu = new FilterPopupMenu(MessageResolver.getMessage("data_reference." + dimension.getI18nKey()));

                        final JCheckBox button = new JCheckBox("Check/Uncheck all");

                        button.setSelected(container.isAllChecked());

                        button.addActionListener(new ActionListener() {
                            /**
                             * Invoked when an action occurs.
                             */
                            public void actionPerformed(ActionEvent e) {

                                for (Component c : container.getComponents()) {
                                    if (c instanceof VBidirectionalBrowsingItem) {
                                        ((VBidirectionalBrowsingItem) c).getCheckBox().setChecked(button.isSelected());
                                    }
                                }
                            }
                        });

                        popupMenu.add(button);

                        popupMenu.add(new JPopupMenu.Separator());

                        popupMenu.add(container);

                        JButton view = new JButton(MessageResolver.getMessage("filtering"), VIcons.FILTER);
                        view.addActionListener(new ActionListener() {

                            /**
                             * Invoked when an action occurs.
                             */
                            public void actionPerformed(ActionEvent e) {
                                popupMenu.setVisible(false);
                            }
                        });

                        popupMenu.add(new JPopupMenu.Separator());

                        popupMenu.add(view);

                        popupMenu.show(VTree.this, (int) p.getX(), (int) p.getY());
                    }
                    else {
                        JOptionPane.showMessageDialog(VTree.this.getParent().getParent().getParent(), MessageResolver.getMessage("no_items_found"), MessageResolver.getMessage("error_message"), JOptionPane.ERROR_MESSAGE);
                    }
                }
                catch (SQLException sqle) {
                    VExplorer.publishException(sqle);

                    if (LOG.isErrorEnabled()) {
                        LOG.error(sqle.getMessage(), sqle);
                    }
                }
            }
        }
    }

    /**
     * TODO: document me!!!
     *
     * @param dimension
     * @param p
     * @return
     * @throws SQLException
     */
    private FilterItemContainer createFilterContainer(VDimension dimension, Point p) throws SQLException {

        FilterItemContainer container = new FilterItemContainer(p);

        boolean allChecked = false;

        for (ResultSet result = getResult(dimension); result.next();) {

            Long id = result.getLong(1);

            for (Filterable filterable : dimension.getSelections()) {
                if (filterable.getId().equals(id)) {
                    allChecked = true;
                    break;
                }
            }

            VBidirectionalBrowsingItem browsingItem;
            if (dimension.isDependent()) {
                browsingItem = new VBidirectionalBrowsingItem(dimension, id, result.getLong(2), result.getString(3));
            }
            else {
                browsingItem = new VBidirectionalBrowsingItem(dimension, id, result.getString(2));
            }
            container.add(browsingItem);
        }

        container.setAllChecked(allChecked);

        return container;
    }

    /**
     * TODO: document me!!!
     *
     * @param dimension
     * @return
     * @throws SQLException
     */
    private ResultSet getResult(VDimension dimension) throws SQLException {

        // Get a connection to retrieve items.
        Connection connection = VExplorer.getConnection();

        QuerySelect querySelect = new QuerySelect(HibernateUtil.getDialect());

        Statement stmt = connection.createStatement();

        querySelect.addSelectColumn("id", "id");

        if (dimension.isDependent()) {
            querySelect.addSelectColumn("parent", "parent");
        }

        querySelect.addSelectColumn("name", "name");

        querySelect.getJoinFragment().addJoin(dimension.getTableName(), UniVisDialect.generateTableAlias(dimension.getTableName(), 1), new String[]{"parent"}, new String[]{"id"}, JoinFragment.FULL_JOIN);

        String sql = "SELECT " + dimension.getTableName() + ".id, " + (dimension.isDependent() ? dimension.getTableName() + ".parent, " : "") + dimension.getTableName() + ".name " + createWhereClause(dimension);

        return stmt.executeQuery(sql);
    }

    /**
     * Creates the where clause for filtering the required values.
     *
     * @param dimension The dimension that gets the where clause statement.
     * @return The where clause sql statement.
     */
    private String createWhereClause(VDimension dimension) {

        // The table name.
        String tableName = dimension.getTableName();

        StringBuilder fromClause = new StringBuilder("FROM " + tableName + " AS " + tableName);
        StringBuilder whereClause = new StringBuilder();

        // Prepares the statement with parentable statements.
        prepareDependentWhereClause(dimension, fromClause, whereClause);

        // Check for existing where clause.
        if (whereClause.length() > 0) {
            whereClause.insert(0, " WHERE ");
        }

        return fromClause.toString() + whereClause.toString();
    }

    /**
     * Prepares the where statement with available parentable statements.
     *
     * @param dimension   The dimension that contains the table which is needed
     *                    to identify the selection values.
     * @param whereClause The where clause that gets the statements.
     */
    private void prepareDependentWhereClause(VDimension dimension, StringBuilder fromClause, StringBuilder whereClause) {

        // Check whether the parent is parentable to filter the previous selected
        // items.
        if (dimension.isDependent() && VMetaHelper.isParentADimension(dimension)) {

            // The parent is a parent because the meta helper checked
            // whether the parent is a parent or not. See while above.
            VDimension parent = (VDimension) dimension.getParent();

            // Append opening bracket if selections are available.
            if (parent.getSelectionSize() > 0) {
                whereClause.append("(");
            }

            for (Filterable filterable : parent.getSelections()) {

                Object id = filterable.getId();

                // Whether the statement contains a filtering.
                if (whereClause.length() > 1) {
                    whereClause.append(" OR ");
                }

                whereClause.append(dimension.getTableName()).append(".parent = ").append(id);
            }

            // Append closing bracket if selections are available.
            if (parent.getSelectionSize() > 0) {
                whereClause.append(")");
            }
        }
    }

    public void refresh(VDiceBox diceBox) {
        setRootVisible(true);
        setModel(new DefaultTreeModel(VTreeHelper.createDefaultTree(diceBox)));
    }

    private VMeasure measure;
    private VFunction function;

    public void setMeasure(VMeasure measure) {
        whatMeasureLabel.setI18NKey(measure.getI18nKey());
        whatMeasureLabel.repaint();

        this.measure = measure;
    }

    public void setFunction(VFunction function) {
        this.function = function;
    }

    public VLabel getWhatMeasureLabel() {
        return whatMeasureLabel;
    }

    /**
     * A <code>DragGestureRecognizer</code> has detected
     * a platform-dependent drag initiating gesture and
     * is notifying this listener
     * in order for it to initiate the action for the user.
     * <p/>
     *
     * @param dge the <code>DragGestureEvent</code> describing
     *            the gesture that has just occurred
     */
    public void dragGestureRecognized(DragGestureEvent dge) {

        // Gets the selected tree node.
        Object o = getLastSelectedPathComponent();

        if (o instanceof DefaultMutableTreeNode) {

            // Gets the user object of the tree node.
            Object userObject = ((DefaultMutableTreeNode) o).getUserObject();

            if (userObject instanceof VDimension) {

                final VDimension dimension = (VDimension) userObject;

                // Whether the selected dimension allows drag and drop
                // or not.
                if (dimension.isDragable() && !dimension.isDropped()) {
                    dge.startDrag(DragSource.DefaultMoveDrop, new Transferable() {

                        /**
                         * Returns an array of DataFlavor objects indicating the flavors the data
                         * can be provided in.  The array should be ordered according to preference
                         * for providing the data (from most richly descriptive to least descriptive).
                         *
                         * @return an array of data flavors in which this data can be transferred
                         */
                        public DataFlavor[] getTransferDataFlavors() {
                            return new DataFlavor[]{VDataReferenceFlavor.COMBINATION_FLAVOR, VDataReferenceFlavor.DIMENSION_FLAVOR};
                        }

                        /**
                         * Returns whether or not the specified data flavor is supported for
                         * this object.
                         *
                         * @param flavor the requested flavor for the data
                         * @return boolean indicating whether or not the data flavor is supported
                         */
                        public boolean isDataFlavorSupported(DataFlavor flavor) {
                            for (DataFlavor dataFlavor : getTransferDataFlavors()) {
                                if (dataFlavor.match(flavor)) {
                                    return true;
                                }
                            }
                            return false;
                        }

                        /**
                         * Returns an object which represents the data to be transferred.  The class
                         * of the object returned is defined by the representation class of the flavor.
                         *
                         * @param flavor the requested flavor for the data
                         * @throws java.awt.datatransfer.UnsupportedFlavorException
                         *          if the requested data flavor is
                         *          not supported.
                         * @see java.awt.datatransfer.DataFlavor#getRepresentationClass
                         */
                        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
                            if (VDataReferenceFlavor.COMBINATION_FLAVOR.match(flavor)) {

                                VCube cube = null;
                                for (VDataReference dataReference = dimension; dataReference != null; dataReference = dataReference.getParent())
                                {
                                    if (dataReference instanceof VCube) {
//                                        cube = (VCube) dataReference;
                                        break;
                                    }
                                }

                                VCombination combination = new VCombinationImpl();
                                combination.setCube(cube);
                                combination.setDimension(dimension);
                                combination.setMeasure(measure);
                                combination.setFunction(function);

                                return combination;
                            }
                            else if (VDataReferenceFlavor.DIMENSION_FLAVOR.match(flavor)) {
                                return dimension;
                            }
                            throw new UnsupportedFlavorException(flavor);
                        }
                    });
                }
                else {
                    if (LOG.isInfoEnabled()) {
                        LOG.info("The dimension node isn't summable or has been drag and dropped in the past.");
                    }
                }
            }
            else if (userObject instanceof VMeasure) {

                final VMeasure measure = (VMeasure) userObject;

                dge.startDrag(DragSource.DefaultMoveDrop, new Transferable() {

                    /**
                     * Returns an array of DataFlavor objects indicating the flavors the data
                     * can be provided in.  The array should be ordered according to preference
                     * for providing the data (from most richly descriptive to least descriptive).
                     *
                     * @return an array of data flavors in which this data can be transferred
                     */
                    public DataFlavor[] getTransferDataFlavors() {
                        return new DataFlavor[]{VDataReferenceFlavor.MEASURE_FLAVOR};
                    }

                    /**
                     * Returns whether or not the specified data flavor is supported for
                     * this object.
                     *
                     * @param flavor the requested flavor for the data
                     * @return boolean indicating whether or not the data flavor is supported
                     */
                    public boolean isDataFlavorSupported(DataFlavor flavor) {
                        for (DataFlavor dataFlavor : getTransferDataFlavors()) {
                            if (dataFlavor.match(flavor)) {
                                return true;
                            }
                        }
                        return false;
                    }

                    /**
                     * Returns an object which represents the data to be transferred.  The class
                     * of the object returned is defined by the representation class of the flavor.
                     *
                     * @param flavor the requested flavor for the data
                     * @throws java.awt.datatransfer.UnsupportedFlavorException
                     *          if the requested data flavor is
                     *          not supported.
                     * @see java.awt.datatransfer.DataFlavor#getRepresentationClass
                     */
                    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
                        if (VDataReferenceFlavor.MEASURE_FLAVOR.match(flavor)) {
                            return measure;
                        }
                        throw new UnsupportedFlavorException(flavor);
                    }
                });
            }
        }
    }

    /**
     * This method is invoked to signify that the Drag and Drop
     * operation is complete. The getDropSuccess() method of
     * the <code>DragSourceDropEvent</code> can be used to
     * determine the termination state. The getDropAction() method
     * returns the operation that the drop site selected
     * to apply to the Drop operation. Once this method is complete, the
     * current <code>DragSourceContext</code> and
     * associated resources become invalid.
     *
     * @param dsde the <code>DragSourceDropEvent</code>
     */
    public void dragDropEnd(DragSourceDropEvent dsde) {
        if (dsde.getDropSuccess()) {

            if (LOG.isDebugEnabled()) {
                LOG.debug("Drag and drop finished successfully. The tree UI will be updated.");
            }

            updateUI();
        }
    }

    /**
     * Called as the cursor's hotspot exits a platform-dependent drop site.
     * This method is invoked when any of the following conditions are true:
     * <UL>
     * <LI>The cursor's hotspot no longer intersects the operable part
     * of the drop site associated with the previous dragEnter() invocation.
     * </UL>
     * OR
     * <UL>
     * <LI>The drop site associated with the previous dragEnter() invocation
     * is no longer active.
     * </UL>
     * OR
     * <UL>
     * <LI> The drop site associated with the previous dragEnter() invocation
     * has rejected the drag.
     * </UL>
     *
     * @param dse the <code>DragSourceEvent</code>
     */
    public void dragExit(DragSourceEvent dse) {
        // empty
    }

    /**
     * Called when the user has modified the drop gesture.
     * This method is invoked when the state of the input
     * device(s) that the user is interacting with changes.
     * Such devices are typically the mouse buttons or keyboard
     * modifiers that the user is interacting with.
     *
     * @param dsde the <code>DragSourceDragEvent</code>
     */
    public void dropActionChanged(DragSourceDragEvent dsde) {
        // empty
    }

    /**
     * Called as the cursor's hotspot moves over a platform-dependent drop site.
     * This method is invoked when all the following conditions are true:
     * <UL>
     * <LI>The cursor's hotspot has moved, but still intersects the
     * operable part of the drop site associated with the previous
     * dragEnter() invocation.
     * <LI>The drop site is still active.
     * <LI>The drop site accepts the drag.
     * </UL>
     *
     * @param dsde the <code>DragSourceDragEvent</code>
     */
    public void dragOver(DragSourceDragEvent dsde) {
        // empty
    }

    /**
     * Called as the cursor's hotspot enters a platform-dependent drop site.
     * This method is invoked when all the following conditions are true:
     * <UL>
     * <LI>The cursor's hotspot enters the operable part of a platform-
     * dependent drop site.
     * <LI>The drop site is active.
     * <LI>The drop site accepts the drag.
     * </UL>
     *
     * @param dsde the <code>DragSourceDragEvent</code>
     */
    public void dragEnter(DragSourceDragEvent dsde) {
        // empty
    }

    /**
     * TODO: document me!!!
     */
    class FilterItemContainer extends JScrollPane {

        // The layout of the container.
        private GridLayout containerLayout;

        // The container that gets the components.
        private Container container;

        // The point to minimize the scrolling window.
        private Point p;

        // Whether all filter items are checked or not.
        private boolean allChecked;

        /**
         * Creates a new <code>JPanel</code> with a double buffer
         * and a flow layout.
         */
        public FilterItemContainer(Point p) {
            this.p = p;

            containerLayout = new GridLayout();
            container = new JPanel(containerLayout);

            setViewportView(container);
            setBorder(BorderFactory.createEmptyBorder());
        }

        /**
         * Appends the specified component to the end of this container.
         * This is a convenience method for {@link #addImpl}.
         * <p/>
         * Note: If a component has been added to a container that
         * has been displayed, <code>validate</code> must be
         * called on that container to display the new component.
         * If multiple components are being added, you can improve
         * efficiency by calling <code>validate</code> only once,
         * after all the components have been added.
         *
         * @param comp the component to be added
         * @return the component argument
         * @see #addImpl
         * @see #validate
         * @see javax.swing.JComponent#revalidate()
         */
        @Override
        public Component add(Component comp) {
            containerLayout.setRows(container.getComponentCount() + 1);
            return container.add(comp);
        }

        /**
         * Returns the components of the container.
         *
         * @return The components of the container.
         */
        public Component[] getComponents() {
            return container.getComponents();
        }

        /**
         * Returns whether the container is empty or not.
         *
         * @return Whether the container is empty or not.
         */
        public boolean isEmpty() {
            return container.getComponentCount() <= 0;
        }

        /**
         * Returns whether all filter items are checked or not.
         *
         * @return Whether all filter items are checked or
         *         not.
         */
        protected boolean isAllChecked() {
            return allChecked;
        }

        /**
         * Sets whether all filter items are checked or not.
         *
         * @param allChecked Whether all filter items are checked or
         *                   not.
         */
        protected void setAllChecked(boolean allChecked) {
            this.allChecked = allChecked;
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
        @Override
        public Dimension getPreferredSize() {

            Dimension d = super.getPreferredSize();

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

            double height = screenSize.getHeight() - p.getX();

            if (d.getHeight() > height) {
                return new Dimension((int) d.getWidth(), (int) height);
            }

            return d;
        }
    }
}