package com.movies.ui.splash;


import com.movies.basemodule.BaseViewModel;
import com.movies.data.datamanager.DataManager;
import com.movies.utils.rx.SchedulerProvider;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;

public class SplashViewModel extends BaseViewModel<SplashViewModel.SplashNavigator> {

    SplashViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    void startSplashTimer() {
        getCompositeDisposable().add(Completable.timer(3, TimeUnit.SECONDS, getSchedulerProvider().getMainThreadScheduler()).subscribe(() ->
                getNavigator().openMainActivity()));
    }

    public interface SplashNavigator {
        void openMainActivity();
    }
}
