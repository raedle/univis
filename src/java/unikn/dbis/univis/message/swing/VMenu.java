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
 * TODO: document me!!!
 * <p/>
 * <code>VMenuItem</code>.
 * <p/>
 * User: raedler, weiler
 * Date: 28.08.2006
 * Time: 19:52:13
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: VMenu.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.2
 */
public class VMenu extends JMenu implements Internationalizable {

    private String i18nKey;

    /**
     * @param i18nKey
     */
    public VMenu(String i18nKey) {
        super(MessageResolver.getMessage(i18nKey));
        this.i18nKey = i18nKey;
    }

    public void internationalize() {
        setText(MessageResolver.getMessage(i18nKey));
    }
}
