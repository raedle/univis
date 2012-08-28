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
package unikn.dbis.univis.dataexchange.schemaimport;

import org.dom4j.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.Locale;
import java.util.Date;
import java.util.Map;
import java.math.BigInteger;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Time;
import java.text.ParseException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * TODO: document me!!!
 * <p/>
 * <code>ImportUtil</code>.
 * <p/>
 * User: raedler
 * Date: 27.09.2006
 * Time: 22:53:08
 *
 * @author Roman R&auml;dle
 * @version $Id: ImportUtil.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.3
 */
public class ImportUtil {

    private static final transient Log LOG = LogFactory.getLog(ImportUtil.class);

    // The pattern of the date. Using in getValue(...)
    protected final static String PARAM_DATE_PATTERN = "date_pattern";

    public static <T> T getValue(Document document, Class<T> type, String xpath) {
        return getValue(document, type, xpath, null);
    }

    /**
     * Returns the value at where the xpath is pointing to as type of the
     * <code>Class</code> type parameter. This method returns a default value
     * if no value was found at the xpath.
     *
     * @param xpath The relative xpath to the value.
     * @param type          The <code>Class</code> of the return value.
     * @param defaultValue  The default value that should return if no value was
     *                      found at the xpath.
     * @return The value of the <code>Class</code> of the type parameter.
     * @throws SchemaImportException This exception occurs while trying
     *                                      to get the value.
     */
    public static <T> T getValue(Document document, Class<T> type, String xpath, T defaultValue) throws SchemaImportException {

        String value = getContent(document, xpath);

        if (value == null) {
            return defaultValue;
        }

        if (String.class.isAssignableFrom(type)) {

            if (LOG.isDebugEnabled()) {
                LOG.debug("The type is assignable from " + String.class + " [XPath=\"" + xpath + "\", StringValue=\"" + value + "\"].");
            }

            //noinspection unchecked
            return (T) value;
        }
        else if (Number.class.isAssignableFrom(type)) {

            if (Long.class.isAssignableFrom(type)) {

                if (LOG.isDebugEnabled()) {
                    LOG.debug("The type is assignable from " + Long.class + " [XPath=\"" + xpath + "\", StringValue=\"" + value + "\"].");
                }

                //noinspection unchecked
                return (T) Long.valueOf(value);
            }
            else if (Integer.class.isAssignableFrom(type)) {

                if (LOG.isDebugEnabled()) {
                    LOG.debug("The type is assignable from " + Integer.class + " [XPath=\"" + xpath + "\", StringValue=\"" + value + "\"].");
                }

                //noinspection unchecked
                return (T) Integer.valueOf(value);
            }
            else if (Double.class.isAssignableFrom(type)) {

                if (LOG.isDebugEnabled()) {
                    LOG.debug("The type is assignable from " + Double.class + " [XPath=\"" + xpath + "\", StringValue=\"" + value + "\"].");
                }

                //noinspection unchecked
                return (T) Double.valueOf(value);
            }
            else if (Float.class.isAssignableFrom(type)) {

                if (LOG.isDebugEnabled()) {
                    LOG.debug("The type is assignable from " + Float.class + " [XPath=\"" + xpath + "\", StringValue=\"" + value + "\"].");
                }

                //noinspection unchecked
                return (T) Float.valueOf(value);
            }
            else if (Byte.class.isAssignableFrom(type)) {

                if (LOG.isDebugEnabled()) {
                    LOG.debug("The type is assignable from " + Byte.class + " [XPath=\"" + xpath + "\", StringValue=\"" + value + "\"].");
                }

                //noinspection unchecked
                return (T) Byte.valueOf(value);
            }
            else if (Short.class.isAssignableFrom(type)) {

                if (LOG.isDebugEnabled()) {
                    LOG.debug("The type is assignable from " + Short.class + " [XPath=\"" + xpath + "\", StringValue=\"" + value + "\"].");
                }

                //noinspection unchecked
                return (T) Short.valueOf(value);
            }
            else if (BigDecimal.class.isAssignableFrom(type)) {

                if (LOG.isDebugEnabled()) {
                    LOG.debug("The type is assignable from " + BigDecimal.class + " [XPath=\"" + xpath + "\", StringValue=\"" + value + "\"].");
                }

                //noinspection unchecked
                return (T) new BigDecimal(value);
            }
            else if (BigInteger.class.isAssignableFrom(type)) {

                if (LOG.isDebugEnabled()) {
                    LOG.debug("The type is assignable from " + BigInteger.class + " [XPath=\"" + xpath + "\", StringValue=\"" + value + "\"].");
                }

                //noinspection unchecked
                return (T) new BigInteger(value);
            }
        }
        else if (Boolean.class.isAssignableFrom(type)) {

            if (LOG.isDebugEnabled()) {
                LOG.debug("The type is assignable from " + Boolean.class + " [XPath=\"" + xpath + "\", StringValue=\"" + value + "\"].");
            }

            //noinspection unchecked
            return (T) Boolean.valueOf(value);
        }
        /*
        else if (java.util.Date.class.isAssignableFrom(type)) {

            if (Date.class.isAssignableFrom(type)) {

                if (LOG.isDebugEnabled()) {
                    LOG.debug("The type is assignable from " + Date.class + " [XPath=\"" + xpath + "\", StringValue=\"" + value + "\"].");
                }

                //noinspection unchecked
                return (T) new java.sql.Date(getDate(value, params).getTime());
            }
            else if (Time.class.isAssignableFrom(type)) {

                if (LOG.isDebugEnabled()) {
                    LOG.debug("The type is assignable from " + Time.class + " [XPath=\"" + xpath + "\", StringValue=\"" + value + "\"].");
                }

                //noinspection unchecked
                return (T) new java.sql.Time(getDate(value, params).getTime());
            }
            else if (Timestamp.class.isAssignableFrom(type)) {

                if (LOG.isDebugEnabled()) {
                    LOG.debug("The type is assignable from " + Timestamp.class + " [XPath=\"" + xpath + "\", StringValue=\"" + value + "\"].");
                }

                //noinspection unchecked
                return (T) new java.sql.Timestamp(getDate(value, params).getTime());
            }

            if (LOG.isDebugEnabled()) {
                LOG.debug("The type is assignable from " + java.util.Date.class + " [XPath=\"" + xpath + "\", StringValue=\"" + value + "\"].");
            }

            //noinspection unchecked
            return (T) getDate(value, params);
        }
        */
        else if (Locale.class.isAssignableFrom(type)) {

            if (LOG.isDebugEnabled()) {
                LOG.debug("The type is assignable from " + Locale.class + " [XPath=\"" + xpath + "\", StringValue=\"" + value + "\"].");
            }

            //noinspection unchecked
            return (T) new Locale(value);
        }

        throw new SchemaImportException("The class [" + type + "] isn't assignable. The assignable types are [" + String.class + ", " + Long.class + ", " + Integer.class + ", " + Double.class + ", " + Float.class + ", " + Byte.class + ", " + Short.class + ", " + BigDecimal.class + ", " + BigInteger.class + ", " + Boolean.class + ", " + Date.class + ", " + java.sql.Date.class + ", " + java.sql.Time.class + ", " + java.sql.Timestamp.class + "] [XPath=\"" + xpath + "\", StringValue=\"" + value + "\"]");
    }

    /**
     * Returns the content
     *
     * @param xpath
     * @return s
     */
    private static String getContent(Document document, String xpath) {

        if (xpath.contains("/@") && xpath.lastIndexOf('@') > xpath.lastIndexOf('/')) {

            if (LOG.isDebugEnabled()) {
                LOG.debug("The path is identified as attribute node path [XPath=\"" + xpath + "\"].");
            }

            return getAttributeContent(document, xpath);
        }
        else {

            if (LOG.isDebugEnabled()) {
                LOG.debug("The path is identified as text node path [XPath=\"" + xpath + "/text()].");
            }

            return getElementContent(document, xpath);
        }
    }

    /**
     * Returns the element content.
     *
     * @param xpath The relative xpath pointing to the element.
     * @return The element content.
     */
    private static String getElementContent(Document document, String xpath) {
        Object o = document.selectObject(xpath + "/text()");

        if (o instanceof CharacterData) {
            return ((CharacterData) o).getText();
        }
        else if (o instanceof List) {

            StringBuffer buffer = new StringBuffer();

            List list = (List) o;
            for (Object content : list) {

                if (content instanceof CharacterData) {
                    buffer.append(((CharacterData) content).getText());
                }
                else {
                    if (LOG.isErrorEnabled()) {
                        LOG.error("The result [" + o.getClass() + "=\"" + o + "\"] contains an illegal element [" + content.getClass() + "] [XPath=\"" + xpath + "\"].");
                    }
                }
            }

            if (list.size() > 0) {
                return buffer.toString();
            }

            // Return null for later use as default value check.
            return null;
        }

        if (LOG.isErrorEnabled()) {
            LOG.error("The result [" + o.getClass() + "=\"" + o + "\"] doesn't match to a " + Text.class + " or " + CDATA.class + " element [XPath=\"" + xpath + "\"].");
        }

        // Return null for later use as default value check.
        return null;
    }

    /**
     * Returns the attribute content.
     *
     * @param xpath The relative path to the attribute content.
     * @return The attribute content.
     */
    private static String getAttributeContent(Document document, String xpath) {
        Object o = document.selectObject(xpath);

        if (o instanceof Attribute) {
            return ((Attribute) o).getStringValue();
        }

        if (LOG.isErrorEnabled()) {
            LOG.error("The result [" + o.getClass() + "] doesn't match to a " + Attribute.class + " element [XPath=\"" + xpath + "\"].");
        }

        // Return null for later use as default value check.
        return null;
    }

    /**
     * Returns the parsed <code>String</code> value as a
     * <code>java.util.Date</code>.
     *
     * @param value  The date as <code>String</code> representation.
     * @param params The parameters used to parse the date.
     * @return The parsed date.
     */
    private static java.util.Date getDate(String value, Map params) {

        // Checks whether the params <code>Map</code> contains the key
        // for a specific pattern. If there isn't such a specific pattern
        // the default pattern will be used.
        String pattern = "dd.MM.yyyy";
        if (params != null && params.containsKey(PARAM_DATE_PATTERN)) {
            pattern = (String) params.get(PARAM_DATE_PATTERN);
        }

        DateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.parse(value);
        }
        catch (ParseException pe) {
            throw new SchemaImportException(pe.getMessage(), pe);
        }
    }
}
