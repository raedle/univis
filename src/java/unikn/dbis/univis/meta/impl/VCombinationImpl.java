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
package unikn.dbis.univis.meta.impl;

import unikn.dbis.univis.meta.*;

/**
 * TODO: document me!!!
 * <p/>
 * <code>VCombinationImpl</code>.
 * <p/>
 * User: raedler, weiler
 * Date: 29.08.2006
 * Time: 01:23:24
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: VCombinationImpl.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.2
 */
public class VCombinationImpl implements VCombination {

    private VCube cube;
    private VDimension dimension;
    private VMeasure measure;
    private VFunction function;

    public VCube getCube() {
        return cube;
    }

    public void setCube(VCube cube) {
        this.cube = cube;
    }

    public VDimension getDimension() {
        return dimension;
    }

    public void setDimension(VDimension dimension) {
        this.dimension = dimension;
    }

    public VMeasure getMeasure() {
        return measure;
    }

    public void setMeasure(VMeasure measure) {
        this.measure = measure;
    }

    public VFunction getFunction() {
        return function;
    }

    public void setFunction(VFunction function) {
        this.function = function;
    }
}
