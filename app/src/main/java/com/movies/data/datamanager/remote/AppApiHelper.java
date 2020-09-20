package com.movies.data.datamanager.remote;

import com.movies.BuildConfig;
import com.movies.data.model.movielist.Movie;
import com.movies.data.model.movielist.MoviesResponse;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class AppApiHelper implements ApiHelper {

    @Inject
    AppApiHelper() {
    }

    @Override
    public Single<MoviesResponse> getMovies(String page) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_MOVIE_LIST)
                .addQueryParameter(ApiEndPoint.PARAM_API_KEY, BuildConfig.API_KEY)
                .addQueryParameter(ApiEndPoint.PARAM_LANGUAGE, "en-US")
                .addQueryParameter(ApiEndPoint.PARAM_PAGE, page)
                .build()
                .getObjectSingle(MoviesResponse.class);
    }

    @Override
    public Single<Movie> getMovieDetail(String movieId) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_MOVIE_DETAIL)
                .addPathParameter(ApiEndPoint.PARAM_ID, movieId)
                .addQueryParameter(ApiEndPoint.PARAM_API_KEY, BuildConfig.API_KEY)
                .addQueryParameter(ApiEndPoint.PARAM_LANGUAGE, "en-US")
                .build()
                .getObjectSingle(Movie.class);
    }
}
