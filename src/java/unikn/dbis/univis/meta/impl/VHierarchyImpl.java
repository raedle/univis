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
package unikn.dbis.univis.meta.impl;

import unikn.dbis.univis.meta.VDataReference;
import unikn.dbis.univis.meta.VHierarchy;

import javax.swing.tree.TreeNode;
import java.util.*;

/**
 * TODO: document me!!!
 * <p/>
 * The <code>VHierarchyImpl</code> is the abstract super class that each
 * element of the involved tree cube structure have to extend of.
 * <p/>
 * User: raedler, weiler
 * Date: 08.04.2006
 * Time: 20:20:36
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: VHierarchyImpl.java 338 2006-10-08 23:11:30Z raedler $
 * @see VHierarchy
 * @see Cloneable
 * @since UniVis Explorer 0.1
 */
public class VHierarchyImpl implements VHierarchy, Cloneable {

    public VHierarchyImpl() {
        children = new LinkedList<VHierarchy>();
    }

    // The (unique) identifier of the tree fresh item.
    private Long id;

    /**
     * Returns the (unique) identifier of the tree fresh
     * item.
     *
     * @return The (unique) identifier of the tree fresh
     *         item.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the (unique) identifier of the tree fresh item.
     *
     * @param id he (unique) identifier of the tree fresh
     *           item.
     */
    public void setId(Long id) {
        this.id = id;
    }

    // ##############################################################################
    // Interface implementations.
    // ##############################################################################

    // The information about the hierarchy data.
    private VDataReference dataReference;

    // The parental hierarchy element of this element.
    private VHierarchy parent;

    // The children of the tree fresh item.
    private List<VHierarchy> children;

    /**
     * Returns the information of the hierarchy data using
     * this referenced data object.
     *
     * @return The information about the hierarchy data.
     */
    public VDataReference getDataReference() {
        return dataReference;
    }

    /**
     * Sets the information of the hierarchy data using
     * this referenced data object.
     *
     * @param dataReference The information about the hierarchy
     *                      data.
     */
    public void setDataReference(VDataReference dataReference) {
        this.dataReference = dataReference;
    }

    /**
     * Returns the parental element of this hierarchy element.
     *
     * @return The parental hierarchy element.
     */
    public VHierarchy getParent() {
        return parent;
    }

    /**
     * Sets the parental element of this hierarchy element.
     *
     * @param parent The parental hierarchy element.
     */
    public void setParent(VHierarchy parent) {
        this.parent = parent;
    }

    /**
     * Returns the children of the tree fresh item.
     *
     * @return The children of the tree fresh item.
     */
    public List<VHierarchy> getChildren() {
        return children;
    }

    /**
     * Sets the children of the tree fresh item.
     *
     * @param children The children of the tree fresh
     *                 item.
     */
    public void setChildren(List<VHierarchy> children) {
        this.children = children;
    }

    /**
     * Returns the child <code>TreeNode</code> at index
     * <code>childIndex</code>.
     *
    public VHierarchy getChildAt(int childIndex) {
        Object o = children.get(childIndex);

        if (o instanceof VHierarchy) {
            try {
                return ((VHierarchy) o).cloneHierarchy();
            }
            catch (CloneNotSupportedException cnse) {
                cnse.printStackTrace();
            }
        }

        throw new IllegalStateException("Couldn't get child at position " + childIndex + ".");
    }

    /**
     * Returns the number of children <code>TreeNode</code>s the receiver
     * contains.
     *
    public int getChildCount() {
        return children.size();
    }

    /**
     * Returns the parent <code>TreeNode</code> of the receiver.
     *
    public TreeNode getParent() {
        return null;
    }

    /**
     * Returns the index of <code>node</code> in the receivers children.
     * If the receiver does not contain <code>node</code>, -1 will be
     * returned.
     *
    public int getIndex(TreeNode node) {
        //noinspection SuspiciousMethodCalls
        return children.indexOf(node);
    }

    /**
     * Returns true if the receiver allows children.
     *
    public boolean getAllowsChildren() {
        return children.size() > 0;
    }

    /**
     * Returns true if the receiver is a leaf.
     *
    public boolean isLeaf() {
        return children.size() <= 0;
    }

    /**
     * Returns the children of the receiver as an <code>Enumeration</code>.
     *
    public Enumeration children() {
        return Collections.enumeration(children);
    }
    */

    // ##############################################################################
    // Override methods of super classes.
    // ##############################################################################

    /**
     * Clones the tree fresh item to handle unique occurence
     * in the tree to perform tree path actions.
     *
     * @return The cloned tree fresh item.
     * @throws CloneNotSupportedException
     */
    public VHierarchy cloneHierarchy() throws CloneNotSupportedException {

        VHierarchyImpl hierarchy = new VHierarchyImpl();

        hierarchy.setId(id);
        hierarchy.setChildren(children);
        hierarchy.setDataReference(dataReference);

        return hierarchy;
    }

    /**
     * Returns the name that should be shown in the tree
     * or anything else.
     *
     * @return The name that should be shown.
     */
    @Override
    public String toString() {

        if (getDataReference() != null) {
            return getDataReference().getI18nKey();
        }

        return super.toString();
    }
}