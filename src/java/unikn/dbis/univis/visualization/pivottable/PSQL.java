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

import java.sql.*;
import unikn.dbis.univis.explorer.VExplorer;

/**
 * PostgreSQL Connection Manager
 *
 * @author Christian Gruen
 * @author Marion Herb
 */
public class PSQL{
    Connection conn = null;
    Statement state;
    ResultSet result;
    String measure;
    boolean connected = false;

    /**
     * connect to database
     *
     * @return true if everything went alright
     */
    boolean connect() {
        try {
            if (!connected) {
                System.out.println("PSQL: Connecting...");
                conn = VExplorer.getConnection();
                state = conn.createStatement();
                connected = true;
                System.out.println("PSQL: Connected");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return connected;
    }

    /**
     * get data for specified table
     *
     * @param table table to be read
     * @return contents of database table
     */
    String[][] getData(String table) {
        if (!connect())
            return null;

        try {
            execute("select count(*) from " + table);
            result.next();
            int size = result.getInt(1);

            execute("select * from " + table);
            String[][] values = new String[size][];
            for (int i = 0; i < size; i++) {
                values[i] = next();
            }
            return values;
        }
        catch (SQLException e) {
            System.out.println("PSQL: Could not get data for table '" + table
                    + "': " + e.getMessage());
            e.printStackTrace();
            return null;
        }
        finally {
            close();
        }
    }

    /**
     * executes an sql statement.
     *
     * @param query sql query
     * @return true if everything went alright
     */
    boolean execute(String query) {
        if (!connect())
            return false;

        try {
            System.out.println("PSQL: Executing Command:\n########## SQL (start) ########## \n"
                    + query + "\n########## SQL (end) ##########");
            result = state.executeQuery(query);
            return true;
        }
        catch (SQLException e) {
            System.out.println("PSQL: Could not execute SQL Command:\n '"
                    + query + "': " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @return empty
     */
    String[] next() {
        try {
            if (!result.next()) {
                return null;
            }

            ResultSetMetaData rsmd = result.getMetaData();
            int cols = rsmd.getColumnCount();
            String[] values = new String[cols];
            for (int i = 1; i <= cols; i++) {
                String type = rsmd.getColumnTypeName(i);
                if (type.equals("integer"))
                    values[i - 1] = "" + result.getInt(i);
                else
                    values[i - 1] = result.getString(i);
            }
            return values;
        }
        catch (SQLException e) {
            System.out.println("PSQL: Error fetching next row: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    Object[] nextAsObject() {
        try {
            if (!result.next()) {
                return null;
            }

            ResultSetMetaData rsmd = result.getMetaData();
            int cols = rsmd.getColumnCount();
            Object[] values = new Object[cols];
            for (int i = 1; i <= cols; i++) {
                values[i - 1] = result.getObject(i);
            }
            return values;
        }
        catch (SQLException e) {
            System.out.println("PSQL: Error fetching next row: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    void close() {
        if (result != null)
            try {
                result.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        if (state != null)
            try {
                state.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        if (conn != null) {
            try {
                conn.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        this.connected = false;
        System.out.println("PSQL: Closed");
    }
}