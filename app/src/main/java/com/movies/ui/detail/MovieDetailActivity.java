package com.movies.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.movies.BR;
import com.movies.R;
import com.movies.basemodule.BaseActivity;
import com.movies.databinding.ActivityMovieDetailBinding;
import com.movies.utils.AppConstants;

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
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            long movieId = bundle.getLong(AppConstants.BundleExtras.BUNDLE_EXTRAS_PARAM_MOVIE_ID, 0);
            mViewModel.getMovieDetail(movieId);
        }
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
}
