package com.movies.data.datamanager.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.movies.data.datamanager.database.dao.MoviesDao;
import com.movies.data.model.Movie;

@Database(entities = {Movie.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MoviesDao moviesDao();
}
