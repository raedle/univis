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
package unikn.dbis.univis.visualization.graph;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * TODO: document me!!!
 * <p/>
 * <code>VHistoryList</code>.
 * <p/>
 * User: raedler, weiler
 * Date: 15.05.2006
 * Time: 17:06:14
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: VHistoryList.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.1
 */
public class VHistoryList<T> {

    private List<T> current = new ArrayList<T>();
    private List<List<T>> history = new ArrayList<List<T>>();

    public void add(T o) {
        current.add(o);
    }

    public void get(int index) {
        current.get(index);
    }

    public List<T> getLastOfHistory() {
        if (history.size() > 0) {
            return history.get(history.size() - 1);
        }

        throw new IllegalStateException("The history doesn't contain elements.");
    }

    public List<T> getCurrent() {
        return current;
    }

    public void historize() {
        history.add(current);
        current = new ArrayList<T>();
    }

    public void historyBack() {
        current = history.remove(history.size() - 1);
    }

    public void reset() {
        current.clear();
        history.clear();
    }
}
