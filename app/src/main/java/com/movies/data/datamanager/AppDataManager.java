package com.movies.data.datamanager;

import android.content.Context;

import com.google.gson.Gson;
import com.movies.data.datamanager.database.DbHelper;
import com.movies.data.datamanager.remote.ApiHelper;
import com.movies.data.model.Movie;
import com.movies.data.model.MoviesResponse;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;

@Singleton
public class AppDataManager implements DataManager {

    private final ApiHelper mApiHelper;
    private final DbHelper mDbHelper;

    @Inject
    AppDataManager(DbHelper dbHelper, ApiHelper apiHelper) {
        mDbHelper = dbHelper;
        mApiHelper = apiHelper;
    }

    @Override
    public Observable<Boolean> insert(List<Movie> movies) {
        return mDbHelper.insert(movies);
    }

    @Override
    public Single<MoviesResponse> getMovies() {
        return mApiHelper.getMovies();
    }
}
