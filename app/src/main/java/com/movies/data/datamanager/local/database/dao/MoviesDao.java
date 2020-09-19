package com.movies.data.datamanager.local.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.movies.data.model.movielist.Movie;

import java.util.List;

@Dao
public interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Movie movie);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Movie> movies);

    @Query("SELECT * FROM Movie")
    List<Movie> getMovies();

    @Query("DELETE FROM Movie")
    abstract void deleteAllMovies();
}
