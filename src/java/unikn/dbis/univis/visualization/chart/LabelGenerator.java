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
package unikn.dbis.univis.visualization.chart;

import org.jfree.chart.labels.CategorySeriesLabelGenerator;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;

import java.text.DecimalFormat;
import java.text.AttributedString;

/**
 * TODO: document me!!!
 * <p/>
 * <code>LabelGenerator</code>.
 * <p/>
 * User: raedler, weiler
 * Date: 28.04.2006
 * Time: 23:09:51
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: LabelGenerator.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.1
 */
public class LabelGenerator implements CategorySeriesLabelGenerator, PieSectionLabelGenerator {

    // Total amount of the chart.
    private int total;

    // Full 100 procent.
    private double full = 100.0;

    // Format for the Total.
    private DecimalFormat decimalFormat = new DecimalFormat("#.##");

    /**
     * @param total
     */
    public LabelGenerator(int total) {
        this.total = total;
    }

    /**
     * Makes a own legend.
     *
     * @param dataset
     * @param series
     * @return String for nice legend.
     */
    public String generateLabel(CategoryDataset dataset, int series) {
        Integer value = dataset.getValue(series, 0).intValue();
        Comparable name = dataset.getRowKey(series);
        Double percent = ((full / total) * value);

        return name.toString() + " = " + value.toString() + " -> " + decimalFormat.format(percent) + "%";
    }

    /**
     * Generates a label for a pie section.
     *
     * @param dataset the dataset (<code>null</code> not permitted).
     * @param key     the section key (<code>null</code> not permitted).
     * @return The label (possibly <code>null</code>).
     */
    public String generateSectionLabel(PieDataset dataset, Comparable key) {
        Integer value = dataset.getValue(key).intValue();
        Comparable name = dataset.getKey(dataset.getIndex(key));
        Double percent = ((full / total) * value);

        return name.toString() + " = " + value.toString() + " -> " + decimalFormat.format(percent) + "%";
    }

    /**
     * @param dataset the dataset.
     * @param key     the key.
     * @return An attributed label (possibly <code>null</code>).
     */
    public AttributedString generateAttributedSectionLabel(PieDataset dataset, Comparable key) {
        return null;
    }
}