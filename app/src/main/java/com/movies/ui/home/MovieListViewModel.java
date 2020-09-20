package com.movies.ui.home;

import androidx.databinding.ObservableField;

import com.movies.basemodule.BaseNavigator;
import com.movies.basemodule.BaseViewModel;
import com.movies.data.datamanager.DataManager;
import com.movies.data.model.state.NetworkState;
import com.movies.ui.home.moviedata.MoviesRepository;
import com.movies.utils.rx.SchedulerProvider;

import java.util.Objects;

public class MovieListViewModel extends BaseViewModel<MovieListViewModel.MovieListNavigator> {
    private static final String TAG = "MovieListViewModel";

    public ObservableField<MoviesListAdapter> moviesListAdapter = new ObservableField<>(new MoviesListAdapter());

    MovieListViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    void initDataSourceFactory() {
        setLoading(true);
        MoviesRepository moviesRepository = new MoviesRepository(getDataManager());
        moviesRepository.getMovies().observe(getNavigator().getBaseActivity(), movies -> {
            if (moviesListAdapter.get() != null) {
                Objects.requireNonNull(moviesListAdapter.get()).submitList(movies);
            }
        });
        moviesRepository.getNetworkState().observe(getNavigator().getBaseActivity(), networkState -> {
            if (moviesListAdapter.get() != null) {
                Objects.requireNonNull(moviesListAdapter.get()).setNetworkState(networkState);
                if (networkState.getStatus() != NetworkState.Status.RUNNING) {
                    setLoading(false);
                }
            }
        });
    }

    public interface MovieListNavigator extends BaseNavigator {
    }
}
