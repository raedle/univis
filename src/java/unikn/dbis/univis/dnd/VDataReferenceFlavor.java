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
package unikn.dbis.univis.dnd;

import unikn.dbis.univis.meta.VDimension;
import unikn.dbis.univis.meta.VMeasure;
import unikn.dbis.univis.meta.VFunction;
import unikn.dbis.univis.meta.VCombination;

import java.awt.datatransfer.DataFlavor;

/**
 * TODO: document me!!!
 * <p/>
 * <code>VDataReferenceFlavor</code>.
 * <p/>
 * User: raedler, weiler
 * Date: 11.04.2006
 * Time: 18:57:42
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: VDataReferenceFlavor.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.1
 */
public class VDataReferenceFlavor extends DataFlavor {

    public static final VDataReferenceFlavor COMBINATION_FLAVOR = new VDataReferenceFlavor(VCombination.class, "UniVis Explorer/VCombination");

    public static final VDataReferenceFlavor DIMENSION_FLAVOR = new VDataReferenceFlavor(VDimension.class, "UniVis Explorer/VDimension");

    public static final VDataReferenceFlavor MEASURE_FLAVOR = new VDataReferenceFlavor(VMeasure.class, "UniVis Explorer/VMeasure");

    public static final VDataReferenceFlavor FUNCTION_FLAVOR = new VDataReferenceFlavor(VFunction.class, "UniVis Explorer/VFunction");

    /**
     * Constructs a new <code>DataFlavor</code>.  This constructor is
     * provided only for the purpose of supporting the
     * <code>Externalizable</code> interface.  It is not
     * intended for public (client) use.
     *
     * @since 1.2
     */
    public VDataReferenceFlavor() {
        super();
    }

    /**
     * Constructs a <code>DataFlavor</code> that represents a Java class.
     * <p/>
     * The returned <code>DataFlavor</code> will have the following
     * characteristics:
     * <pre>
     *    representationClass = representationClass
     *    mimeType            = application/x-java-serialized-object
     * </pre>
     *
     * @param representationClass  the class used to transfer data in this flavor
     * @param humanPresentableName the human-readable string used to identify
     *                             this flavor; if this parameter is <code>null</code>
     *                             then the value of the the MIME Content Type is used
     * @throws NullPointerException if <code>representationClass</code> is null
     */
    public VDataReferenceFlavor(Class<?> representationClass, String humanPresentableName) {
        super(representationClass, humanPresentableName);
    }
}