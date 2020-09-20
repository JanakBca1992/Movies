package com.movies.data.datamanager.local.database;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.movies.data.model.movielist.Movie;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

@SuppressWarnings("ALL")
public interface DbHelper {
    Observable<Long> insert(Movie movie);

    Completable insert(List<Movie> movies);

    Observable<Movie> getMovie(long id);

    LiveData<PagedList<Movie>> getMovies();
}
