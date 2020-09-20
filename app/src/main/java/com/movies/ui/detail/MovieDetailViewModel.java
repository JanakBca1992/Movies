package com.movies.ui.detail;


import com.movies.basemodule.BaseViewModel;
import com.movies.data.datamanager.DataManager;
import com.movies.utils.rx.SchedulerProvider;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;

public class MovieDetailViewModel extends BaseViewModel<MovieDetailViewModel.MovieDetailNavigator> {

    MovieDetailViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public interface MovieDetailNavigator {
    }
}
