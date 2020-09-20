package com.movies.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.movies.BR;
import com.movies.R;
import com.movies.basemodule.BaseActivity;
import com.movies.databinding.ActivityMovieDetailBinding;
import com.movies.utils.AppConstants;

import java.util.Objects;

import javax.inject.Inject;

public class MovieDetailActivity extends BaseActivity<ActivityMovieDetailBinding, MovieDetailViewModel> implements MovieDetailViewModel.MovieDetailNavigator {

    @Inject
    MovieDetailViewModel mViewModel;

    public static Intent getIntent(Context context, Bundle bundle) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getResourceLayout() {
        return R.layout.activity_movie_detail;
    }

    @Override
    public void onInitialize() {
        mViewModel.setNavigator(this);
        setupToolbar();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            long movieId = bundle.getLong(AppConstants.BundleExtras.BUNDLE_EXTRAS_PARAM_MOVIE_ID, 0);
            mViewModel.getMovieDetail(movieId);
        }
    }

    private void setupToolbar() {
        setSupportActionBar(getViewBinding().toolBar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
    public MovieDetailViewModel getViewModel() {
        return mViewModel;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
