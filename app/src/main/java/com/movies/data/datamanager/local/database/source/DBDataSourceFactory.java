package com.movies.data.datamanager.local.database.source;

import androidx.paging.DataSource;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings("ALL")
public class DBDataSourceFactory<T> extends DataSource.Factory {
    private DBDataSource<T> dbDataSource;

    public DBDataSourceFactory(Object object) {
        dbDataSource = new DBDataSource<T>(object);
    }

    @NotNull
    @Override
    public DataSource create() {
        return dbDataSource;
    }
}
