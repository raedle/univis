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
package unikn.dbis.univis.sql.dialect;

import org.hibernate.dialect.Dialect;

/**
 * TODO: document me!!!
 * <p/>
 * <code>UniVisDialect</code>.
 * <p/>
 * User: raedler, weiler
 * Date: 10.04.2006
 * Time: 01:37:14
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: UniVisDialect.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.1
 */
public abstract class UniVisDialect extends Dialect {
    public abstract String getSelect();

    public static String generateTableAlias(String rootAlias, int tableNumber) {
        if (tableNumber == 0) return rootAlias;
        StringBuffer buf = new StringBuffer().append(rootAlias);
        if (!rootAlias.endsWith("_")) buf.append('_');
        return buf.append(tableNumber).append('_').toString();
    }

    public void test() {
        //super.createOuterJoinFragment().addCrossJoin();
    }
}
