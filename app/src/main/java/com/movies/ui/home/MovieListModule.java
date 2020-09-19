package com.movies.ui.home;

import com.movies.data.datamanager.DataManager;
import com.movies.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class MovieListModule {
    @Provides
    MovieListViewModel provideMovieListModule(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new MovieListViewModel(dataManager, schedulerProvider);
    }
}
