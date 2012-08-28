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
package unikn.dbis.univis.visualization;

import java.awt.*;

/**
 * TODO: document me!!!
 * <p/>
 * AbstractRenderer.
 * <p/>
 * User: raedler, weiler
 * Date: 10.05.2006
 * Time: 12:51:29
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: AbstractRenderer.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.1
 */
public abstract class AbstractRenderer implements Renderable {

    // The rendered component or null.
    private Component component;

    /**
     * Renders a component that could be used
     * to display on screen.
     *
     * @return The component that could be used
     *         to display on screen.
     */
    public Component render() {
        if (component == null) {
            component = renderComponent();
        }
        return component;
    }

    /**
     * Updates the rendered component. It's similar to
     * rerendering the component.
     */
    public void update() {
        component = renderComponent();
    }

    /**
     * Renders the component that should be displayed
     * at screen.
     *
     * @return The component that should be displayed
     *         at screen.
     */
    public abstract Component renderComponent();
}