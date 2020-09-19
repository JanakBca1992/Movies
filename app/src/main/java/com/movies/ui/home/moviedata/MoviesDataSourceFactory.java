package com.movies.ui.home.moviedata;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.movies.data.datamanager.DataManager;
import com.movies.data.model.movielist.Movie;

import org.jetbrains.annotations.NotNull;

import io.reactivex.subjects.ReplaySubject;

@SuppressWarnings("ALL")
public class MoviesDataSourceFactory extends DataSource.Factory {
    private DataManager dataManager;

    private MutableLiveData<MoviesDataSource> networkStatus;
    private MoviesDataSource moviesPageKeyedDataSource;

    public MoviesDataSourceFactory(DataManager dataManager) {
        this.dataManager = dataManager;
        networkStatus = new MutableLiveData<>();
        moviesPageKeyedDataSource = new MoviesDataSource(dataManager);
    }

    @NotNull
    @Override
    public DataSource create() {
        networkStatus.postValue(moviesPageKeyedDataSource);
        return moviesPageKeyedDataSource;
    }

    public MutableLiveData<MoviesDataSource> getNetworkStatus() {
        return networkStatus;
    }

    public ReplaySubject<Movie> getMovies() {
        return moviesPageKeyedDataSource.getMovies();
    }
}
