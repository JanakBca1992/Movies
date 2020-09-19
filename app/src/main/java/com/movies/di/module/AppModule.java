package com.movies.di.module;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.movies.data.datamanager.AppDataManager;
import com.movies.data.datamanager.DataManager;
import com.movies.data.datamanager.database.AppDatabase;
import com.movies.data.datamanager.database.AppDbHelper;
import com.movies.data.datamanager.database.DbHelper;
import com.movies.data.datamanager.remote.ApiHelper;
import com.movies.data.datamanager.remote.AppApiHelper;
import com.movies.di.ApiInfo;
import com.movies.di.DatabaseInfo;
import com.movies.utils.AppConstants;
import com.movies.utils.rx.AppSchedulerProvider;
import com.movies.utils.rx.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    @Singleton
    ApiHelper provideApiHelper(AppApiHelper appApiHelper) {
        return appApiHelper;
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(@DatabaseInfo String dbName, Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, dbName).fallbackToDestructiveMigration().build();
    }

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return AppConstants.DB_NAME;
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }
}
