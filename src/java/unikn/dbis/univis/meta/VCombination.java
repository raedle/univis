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

/**
 * TODO: document me!!!
 * <p/>
 * <code>VCombination</code>.
 * <p/>
 * User: raedler, weiler
 * Date: 29.08.2006
 * Time: 01:20:24
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: VCombination.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.2
 */
public interface VCombination {
    public VCube getCube();
    public void setCube(VCube cube);
    public VDimension getDimension();
    public void setDimension(VDimension dimension);
    public VMeasure getMeasure();
    public void setMeasure(VMeasure measure);
    public VFunction getFunction();
    public void setFunction(VFunction function);
}
