package com.movies.ui.detail;

import androidx.databinding.ObservableField;

import com.movies.basemodule.BaseNavigator;
import com.movies.basemodule.BaseViewModel;
import com.movies.data.datamanager.DataManager;
import com.movies.data.model.movielist.Movie;
import com.movies.utils.rx.SchedulerProvider;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class MovieDetailViewModel extends BaseViewModel<MovieDetailViewModel.MovieDetailNavigator> {

    public ObservableField<Movie> movieItem = new ObservableField<>(new Movie());

    MovieDetailViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void getMovieDetail(long movieId) {
        if (!getNavigator().isNetworkAvailable()) {
            getMovieFromDB(movieId);
        } else {
            setLoading(true);
            getCompositeDisposable().add(getDataManager().getMovieDetail(String.valueOf(movieId))
                    .flatMap((Function<Movie, SingleSource<Integer>>) movie -> Single.fromObservable(getDataManager().update(movie)))
                    .subscribeOn(getSchedulerProvider().getIOThreadScheduler())
                    .observeOn(getSchedulerProvider().getMainThreadScheduler()).subscribe(id -> {
                        setLoading(false);
                        getMovieFromDB(movieId);
                    }, throwable -> setLoading(false)));
        }
    }

    private void getMovieFromDB(Long id) {
        setLoading(true);
        getCompositeDisposable().add(getDataManager().getMovie(id).subscribeOn(getSchedulerProvider().getIOThreadScheduler())
                .observeOn(getSchedulerProvider().getMainThreadScheduler()).subscribe(new Consumer<Movie>() {
                    @Override
                    public void accept(Movie movie) throws Exception {
                        setLoading(false);
                        loadMovieDetails(movie);
                    }
                }, throwable -> setLoading(false)));
    }

    private void loadMovieDetails(Movie movie) {
        movieItem.set(movie);
    }

    public interface MovieDetailNavigator extends BaseNavigator {

    }
}
