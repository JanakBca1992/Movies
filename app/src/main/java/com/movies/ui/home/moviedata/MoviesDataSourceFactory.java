package com.movies.ui.home.moviedata;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.movies.data.datamanager.DataManager;
import com.movies.data.model.movielist.Movie;
import com.movies.data.model.movielist.MoviesResponse;
import com.movies.data.model.state.NetworkState;
import com.movies.utils.AppConstants;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.ReplaySubject;

import static com.movies.utils.AppConstants.LOADING_PAGE_SIZE;
import static com.movies.utils.AppConstants.NUMBERS_OF_THREADS;

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

    public void onReferesh() {
        moviesPageKeyedDataSource.invalidate();
    }

    public void onRetry() {
        moviesPageKeyedDataSource.invalidate();
    }


    public static class MoviesDataSource extends PageKeyedDataSource<Long, Movie> {
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
            dataManager.getMovies(String.valueOf(1))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<MoviesResponse>() {
                        @Override
                        public void accept(MoviesResponse moviesResponse) throws Exception {
                            if (moviesResponse.getResults() != null && !moviesResponse.getResults().isEmpty()) {
                                moviesResponse.getResults().addAll(dataManager.getMovies().getValue());
                                loadInitialCallback.onResult(moviesResponse.getResults(), moviesResponse.getPage(), moviesResponse.getPage() + 1);
                                networkState.postValue(NetworkState.LOADED);
                                moviesResponse.getResults().forEach(moviesObservable::onNext);
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            String errorMessage = "Unknown Error!";
                            if (throwable != null) {
                                errorMessage = getAPIError((ANError) throwable);
                            }
                            networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                            loadInitialCallback.onResult(new ArrayList<>(), 1L, 2L);
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

            dataManager.getMovies(String.valueOf(loadParams.key))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<MoviesResponse>() {
                        @Override
                        public void accept(MoviesResponse moviesResponse) throws Exception {
                            if (moviesResponse.getResults() != null && !moviesResponse.getResults().isEmpty()) {
                                loadCallback.onResult(moviesResponse.getResults(), loadParams.key + 1);
                                networkState.postValue(NetworkState.LOADED);
                                moviesResponse.getResults().forEach(moviesObservable::onNext);
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            String errorMessage = "Unknown Error!";
                            if (throwable != null) {
                                errorMessage = getAPIError((ANError) throwable);
                            }
                            networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                            loadCallback.onResult(new ArrayList<>(), loadParams.key);
                        }
                    });
        }

        private String getAPIError(ANError anError) {
            if (anError.getErrorBody() == null) {
                return "Server not responding please try later!";
            } else if (anError.getErrorCode() == AppConstants.API_STATUS_CODE_LOCAL_ERROR && anError.getErrorDetail() == ANConstants.CONNECTION_ERROR) {
                return "Check your internet connection and try again!";
            } else {
                return "Unknown error";
            }
        }
    }

    public static class MoviesNetwork {
        final private LiveData<PagedList<Movie>> moviesPaged;
        final private LiveData<NetworkState> networkState;
        private MoviesDataSourceFactory dataSourceFactory;
        private Executor executor;

        public MoviesNetwork(MoviesDataSourceFactory dataSourceFactory, PagedList.BoundaryCallback<Movie> boundaryCallback) {
            this.dataSourceFactory = dataSourceFactory;
            PagedList.Config pagedListConfig = (new PagedList.Config.Builder()).setEnablePlaceholders(false).setInitialLoadSizeHint(LOADING_PAGE_SIZE).setPageSize(LOADING_PAGE_SIZE).build();

            networkState = Transformations.switchMap(dataSourceFactory.getNetworkStatus(), (Function<MoviesDataSource, LiveData<NetworkState>>) MoviesDataSource::getNetworkState);

            executor = Executors.newFixedThreadPool(NUMBERS_OF_THREADS);
            LivePagedListBuilder livePagedListBuilder = new LivePagedListBuilder(dataSourceFactory, pagedListConfig);
            moviesPaged = livePagedListBuilder.setFetchExecutor(executor).setBoundaryCallback(boundaryCallback).build();
        }

        public LiveData<PagedList<Movie>> getPagedMovies() {
            return moviesPaged;
        }

        public LiveData<NetworkState> getNetworkState() {
            return networkState;
        }

        public void onReferesh() {
            dataSourceFactory.onReferesh();
        }

        public void onRetry() {

        }
    }
}
