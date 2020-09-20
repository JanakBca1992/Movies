package com.movies.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.movies.BR;
import com.movies.R;
import com.movies.basemodule.BaseActivity;
import com.movies.databinding.ActivityMovieListBinding;
import com.movies.ui.detail.MovieDetailActivity;
import com.movies.utils.AppConstants;

import java.util.Objects;

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
        mViewModel.initDataSourceFactory();
        setupToolbar();
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

    private void setupToolbar() {
        setSupportActionBar(getViewBinding().toolBar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle(R.string.title_list);
    }

    @Override
    public void openMovieDetailScreen(long id) {
        Bundle bundle = new Bundle();
        bundle.putLong(AppConstants.BundleExtras.BUNDLE_EXTRAS_PARAM_MOVIE_ID, id);
        startActivity(MovieDetailActivity.getIntent(this, bundle));
    }
}
