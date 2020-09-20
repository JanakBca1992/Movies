package com.movies.utils;

public final class AppConstants {
    public static final String DB_NAME = "movies.db";
    public static final String PREF_NAME = "movies.pref";

    public static final int LOADING_PAGE_SIZE = 20;
    public static final int NUMBERS_OF_THREADS = 3;

    private static final String IMAGE_URL_PREFIX = "https://image.tmdb.org/t/p/";
    private static final String SMALL_IMAGE_URL_PREFIX = IMAGE_URL_PREFIX + "w300";
    private static final String BIG_IMAGE_URL_PREFIX = IMAGE_URL_PREFIX + "w500";

    public static final String LOAD_SMALL_IMAGE = SMALL_IMAGE_URL_PREFIX + "%s";
    public static final String LOAD_LARGE_IMAGE = BIG_IMAGE_URL_PREFIX + "%s";

    public interface BundleExtras {
        String BUNDLE_EXTRAS_PARAM_MOVIE_ID = "bundle_extras_param_movie_id";
    }
}
