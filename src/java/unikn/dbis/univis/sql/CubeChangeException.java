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
package unikn.dbis.univis.sql;

/**
 * TODO: document me!!!
 * <p/>
 * <code>CubeChangeException</code>.
 * <p/>
 * User: raedler, weiler
 * Date: 29.08.2006
 * Time: 12:53:24
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: CubeChangeException.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.2
 */
public class CubeChangeException extends Exception {
    
    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public CubeChangeException(String message) {
        super(message);
    }
}
