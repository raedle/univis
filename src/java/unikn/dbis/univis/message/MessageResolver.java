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
package unikn.dbis.univis.message;

import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

/**
 * The <code>MessageResolver</code> is able to get messages
 * of the defined bundle property files. The resolver uses
 * the current <code>Locale</code> to detect the correct
 * property files. Call the {@link MessageResolver#getMessage(String)}
 * with the key of the message and you will get the message
 * that corresponds to the current <code>Locale</code>. If
 * there isn't such a key in that property file the resolver
 * fallback to a lower file in the hierarchy. If there isn't
 * such a key in the whole hierarchy the resolver returns the
 * key surrounded by three question marks.
 */
public class MessageResolver {

    // The message properties.
    private static ResourceBundleMessageSource bundle =
            new ResourceBundleMessageSource();

    static {
        bundle.setBasenames(new String[]{
                "unikn.dbis.univis.message.univis",
                "unikn.dbis.univis.message.data-reference"
        });
    }

    private static Locale locale = Locale.getDefault();

    public static void setLocale(Locale locale) {
        MessageResolver.locale = locale;
    }

    /**
     * Returns the message that will be found at parameter
     * key or the key if there isn't a matching key in one
     * of the property files.
     *
     * @param key The key that indicates the message.
     * @return The message or the key if there isn't a matching
     *         key in one of the property files.
     */
    public static String getMessage(String key) {
        return bundle.getMessage(key, null, "??? " + key + " ???", locale);
    }
}