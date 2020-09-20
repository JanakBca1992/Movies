package com.movies.utils;

import java.util.Locale;

public class AppUtils {
    public static String getLanguage(String languageCode) {
        Locale locale = new Locale(languageCode);
        return locale.getDisplayLanguage(locale);
    }
}
