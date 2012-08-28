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
import unikn.dbis.univis.meta.VDataReference;
import unikn.dbis.univis.meta.Filterable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Set;
import java.util.HashSet;

/**
 * TODO: document me!!!
 * <p/>
 * <code>VIdCheckBox</code>.
 * <p/>
 * User: raedler, weiler
 * Date: 10.04.2006
 * Time: 19:17:20
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: VIdCheckBox.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.1
 */
public class VIdCheckBox extends JCheckBox {

    private VDimension dimension;

    private Long id;
    private Long parentId;

    /**
     * TODO: document me!!!
     *
     * @param
     */
    public VIdCheckBox(final VDimension dimension, final Long id, String text) {
        this(dimension, id, null, text);
    }

    /**
     * TODO: document me!!!
     *
     * @param
     */
    public VIdCheckBox(final VDimension dimension, final Long id, final Long parentId, String text) {
        super(text);

        this.dimension = dimension;
        this.id = id;
        this.parentId = parentId;

        //setSelected(dimension.getSelections().contains(id));

        addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             */
            public void actionPerformed(ActionEvent e) {
                if (isSelected()) {
                    dimension.getSelections().add(new VIdCheckBoxFilter(id, parentId));
                }
                else {
                    dimension.getSelections().remove(new VIdCheckBoxFilter(id, parentId));

                    for (VDataReference dataReference : dimension.getChildren()) {
                        if (dataReference instanceof VDimension) {
                            recursiceRemove((VDimension) dataReference, id);
                        }
                    }
                }
            }
        });
    }

    public void setChecked(boolean checked) {
        if (!isSelected() && checked) {
            dimension.getSelections().add(new VIdCheckBoxFilter(id, parentId));
        }
        else if (isSelected() && !checked) {
            dimension.getSelections().remove(new VIdCheckBoxFilter(id, parentId));

            for (VDataReference dataReference : dimension.getChildren()) {
                if (dataReference instanceof VDimension) {
                    recursiceRemove((VDimension) dataReference, id);
                }
            }
        }

        setSelected(checked);
    }

    /**
     * TODO: document me!!!
     *
     * @param dimension
     * @param id
     */
    public void recursiceRemove(VDimension dimension, Long id) {

        Set<Filterable> selections = dimension.getSelections();

        // !!! Fixes the concurrent modification exception.
        Set<Object> removables = new HashSet<Object>();

        for (Object o : selections) {
            if (o instanceof VIdCheckBoxFilter) {
                VIdCheckBoxFilter parentor = (VIdCheckBoxFilter) o;

                if (id.equals(parentor.getParentId())) {
                    removables.add(parentor);

                    for (VDataReference dataReference : dimension.getChildren()) {
                        if (dataReference instanceof VDimension) {
                            recursiceRemove((VDimension) dataReference, parentor.getId());
                        }
                    }
                }
            }
        }

        selections.removeAll(removables);
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
    @Override
    protected void paintComponent(Graphics g) {
        for (Filterable filterable : dimension.getSelections()) {
            if (filterable.getId().equals(id)) {
                setSelected(true);
                break;
            }
        }

        super.paintComponent(g);
    }

    public class VIdCheckBoxFilter implements Filterable {

        private Long id;
        private Long parentId;

        public VIdCheckBoxFilter(Long id, Long parentId) {
            this.id = id;
            this.parentId = parentId;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getParentId() {
            return parentId;
        }

        public void setParentId(Long parentId) {
            this.parentId = parentId;
        }

        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            final VIdCheckBoxFilter filter = (VIdCheckBoxFilter) o;

            if (id != null ? !id.equals(filter.id) : filter.id != null) return false;
            return !(parentId != null ? !parentId.equals(filter.parentId) : filter.parentId != null);
        }

        public int hashCode() {
            int result;
            result = (id != null ? id.hashCode() : 0);
            result = 29 * result + (parentId != null ? parentId.hashCode() : 0);
            return result;
        }
    }
}