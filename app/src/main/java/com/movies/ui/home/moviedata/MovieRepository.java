package com.movies.ui.home.moviedata;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.paging.PagedList;

import com.movies.data.datamanager.DataManager;
import com.movies.data.model.movielist.Movie;
import com.movies.data.model.state.NetworkState;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

@SuppressWarnings("ALL")
public class MovieRepository {
    private static final String TAG = "MovieRepository";

    private DataManager dataManager;

    final private MoviesNetwork network;
    final private MediatorLiveData liveDataMerger;

    public MovieRepository(DataManager dataManager) {
        this.dataManager = dataManager;

        MoviesDataSourceFactory dataSourceFactory = new MoviesDataSourceFactory(dataManager);
        network = new MoviesNetwork(dataSourceFactory, boundaryCallback);
        liveDataMerger = new MediatorLiveData<>();
        liveDataMerger.addSource(network.getPagedMovies(), value -> {
            liveDataMerger.setValue(value);
        });
        dataSourceFactory.getMovies().observeOn(Schedulers.io()).flatMap(new Function<Movie, ObservableSource<Long>>() {
            @Override
            public ObservableSource<Long> apply(Movie movie) throws Exception {
                return dataManager.insert(movie);
            }
        }).observeOn(Schedulers.io()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                Log.d(TAG, "accept: ");
            }
        });
    }

    private PagedList.BoundaryCallback<Movie> boundaryCallback = new PagedList.BoundaryCallback<Movie>() {
        @Override
        public void onZeroItemsLoaded() {
            super.onZeroItemsLoaded();
            liveDataMerger.addSource(dataManager.getMovies(), value -> {
                liveDataMerger.setValue(value);
                liveDataMerger.removeSource(dataManager.getMovies());
            });
        }
    };

    public LiveData<PagedList<Movie>> getMovies() {
        return liveDataMerger;
    }

    public LiveData<NetworkState> getNetworkState() {
        return network.getNetworkState();
    }
}
