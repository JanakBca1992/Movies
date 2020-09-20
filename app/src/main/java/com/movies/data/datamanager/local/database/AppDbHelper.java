package com.movies.data.datamanager.local.database;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.movies.data.datamanager.local.database.source.DBDataSourceFactory;
import com.movies.data.model.movielist.Movie;
import com.movies.di.component.AppComponent;
import com.movies.utils.AppConstants;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.functions.Action;

@SuppressWarnings("ALL")
@Singleton
public class AppDbHelper implements DbHelper {

    private final AppDatabase mAppDatabase;

    @Inject
    AppDbHelper(AppDatabase appDatabase) {
        this.mAppDatabase = appDatabase;
    }

    @Override
    public Observable<Long> insert(Movie movie) {
        return Observable.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return mAppDatabase.moviesDao().insert(movie);
            }
        });
    }

    @Override
    public Observable<Movie> getMovie(long id) {
        return Observable.fromCallable(new Callable<Movie>() {
            @Override
            public Movie call() throws Exception {
                return mAppDatabase.moviesDao().getMovie(id);
            }
        });
    }

    @Override
    public Completable insert(List<Movie> movies) {
        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                mAppDatabase.moviesDao().insert(movies);
            }
        });
    }

    @Override
    public LiveData<PagedList<Movie>> getMovies() {
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder()).setEnablePlaceholders(false).setInitialLoadSizeHint(Integer.MAX_VALUE).setPageSize(Integer.MAX_VALUE).build();
        Executor executor = Executors.newFixedThreadPool(AppConstants.NUMBERS_OF_THREADS);
        DBDataSourceFactory dataSourceFactory = new DBDataSourceFactory<Movie>(mAppDatabase.moviesDao());
        LivePagedListBuilder livePagedListBuilder = new LivePagedListBuilder(dataSourceFactory, pagedListConfig);
        return livePagedListBuilder.setFetchExecutor(executor).build();
    }
}
