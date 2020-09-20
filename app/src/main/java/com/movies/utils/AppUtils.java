package com.movies.utils;

import com.movies.data.model.movielist.CommonDetail;

import java.util.List;
import java.util.Locale;

public class AppUtils {
    public static String getLanguage(String languageCode) {
        Locale locale = new Locale(languageCode);
        return locale.getDisplayLanguage(locale);
    }

    public static String getGenres(List<CommonDetail> genreList) {
        StringBuilder genres = new StringBuilder();

        if (genreList != null) {
            for (CommonDetail commonDetail : genreList) {
                if (genres.length() > 0) {
                    genres.append(", ");
                }
                genres.append(commonDetail.getName());
            }
        }

        return genres.toString();
    }

    public static String getLanguages(List<CommonDetail> languageList) {
        StringBuilder languages = new StringBuilder();

        if (languageList != null) {
            for (CommonDetail commonDetail : languageList) {
                if (languages.length() > 0) {
                    languages.append(", ");
                }
                languages.append(commonDetail.getName());
            }
        }

        return languages.toString();
    }
}
