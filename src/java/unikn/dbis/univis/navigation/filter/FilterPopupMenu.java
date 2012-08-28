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
package unikn.dbis.univis.navigation.filter;

import javax.swing.*;
import java.awt.*;

/**
 * TODO: document me!!!
 * <p/>
 * <code>FilterPopupMenu</code>.
 * <p/>
 * User: raedler
 * Date: 27.09.2006
 * Time: 14:35:18
 *
 * @author Roman R&auml;dle
 * @version $Id: FilterPopupMenu.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.3
 */
public class FilterPopupMenu extends JPopupMenu {

    /**
     * Constructs a <code>JPopupMenu</code> without an "invoker".
     *
     * @param headline
     */
    public FilterPopupMenu(String headline) {

        JLabel header = new JLabel(headline);

        Font font = header.getFont();
        header.setFont(font.deriveFont(Font.BOLD, font.getSize() + 2));

        add(header);
        add(new JPopupMenu.Separator());
    }
}
