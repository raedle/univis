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
package unikn.dbis.univis.message.swing;

import unikn.dbis.univis.message.Internationalizable;
import unikn.dbis.univis.message.MessageResolver;

import javax.swing.*;

/**
 * The <code>VRadioButtonMenuItem</code> extends the <code>JRadioButtonMenuItem</code>
 * and provides an easy way to internationlize the content of the radio button.
 * <p/>
 * User: raedler, weiler
 * Date: 07.07.2006
 * Time: 11:58:50
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: VRadioButtonMenuItem.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.1
 * @deprecated doesn't work with {@link unikn.dbis.univis.util.ComponentUtilities#repaintComponentTree(java.awt.Component)}
 */
public class VRadioButtonMenuItem extends JRadioButtonMenuItem implements Internationalizable {

    // The i18n key to internationlize the label text.
    private String i18nKey;

    /**
     * Creates a <code>JRadioButtonMenuItem</code> with text.
     *
     * @param i18nKey The i18n key.
     */
    public VRadioButtonMenuItem(String i18nKey) {
        this.i18nKey = i18nKey;
    }

    /**
     * Creates a radio button menu item with the resolved i18n text
     * and <code>Icon</code>.
     *
     * @param i18nKey The i18n key.
     * @param icon    the icon to display on the <code>JRadioButtonMenuItem</code>
     */
    public VRadioButtonMenuItem(String i18nKey, Icon icon) {
        super(MessageResolver.getMessage(i18nKey), icon);
        this.i18nKey = i18nKey;
    }

    /**
     * Creates a radio button menu item with the resolved i18n text
     * and selection state.
     *
     * @param i18nKey  The i18n key.
     * @param selected the selected state of the <code>CheckBoxMenuItem</code>
     */
    public VRadioButtonMenuItem(String i18nKey, boolean selected) {
        super(MessageResolver.getMessage(i18nKey), selected);
        this.i18nKey = i18nKey;
    }

    /**
     * Creates a radio button menu item that has the resolved i18n
     * text, image, and selection state.  All other constructors
     * defer to this one.
     *
     * @param i18nKey The i18n key.
     * @param icon    the image that the button should display
     */
    public VRadioButtonMenuItem(String i18nKey, Icon icon, boolean selected) {
        super(MessageResolver.getMessage(i18nKey), icon, selected);
        this.i18nKey = i18nKey;
    }

    /**
     * Sets the i18n key to resolve the internationalized
     * message.
     *
     * @param i18nKey The i18n key.
     */
    public void setI18NKey(String i18nKey) {
        this.i18nKey = i18nKey;
        internationalize();
    }

    public void internationalize() {
        setText(MessageResolver.getMessage(i18nKey));
    }
}