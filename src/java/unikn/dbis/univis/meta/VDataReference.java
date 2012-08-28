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
package unikn.dbis.univis.meta;

import java.util.Set;

/**
 * The <code>VDataReference</code> contains information about
 * the data of the object that references this object.
 * <p/>
 * User: raedler, weiler
 * Date: 09.04.2006
 * Time: 11:00:32
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: VDataReference.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.1
 */
public interface VDataReference {

    /**
     * Returns the key of the data reference. This
     * key should be a unique key.
     *
     * @return The key of the data reference.
     */
    public String getKey();

    /**
     * Sets the key of the data reference. This key
     * should be a unique key.
     *
     * @param key The key of the data reference.
     */
    public void setKey(String key);

    /**
     * Returns the internationalization key to allow
     * international application support.
     *
     * @return The i18n key to allow international application
     *         support.
     */
    public String getI18nKey();

    /**
     * Sets the internationalization key to allow international
     * application support.
     *
     * @param i18nKey The i18n key to allow international
     *                application support.
     */
    public void setI18nKey(String i18nKey);

    /**
     * Returns the join attribute.
     *
     * @return The join attribute to join tables.
     */
    public String getForeignKey();

    /**
     * Sets the joinable attribute.
     *
     * @param joinable The joinable key shows the join
     *                 attribute.
     */
    public void setForeignKey(String joinable);

    /**
     * Returns the name of the table that contains the
     * data.
     *
     * @return The name of the table that contains the
     *         data.
     */
    public String getTableName();

    /**
     * Sets the name of the table that contains the
     * data.
     *
     * @param tableName The name of the table that contains
     *                  the data.
     */
    public void setTableName(String tableName);

    /**
     * TODO: document me!!!
     *
     * @return
     */
    public VDataReference getParent();

    /**
     * TODO: document me!!!
     *
     * @param parent
     */
    public void setParent(VDataReference parent);

    /**
     * TODO: document me!!!
     *
     * @return
     */
    public Set<VDataReference> getChildren();

    /**
     * TODO: document me!!!
     *
     * @param children
     */
    public void setChildren(Set<VDataReference> children);

    /**
     * Returns whether the data reference is enabled
     * or not.
     *
     * @return Whether the data reference is enabled
     *         or not.
     */
    public boolean isEnabled();

    /**
     * Sets whether the data reference is enabled
     * or not.
     *
     * @param enabled Whether the data reference is enabled
     *                or not.
     */
    public void setEnabled(boolean enabled);

    /**
     * Returns the decendants of this data reference.
     *
     * @return The decendants of this data reference within a
     *         <code>Set</code>.
     */
    public Set<VDataReference> getDescendants();

    /**
     * Returns the name that will be shown as tree label or
     * anywhere else on the frontend.
     *
     * @return The label that will be displayed on the
     *         frontend.
     */
    public String toString();
}