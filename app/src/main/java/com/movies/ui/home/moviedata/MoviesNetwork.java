package com.movies.ui.home.moviedata;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.movies.data.datamanager.DataManager;
import com.movies.data.model.movielist.Movie;
import com.movies.data.model.state.NetworkState;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.movies.utils.AppConstants.LOADING_PAGE_SIZE;
import static com.movies.utils.AppConstants.NUMBERS_OF_THREADS;

@SuppressWarnings("ALL")
public class MoviesNetwork {
    final private LiveData<PagedList<Movie>> moviesPaged;
    final private LiveData<NetworkState> networkState;

    public MoviesNetwork(MoviesDataSourceFactory dataSourceFactory, PagedList.BoundaryCallback<Movie> boundaryCallback) {
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder()).setEnablePlaceholders(false).setInitialLoadSizeHint(LOADING_PAGE_SIZE).setPageSize(LOADING_PAGE_SIZE).build();

        networkState = Transformations.switchMap(dataSourceFactory.getNetworkStatus(), (Function<MoviesDataSource, LiveData<NetworkState>>) MoviesDataSource::getNetworkState);

        Executor executor = Executors.newFixedThreadPool(NUMBERS_OF_THREADS);
        LivePagedListBuilder livePagedListBuilder = new LivePagedListBuilder(dataSourceFactory, pagedListConfig);
        moviesPaged = livePagedListBuilder.setFetchExecutor(executor).setBoundaryCallback(boundaryCallback).build();
    }

    public LiveData<PagedList<Movie>> getPagedMovies() {
        return moviesPaged;
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }
}
