package com.movies.data.datamanager.remote;

import com.movies.data.model.movielist.Movie;
import com.movies.data.model.movielist.MoviesResponse;

import io.reactivex.Single;

public interface ApiHelper {
    Single<MoviesResponse> getMovies(String page);

    Single<Movie> getMovieDetail(String movieId);
}
