package com.movies.data.datamanager.database;

import com.movies.data.model.Movie;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class AppDbHelper implements DbHelper {

    private final AppDatabase mAppDatabase;

    @Inject
    AppDbHelper(AppDatabase appDatabase) {
        this.mAppDatabase = appDatabase;
    }

    @Override
    public Observable<Boolean> insert(List<Movie> movies) {
        return Observable.fromCallable(() -> {
            mAppDatabase.moviesDao().insert(movies);
            return true;
        });
    }
}
