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

import unikn.dbis.univis.meta.VHierarchy;
import unikn.dbis.univis.meta.VDataReference;
import unikn.dbis.univis.meta.VDimension;
import unikn.dbis.univis.meta.VDiceBox;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.DefaultMutableTreeNode;

import org.hibernate.Hibernate;

/**
 * TODO: document me!!!
 * <p/>
 * <code>VTreeHelper</code>.
 * <p/>
 * User: raedler, weiler
 * Date: 10.04.2006
 * Time: 16:38:04
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: VTreeHelper.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.1
 */
public class VTreeHelper {

    public static MutableTreeNode createDefaultTree(VDiceBox diceBox) {

        MutableTreeNode root = new DefaultMutableTreeNode(diceBox);

        int index = 0;
        for (VHierarchy child : diceBox.getChildren()) {
            createDefaultTree(root, child, index++);
        }

        return root;
    }

    private static MutableTreeNode createDefaultTree(MutableTreeNode parent, VHierarchy hierarchy, int index) {

        VDataReference dataReference = hierarchy.getDataReference();
        dataReference.setParent(hierarchy.getParent().getDataReference());

        if (dataReference.getParent() != null) {
            dataReference.getParent().getChildren().add(dataReference);
        }

        int i = 0;
        if (dataReference instanceof VDimension) {
            if (((VDimension) dataReference).isVisible()) {

                MutableTreeNode node = addChildToParent(parent, dataReference, index);

                for (VHierarchy child : hierarchy.getChildren()) {
                    createDefaultTree(node, child, i++);
                }
            }
            else {
                for (VHierarchy child : hierarchy.getChildren()) {
                    createDefaultTree(parent, child, i);
                }
            }
        }
        else {

            MutableTreeNode node = addChildToParent(parent, dataReference, index);

            for (VHierarchy child : hierarchy.getChildren()) {
                createDefaultTree(node, child, i++);
            }
        }

        return parent;
    }

    private static MutableTreeNode addChildToParent(MutableTreeNode parent, VDataReference dataReference, int index) {
        MutableTreeNode node = new DefaultMutableTreeNode();
        node.setUserObject(dataReference);
        parent.insert(node, index);

        return node;
    }
}