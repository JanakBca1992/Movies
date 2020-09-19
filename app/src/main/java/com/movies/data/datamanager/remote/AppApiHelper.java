package com.movies.data.datamanager.remote;

import com.movies.data.model.MoviesResponse;
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
    public Single<MoviesResponse> getMovies() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_BLOG)
                .build()
                .getObjectSingle(MoviesResponse.class);
    }
}
