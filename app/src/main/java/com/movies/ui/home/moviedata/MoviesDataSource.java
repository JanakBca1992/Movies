package com.movies.ui.home.moviedata;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.movies.data.datamanager.DataManager;
import com.movies.data.model.movielist.Movie;
import com.movies.data.model.movielist.MoviesResponse;
import com.movies.data.model.state.NetworkState;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.ReplaySubject;

@SuppressWarnings("ALL")
public class MoviesDataSource extends PageKeyedDataSource<Long, Movie> {
    private static final String TAG = "MoviesDataSource";

    private DataManager dataManager;

    private final MutableLiveData networkState;
    private final ReplaySubject<Movie> moviesObservable;

    MoviesDataSource(DataManager dataManager) {
        this.dataManager = dataManager;
        networkState = new MutableLiveData();
        moviesObservable = ReplaySubject.create();
    }

    public MutableLiveData getNetworkState() {
        return networkState;
    }

    public ReplaySubject<Movie> getMovies() {
        return moviesObservable;
    }

    //This will be called once to load the initial data
    @Override
    public void loadInitial(@NotNull LoadInitialParams<Long> loadInitialParams, @NotNull LoadInitialCallback<Long, Movie> loadInitialCallback) {
        networkState.postValue(NetworkState.LOADING);
        dataManager.getMovies(Math.toIntExact(dataManager.getCurrentPage() + 1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MoviesResponse>() {
                    @Override
                    public void accept(MoviesResponse moviesResponse) throws Exception {
                        if (moviesResponse.getResults() != null && !moviesResponse.getResults().isEmpty()) {
                            dataManager.setCurrentPage(moviesResponse.getPage());
                            loadInitialCallback.onResult(moviesResponse.getResults(), moviesResponse.getPage(), moviesResponse.getPage() + 1);
                            networkState.postValue(NetworkState.LOADED);
                            moviesResponse.getResults().forEach(moviesObservable::onNext);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        String errorMessage = throwable.getMessage() == null ? "Unknown Error" : throwable.getMessage();
                        networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                        loadInitialCallback.onResult(new ArrayList<>(), dataManager.getCurrentPage(), dataManager.getCurrentPage() + 1);
                    }
                });
    }

    //This will load the previous page
    @Override
    public void loadBefore(@NotNull LoadParams<Long> loadParams, @NotNull LoadCallback<Long, Movie> loadCallback) {
    }

    //This will load the next page
    @Override
    public void loadAfter(@NotNull LoadParams<Long> loadParams, @NotNull LoadCallback<Long, Movie> loadCallback) {
        networkState.postValue(NetworkState.LOADING);
        dataManager.getMovies(Math.toIntExact(loadParams.key))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MoviesResponse>() {
                    @Override
                    public void accept(MoviesResponse moviesResponse) throws Exception {
                        if (moviesResponse.getResults() != null && !moviesResponse.getResults().isEmpty()) {
                            dataManager.setCurrentPage(moviesResponse.getPage());
                            loadCallback.onResult(moviesResponse.getResults(), dataManager.getCurrentPage() + 1);
                            networkState.postValue(NetworkState.LOADED);
                            moviesResponse.getResults().forEach(moviesObservable::onNext);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        String errorMessage = throwable.getMessage() == null ? "Unknown Error" : throwable.getMessage();
                        networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                        loadCallback.onResult(new ArrayList<>(), dataManager.getCurrentPage() + 1);
                    }
                });
    }
}
