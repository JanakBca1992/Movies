package com.movies.basemodule;


import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.ViewModel;

import com.movies.data.datamanager.DataManager;
import com.movies.utils.rx.SchedulerProvider;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;

public class BaseViewModel<N> extends ViewModel {

    private WeakReference<N> mNavigator;
    private final DataManager mDataManager;
    private CompositeDisposable mCompositeDisposable;
    private final SchedulerProvider mSchedulerProvider;
    private final ObservableBoolean mIsLoading = new ObservableBoolean(false);

    public BaseViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        this.mDataManager = dataManager;
        this.mSchedulerProvider = schedulerProvider;
        this.mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        mCompositeDisposable.dispose();
        super.onCleared();
    }

    protected CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    protected DataManager getDataManager() {
        return mDataManager;
    }

    public ObservableBoolean isLoading() {
        return mIsLoading;
    }

    protected void setLoading(boolean isLoading) {
        mIsLoading.set(isLoading);
    }

    protected N getNavigator() {
        return mNavigator.get();
    }

    public void setNavigator(N navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }

    protected SchedulerProvider getSchedulerProvider() {
        return mSchedulerProvider;
    }
}
