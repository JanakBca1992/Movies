package com.movies.ui.splash;

import com.movies.BR;
import com.movies.R;
import com.movies.basemodule.BaseActivity;
import com.movies.databinding.ActivitySplashBinding;
import com.movies.ui.home.MovieListActivity;

import javax.inject.Inject;

public class SplashActivity extends BaseActivity<ActivitySplashBinding, SplashViewModel> implements SplashViewModel.SplashNavigator {

    @Inject
    SplashViewModel mViewModel;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getResourceLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void onInitialize() {
        mViewModel.setNavigator(this);
        mViewModel.startSplashTimer();
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
    public SplashViewModel getViewModel() {
        return mViewModel;
    }

    @Override
    public void openMainActivity() {
        startActivity(MovieListActivity.getIntent(this));
        finish();
    }
}
