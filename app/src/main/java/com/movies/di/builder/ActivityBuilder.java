package com.movies.di.builder;

import com.movies.ui.home.MovieListActivity;
import com.movies.ui.home.MovieListModule;
import com.movies.ui.splash.SplashActivity;
import com.movies.ui.splash.SplashModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = SplashModule.class)
    abstract SplashActivity bindSplashActivity();

    @ContributesAndroidInjector(modules = MovieListModule.class)
    abstract MovieListActivity bindMovieListActivity();
}
