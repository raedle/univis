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
package unikn.dbis.univis.visualization.pivottable;

import unikn.dbis.univis.meta.*;

import java.util.Vector;
import java.text.MessageFormat;

/**
 * TODO: document me!!!
 * <p/>
 * The <code>VPivotTableData</code> organizes the pivot table data structure.
 * <p/>
 * User: herb, raedler
 * Date: 28.08.2006
 * Time: 17:35:44
 *
 * @author Roman R&auml;dle
 * @author Marion Herb
 * @version $Id: VPivotTableData.java 340 2006-10-11 12:05:47Z maherb $
 * @since UniVis Explorer 0.2
 */
public class VPivotTableData {

    private enum PivotType {
        X_Y,
        X,
        Y
    }

    private PivotType pivotType;

    private VCube cube;
    private VMeasure measure;
    private VFunction function;

//    private List<VDimension> xAxisDimensions = new ArrayList<VDimension>();
//    private List<VDimension> yAxisDimensions = new ArrayList<VDimension>();
    private Vector<VDimension> xAxisDimensions = new Vector<VDimension>();
    private Vector<VDimension> yAxisDimensions = new Vector<VDimension>();

    int amountCols;

    int groupsInY;

    //Vector allJoinTables;

    // Anzeige für NULL-Values in den Dimensions-Elementen
    static String NULL_VALUE = "*NULL*";
    PSQL myPSQL = new PSQL();

    public VPivotTableData(VCube cube, VMeasure measure, VFunction function) {
        this.cube = cube;
        this.measure = measure;
        this.function = function;
    }

    /**
     * get data for Pivottable
     *
     * @param xAxisDimensions
     * @param yAxisDimensions
     * @return Returns a Vector with data for pivottable
     */
    public Vector getPivottableData(Vector<VDimension> xAxisDimensions, Vector<VDimension> yAxisDimensions) {
        this.xAxisDimensions = xAxisDimensions;
        this.yAxisDimensions = yAxisDimensions;

        //allJoinTables = buildJoinTables();

        // Detects the pivot type.
        detectPivotType();

        String sql;
        String sql_select;
        String sql_from = "";
        String sql_order = "";

        boolean buildSelect = false;
        boolean buildFrom = false;
        boolean buildOrder = false;

        /* 1. Zusammenbasteln des SQLs: zuerst SELECT incl. CASE-Abfragen ... */
        sql_select = buildSqlSelect();
        if (sql_select != null)
            buildSelect = true;

        /* 2. ... dann FROM-Block inclusive JOINS ... */
        if (buildSelect)
            sql_from = buildSqlFrom();
        if (sql_from != null)
            buildFrom = true;

        /* 3. ... dann ORDER-BY-Block ... */
        if (buildFrom)
            sql_order = buildSqlOrder();
        if (sql_order != null)
            buildOrder = true;

        Vector values = new Vector();
        // now execute SQL and move tupels into Vector for display in JTable
        if (buildSelect && buildFrom && buildOrder) {
            try {
                sql = sql_select + sql_from + sql_order;
                myPSQL.execute(sql);

                Object[] resultTemp;
                while (!myPSQL.result.isLast()) {
                    Vector valuesSingleRow = new Vector();
                    /* numbers as Integers or Doubles will align right automatically */
                    resultTemp = myPSQL.nextAsObject();
                    for (int i = 0; i < resultTemp.length; i++) {
                        valuesSingleRow.addElement(resultTemp[i]);
                    }
                    values.addElement(valuesSingleRow);
                }

            }
            catch (Exception e) {
                System.out.println("PSQL: Could not get data for Pivottable-Data Query!");
                e.printStackTrace();
                return null;
            }
            finally {
                myPSQL.close();
            }
        }
        return values;
    }

    /**
     * Get the header info for pivot table
     *
     * @param xAxisDimensions
     * @param yAxisDimensions
     * @return Returns String[] of column names
     */
    public Vector<String> getPivottableHeader(Vector<VDimension> xAxisDimensions, Vector<VDimension> yAxisDimensions) {

        int size = yAxisDimensions.size();
        if (xAxisDimensions.size() > 0) {
            size += getAmountOfTableRows(xAxisDimensions.get(0));
        }

        Vector<String> columnNames = new Vector<String>();

        for (VDimension dimension : yAxisDimensions) {
            columnNames.addElement(dimension.toString());
            //columnNames.addElement(dimension.getTableName());
        }

        try {
            switch (pivotType) {
                case Y:
                    columnNames.addElement("SUM");
                    break;
                case X:
                    myPSQL.execute("select name from " + (xAxisDimensions.get(0)).getTableName() + ";");

                    for (int i = yAxisDimensions.size(); i < size; i++) {
                        columnNames.addElement(myPSQL.next()[0]);
                    }
                    break;
                case X_Y:
                    myPSQL.execute("select name from " + (xAxisDimensions.get(0)).getTableName() + ";");

                    for (int i = yAxisDimensions.size(); i < size; i++) {
                        columnNames.addElement(myPSQL.next()[0]);
                    }
                    break;
                default:
                    break;
            }

            return columnNames;
        }
        catch (Exception e) {
            System.out.println("PSQL: Could not get data for Pivottable-Header Query.");
            e.printStackTrace();
            return null;
        }
        finally {
            myPSQL.close();
        }
    }

    private void detectPivotType() {
        int groupedXValues = xAxisDimensions.size();
        int groupedYValues = yAxisDimensions.size();

        if ((groupedYValues > 0) && (groupedXValues > 0)) {
            pivotType = PivotType.X_Y; // PivotType.X_Y => Values on x-axis and y-axis.
        }
        else if (groupedYValues == 0) {
            pivotType = PivotType.X; // PivotType.X => Only values on x-axis.
        }
        else if (groupedXValues == 0) {
            pivotType = PivotType.Y; // PivotType.Y => Only values on y-axis.
        }
    }

    /*
    * SQL-Teil bauen welcher alle Spalten definiert: also alle Gruppierungen,
    * und anschliessend Platzhalter für alle Spalten in X-Richtung
    * Sonderfall: wenn kein Element für X-Achse gegeben -> in X-Achse wird nur die
    * Summe für die Gruppierung auf der Y-Achse geschrieben, also nur 1 Spalte
    */
    private String buildSqlSelect() {

        StringBuilder select = new StringBuilder();

        // abbrechen wenn keine Werte auf X-Achse denn dann wird lediglich ein
        // Subselect benötigt, und kein Mergen verschiedener Selects
        if (PivotType.Y.equals(pivotType)) return ""; // besser NULL aber nicht möglich

        select.append("SELECT \n");

        int instancesOnXAxis;

        /* momentan noch nur 1 Element */
        instancesOnXAxis = getAmountOfTableRows(xAxisDimensions.get(0));

        amountCols = instancesOnXAxis;
        // bei mehr als x Spalten wird abgebrochen -> entspricht Subselects -> tatsächliche Grenze??
        if (amountCols > 150) {
            System.out.println(amountCols + " Columns are too many for X-Axis! Please transform your query.");
            return null;
        }

        int groupedYValues = yAxisDimensions.size();

        /*
        * outer loop runs through elements displayed in x-axis ->
        * instancesOnXAxis + groupedYValues
        */
        for (int i = 0; i < (instancesOnXAxis + groupedYValues); i++) {
            if (i != 0) {
                select.append(",\n");
            }

            select.append("CASE ");

            /* inner loop runs through subselect-joins -> instancesOnXAxis */
            if (i < groupedYValues) {
                for (int j = 0; j < instancesOnXAxis; j++) {
                    select.append("\n\tWHEN not t" + j + ".y" + i
                            + " isnull THEN t" + j + ".y" + i);
                }
                select.append("\n");
                select.append("END");
                select.append(" AS y" + i);
            }
            else {
                select.append("WHEN \"y" + i + "\"");
                select.append(" isnull THEN 0 ELSE \"y" + i + "\" ");
                select.append("END");
            }
        }
        select.append("\n\n");

        return select.toString();
    }

    /*
      * Subselects generieren
      */
    private String buildSqlFrom() {

        int groupedYValues = yAxisDimensions.size();

        // aneinanderhängen der Subselects entweder mit FULL JOIN (wenn Werte auf Y-Achse vorhanden)
        // oder Konkatenation (wenn nur Werte auf X-Achse)
        String attached = new String();
//		if (groupedYValues > 0) {
        if (PivotType.X_Y.equals(pivotType)) {
            attached = "\nFULL JOIN\n";
        }
        else if (PivotType.X.equals(pivotType)) {
            attached = "\n, \n";
        }

        StringBuffer from = new StringBuffer();

        try {

            int upperBorder = 1; // damit Schleife 1x durchlaufen wird
            // nur wenn mind. 1 Wert auf X-Achse ist kann man seine Instanzen holen
            // also Ausnahme bei Anfrage bei nur Knote auf Y-Achse, dann ist per default upperBorder=-1
            if (!PivotType.Y.equals(pivotType)) {
                VDimension dimension = xAxisDimensions.get(0);
                upperBorder = getAmountOfTableRows(dimension);
                myPSQL.execute("select name from " + dimension.getTableName() + ";");
                from.append("FROM ");
            }

            String xDimTable = "";

            /* loop through elements on x-axis for creating the subselects */
            for (int instanceOnX = 0; instanceOnX < upperBorder; instanceOnX++) {
                /* Loop for which instance? e.g. "mit Bachelorabschluss", "I"... */
                if (!PivotType.Y.equals(pivotType)) {
                    xDimTable = myPSQL.next()[0];
                    from.append("\n( ");
                }

                from.append("SELECT\n");
                /* loop through elements on y-axis */
                /* SELECT elements */
                String fromTable;

                //Vector uniqueY = buildUniqueNodes(yAxisDimensions);

                int i = 0;
                for (VDimension dimension : yAxisDimensions) {
                    from.append("CASE WHEN ");
                    from.append(dimension.getTableName() + ".name");
                    from.append(" isnull THEN '" + NULL_VALUE + "' ELSE " + dimension.getTableName() + ".name");
                    from.append(" END AS y" + i++);
                    from.append(",\n");
                }

                /*
                     * um Measure CASE WHEN-Abfrage: denn bei nur Wert auf Y-Achse kann es vorkommen
                     * z.B. wenn man HZP angezeigt haben will, dass 1. Wert in Spalte NULL ist,
                     * dann kracht es bei getValueAt() von EnvelopeTableModell der auf NULL trifft
                     * "CASE WHEN sum(SOS_CUBE.koepfe) isnull THEN '*NULL*' ELSE sum(SOS_CUBE.koepfe) END"
                     */
                if (PivotType.Y.equals(pivotType)) {
                    from.append("CASE WHEN ");
                }

                // Merge function [e.g. SUM({0})] with measure [e.g. cases]. E.g. merge result [SUM(cases)]
                String cubeAttribute = MessageFormat.format("SUM({0})", cube.getTableName() + "." + measure.getColumn());

                from.append(cubeAttribute);

                if (PivotType.Y.equals(pivotType)) {
                    from.append(" isnull THEN 0 ELSE " + cubeAttribute + " END");
                    //					from.append("\"y" + (instanceOnX+j) + "\" -- " + xDimTable);
                }
                from.append(" AS \"y" + (instanceOnX + i) + "\"");
                from.append("\n");

                /*
                     * JOINS
                     */
                from.append("FROM " + cube.getTableName() + "\n");

                /*
                     * Loop through all needed tables in order to joinDimensions them all to
                     * the fact table, important to first joinDimensions all Blueps to fact table
                     * and after that the dimensions because otherwise syntax errors appear
                     */
                // 1 because first element was cube, already taken out
                Vector<VDimension> joinDimensions = new Vector<VDimension>();
                joinDimensions.addAll(xAxisDimensions);
                joinDimensions.addAll(yAxisDimensions);

                for (VDimension dimension : joinDimensions) {

                    if (!dimension.getBlueprint().equals(dimension)) {
                        VDimension blueprint = dimension.getBlueprint();

                        from.append("FULL JOIN " + blueprint.getTableName() + " ON ( " + blueprint.getTableName() + ".id = ");
                        from.append(cube.getTableName() + "." + blueprint.getForeignKey());
                        from.append(" ) " + "\n");

                        from.append("FULL JOIN " + dimension.getTableName() + " ON ( " + dimension.getTableName() + ".id = ");
                        from.append(dimension.getBlueprint().getTableName() + "." + dimension.getForeignKey());
                        from.append(" ) " + "\n");
                    }
                    else {
                        from.append("FULL JOIN " + dimension.getTableName() + " ON ( " + dimension.getTableName() + ".id = ");
                        from.append(cube.getTableName() + "." + dimension.getForeignKey());
                        from.append(" ) " + "\n");
                    }
                }

                /* WHERE */
                if (!PivotType.Y.equals(pivotType)) {
                    from.append("WHERE " + xAxisDimensions.get(0).getTableName() + ".name = '" + xDimTable + "'\n");
                }

                /* GROUP BY */
                if (PivotType.X_Y.equals(pivotType) || PivotType.Y.equals(pivotType)) {
                    from.append("GROUP BY ");
                    for (int l = 0; l < groupedYValues; l++) {
                        from.append(yAxisDimensions.get(l).getTableName() + ".name");
                        if (l < (groupedYValues - 1))
                            from.append(", ");
                    }
                }
                if (!PivotType.Y.equals(pivotType)) {
                    from.append("\n ) AS t" + instanceOnX + "\n");
                }
                // wenn Werte auf Y-Achse:
                /* subselects need a joinDimensions-criteria */
//				if (groupedYValues > 0) {
                if (PivotType.X_Y.equals(pivotType)) {
                    if (instanceOnX > 0) {
                        from.append("\nON (");
                        /* loop through elements on yAxis */
                        for (int outer = 0; outer < groupedYValues; outer++) {
                            from.append("\nCASE");
                            /* loop through amount of subselects */
                            for (int inner = 0; inner < instanceOnX; inner++) {
                                from.append("\n\tWHEN not t" + inner + ".y" + outer
                                        + " isnull THEN ");
                                from.append("t" + inner + ".y" + outer);
                            }
                            from.append("\nEND = t" + instanceOnX + ".y" + outer + "\n");
                            if (outer < (groupedYValues - 1))
                                from.append("AND ");
                        }
                        from.append(")\n");
                    }
                }

                // aneinanderhängen der Subselects
                // entweder mit "FULL JOIN" oder ","
                if (instanceOnX != (upperBorder - 1))
                    from.append(attached);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("PSQL: Could not execute From Query.");
            return null;
        }
        finally {
            myPSQL.close();
        }
        return from.toString();
    }

    /*
      * Order-Klausel generieren
      */
    private String buildSqlOrder() {

        StringBuffer orderBy = new StringBuffer();
        // order by nicht nötig bei Werten nur in X-Achse
        if (!PivotType.X.equals(pivotType)) {
            orderBy.append("\nORDER BY ");
            for (int i = 0; i < yAxisDimensions.size(); i++) {
                if (i != 0)
                    orderBy.append(", ");
                orderBy.append("y" + i);
            }
        }
        return orderBy.append(";").toString();
    }

    /*
    /**
      * Eliminiert Duplikate in Nodes
      * Bedeutung: wenn Node mehrmals gedroppt wird, findet trotzdem nur einmalige
      * Gruppierung danach statt
      *
    private Vector<VDimension> buildUniqueNodes(Vector<VDimension> axisDimensions) {
        int groupedYValues = yAxisDimensions.size();
        Vector<VDimension> uniqueNodes = new Vector<VDimension>();

        for (int i = 0; i < groupedYValues; i++) {
            if (!uniqueNodes.contains(axisDimensions.elementAt(i)))
                uniqueNodes.add(axisDimensions.elementAt(i));
        }
        return uniqueNodes;
    }
    */

    /*
      * which ones are needed? connecting fact table with all tables in x- as
      * well as in y-axis, as well as all bluep-tables on the way down to
      * those dim-tables
      *
    private Vector buildJoinTables() {
        int nodesOnXAxis = xAxisDimensions.size();
        int nodesOnYAxis = yAxisDimensions.size();

        Vector<VDimension> joinDimensions = new Vector<VDimension>();

        // zuerst Faktentabelle in Vektor
        // man nehme zufaelligen Knoten aus einem Vektor und holt Cube dazu
        // -> es wird momentan nur 1 Cube angezeigt und keine Schneidung mehrerer

        // wichtig: alle Blueps jeweils vor ihren Dims einsortieren
        // first all entries from x-axis ...
        if (!xAxisDimensions.isEmpty()) { // -> es exisitieren Werte in X-Achse

            for (int i = 0; i < nodesOnXAxis; i++) {
                VDimension dimension = xAxisDimensions.get(i);
                // if not already inserted
                if (!joinDimensions.contains(dimension)) {

                    joinDimensions.add(dimension);

                    VDimension blueprint = dimension.getBlueprint();
                    if (!joinDimensions.contains(blueprint)) {
                        joinDimensions.add(blueprint);
                    }
                }
            }
            groupsInY = 0;
        }

        // ... then all entries from y-axis
        if (!yAxisDimensions.isEmpty()) { // -> es exisitieren Werte in Y-Achse
            // nur Cube adden falls noch keiner drin!
            if (xAxisDimensions.isEmpty()) {
                // TODO joinDimensions.add(yAxisDimensions.get(0).getCube());
            }
            for (int i = 0; i < nodesOnYAxis; i++) {
                VDimension dimension = yAxisDimensions.get(i);
                // if not already inserted
                if (!joinDimensions.contains(dimension)) {

                    joinDimensions.add(dimension);

                    VDimension blueprint = dimension.getBlueprint();
                    if (!joinDimensions.contains(blueprint)) {
                        joinDimensions.add(blueprint);
                    }

                    groupsInY ++;
                }
            }
        }
//		System.out.println("groupsInY " + groupsInY);
        return joinDimensions;
    }
    */

    /*
      * Methode holt Anzahl der Dimensions-Elemente in X-Achse also Anzahl Spalten
      */
    private int getAmountOfTableRows(VDimension dimension) {
        int i = -1;

        try {
            myPSQL.execute("select count(*) from " + dimension.getTableName() + ";");
            myPSQL.result.next();
            i = myPSQL.result.getInt(1);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("PSQL: Could not retrieve amount of table rows.");
        }
        finally {
            myPSQL.close();
        }

        return i;
    }
}