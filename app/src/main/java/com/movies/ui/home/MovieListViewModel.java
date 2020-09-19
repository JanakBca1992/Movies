package com.movies.ui.home;


import com.movies.basemodule.BaseViewModel;
import com.movies.data.datamanager.DataManager;
import com.movies.utils.rx.SchedulerProvider;

public class MovieListViewModel extends BaseViewModel<MovieListViewModel.MovieListNavigator> {

    MovieListViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }


    public interface MovieListNavigator {
    }
}
