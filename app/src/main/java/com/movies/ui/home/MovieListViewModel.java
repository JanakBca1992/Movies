package com.movies.ui.home;

import android.view.View;

import androidx.databinding.ObservableField;

import com.movies.basemodule.BaseNavigator;
import com.movies.basemodule.BaseViewModel;
import com.movies.data.datamanager.DataManager;
import com.movies.data.model.movielist.Movie;
import com.movies.data.model.state.NetworkState;
import com.movies.listeners.ItemClickListener;
import com.movies.ui.home.moviedata.MoviesRepository;
import com.movies.utils.rx.SchedulerProvider;

import java.util.Objects;

public class MovieListViewModel extends BaseViewModel<MovieListViewModel.MovieListNavigator> implements ItemClickListener {
    public ObservableField<MoviesListAdapter> moviesListAdapter = new ObservableField<>(new MoviesListAdapter(this));

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

    @Override
    public void onItemClicked(View view, int position) {
        Movie movie = Objects.requireNonNull(moviesListAdapter.get()).getItemAt(position);
        getNavigator().openMovieDetailScreen(movie.getId());
    }

    public interface MovieListNavigator extends BaseNavigator {
        void onRefresh(boolean isRefreshing);

        void openMovieDetailScreen(long id);
    }
}
