package com.movies.data.datamanager;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.movies.data.datamanager.local.database.DbHelper;
import com.movies.data.datamanager.local.preference.PreferencesHelper;
import com.movies.data.datamanager.remote.ApiHelper;
import com.movies.data.model.movielist.Movie;
import com.movies.data.model.movielist.MoviesResponse;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

@SuppressWarnings("ALL")
@Singleton
public class AppDataManager implements DataManager {

    private final ApiHelper mApiHelper;
    private final DbHelper mDbHelper;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    AppDataManager(DbHelper dbHelper, ApiHelper apiHelper, PreferencesHelper preferencesHelper) {
        mDbHelper = dbHelper;
        mApiHelper = apiHelper;
        mPreferencesHelper = preferencesHelper;
    }

    @Override
    public Long getCurrentPage() {
        return mPreferencesHelper.getCurrentPage();
    }

    @Override
    public void setCurrentPage(Long currentPage) {
        mPreferencesHelper.setCurrentPage(currentPage);
    }

    @Override
    public Long getTotalPage() {
        return mPreferencesHelper.getTotalPage();
    }

    @Override
    public void setTotalPage(Long totalPage) {
        mPreferencesHelper.setTotalPage(totalPage);
    }

    @Override
    public Observable<Long> insert(Movie movie) {
        return mDbHelper.insert(movie);
    }

    @Override
    public Observable<Integer> update(Movie movie) {
        return mDbHelper.update(movie);
    }

    @Override
    public Completable insert(List<Movie> movies) {
        return mDbHelper.insert(movies);
    }

    @Override
    public Observable<Movie> getMovie(long id) {
        return mDbHelper.getMovie(id);
    }

    @Override
    public LiveData<PagedList<Movie>> getMovies() {
        return mDbHelper.getMovies();
    }

    @Override
    public Single<MoviesResponse> getMovies(String page) {
        return mApiHelper.getMovies(page);
    }

    @Override
    public Single<Movie> getMovieDetail(String movieId) {
        return mApiHelper.getMovieDetail(movieId);
    }
}
