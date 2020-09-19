package com.movies.data.datamanager.remote;

import com.movies.BuildConfig;

public final class ApiEndPoint {
    public static final String MOVIES_API_URL = BuildConfig.API_URL + "/3/movie/";

    public static final String PARAM_API_KEY = "api_key";
    public static final String PARAM_LANGUAGE = "language";
    public static final String PARAM_PAGE = "page";
    public static final String PARAM_ID = "id";

    public static final String ENDPOINT_MOVIE_LIST = MOVIES_API_URL + "top_rated?";
    public static final String ENDPOINT_MOVIE_DETAIL = MOVIES_API_URL + "{" + PARAM_ID + "}";
}
