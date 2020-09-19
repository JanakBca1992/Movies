package com.movies.data.datamanager.local.database.source;

import androidx.paging.PageKeyedDataSource;

import com.movies.data.datamanager.local.database.dao.MoviesDao;
import com.movies.data.model.movielist.Movie;

import org.jetbrains.annotations.NotNull;

import java.util.List;

@SuppressWarnings("ALL")
public class DBDataSource<T> extends PageKeyedDataSource<Long, T> {
    private Object objectDao;

    public DBDataSource(Object objectDao) {
        this.objectDao = objectDao;
    }

    @Override
    public void loadInitial(@NotNull LoadInitialParams<Long> loadInitialParams, @NotNull LoadInitialCallback<Long, T> loadInitialCallback) {
        if (objectDao instanceof MoviesDao) {
            List<Movie> movies = ((MoviesDao) objectDao).getMovies();
            if (movies.size() != 0) {
                loadInitialCallback.onResult((List<? extends T>) movies, 0l, 1l);
            }
        }
    }

    @Override
    public void loadAfter(@NotNull LoadParams<Long> loadParams, @NotNull LoadCallback<Long, T> loadCallback) {

    }

    @Override
    public void loadBefore(@NotNull LoadParams<Long> loadParams, @NotNull LoadCallback<Long, T> loadCallback) {
    }
}
