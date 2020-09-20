package com.movies.ui.home;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.movies.basemodule.BaseNavigator;
import com.movies.basemodule.BaseViewModel;
import com.movies.data.datamanager.DataManager;
import com.movies.data.model.state.NetworkState;
import com.movies.ui.home.moviedata.MoviesRepository;
import com.movies.utils.rx.SchedulerProvider;

import java.util.Objects;

public class MovieListViewModel extends BaseViewModel<MovieListViewModel.MovieListNavigator> {
    public ObservableField<MoviesListAdapter> moviesListAdapter = new ObservableField<>(new MoviesListAdapter());

    MoviesRepository moviesRepository;

    MovieListViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    void initDataSourceFactory() {
        setLoading(true);
        moviesRepository = new MoviesRepository(getDataManager());
        moviesRepository.getMovies().observe(getNavigator().getBaseActivity(), movies -> {
            Objects.requireNonNull(moviesListAdapter.get()).submitList(movies);
        });
        moviesRepository.getNetworkState().observe(getNavigator().getBaseActivity(), networkState -> {
            Objects.requireNonNull(moviesListAdapter.get()).setNetworkState(networkState);
            if (networkState.getStatus() != NetworkState.Status.RUNNING) {
                setLoading(false);
                getNavigator().onRefresh(false);
            }
        });
    }

    public void onRefresh() {
        if (moviesRepository != null) {
            Objects.requireNonNull(moviesListAdapter.get()).submitList(null);
            moviesRepository.onReferesh();
        }
    }


    public interface MovieListNavigator extends BaseNavigator {
        void onRefresh(boolean isRefreshing);
    }
}
