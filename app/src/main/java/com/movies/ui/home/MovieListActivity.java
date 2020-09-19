package com.movies.ui.home;

import android.content.Context;
import android.content.Intent;

import com.movies.BR;
import com.movies.R;
import com.movies.basemodule.BaseActivity;
import com.movies.databinding.ActivityMovieListBinding;

import javax.inject.Inject;

public class MovieListActivity extends BaseActivity<ActivityMovieListBinding, MovieListViewModel> implements MovieListViewModel.MovieListNavigator {

    @Inject
    MovieListViewModel mViewModel;

    public static Intent getIntent(Context context) {
        return new Intent(context, MovieListActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getResourceLayout() {
        return R.layout.activity_movie_list;
    }

    @Override
    public void onInitialize() {
        mViewModel.setNavigator(this);
    }

    @Override
    public void onTerminate() {

    }

    @Override
    public void onShow() {

    }

    @Override
    public void onHide() {

    }

    @Override
    public MovieListViewModel getViewModel() {
        return mViewModel;
    }
}