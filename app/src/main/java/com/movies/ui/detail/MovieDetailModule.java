package com.movies.ui.detail;

import com.movies.data.datamanager.DataManager;
import com.movies.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class MovieDetailModule {
    @Provides
    MovieDetailViewModel provideMovieDetailViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new MovieDetailViewModel(dataManager, schedulerProvider);
    }
}
