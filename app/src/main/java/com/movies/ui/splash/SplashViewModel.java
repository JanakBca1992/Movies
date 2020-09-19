package com.movies.ui.splash;


import com.movies.basemodule.BaseViewModel;
import com.movies.data.datamanager.DataManager;
import com.movies.utils.rx.SchedulerProvider;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.functions.Action;

public class SplashViewModel extends BaseViewModel<SplashViewModel.SplashNavigator> {

    SplashViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    void startSplashTimer() {
        getCompositeDisposable().add(Completable.timer(3, TimeUnit.SECONDS, getSchedulerProvider().ui()).subscribe(new Action() {
            @Override
            public void run() {
                getNavigator().openMainActivity();
            }
        }));
    }

    public interface SplashNavigator {
        void openMainActivity();
    }
}
