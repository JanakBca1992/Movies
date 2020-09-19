package com.movies.data.datamanager.database;

import com.movies.data.model.Movie;

import java.util.List;

import io.reactivex.Observable;

public interface DbHelper {
    Observable<Boolean> insert(List<Movie> movies);
}
