package com.movies.data.datamanager.local.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.movies.data.model.movielist.Movie;

import java.util.List;

@Dao
public interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Movie movie);

    @Update
    int update(Movie movie);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Movie> movies);

    @Query("SELECT * FROM Movie WHERE id=:id")
    Movie getMovie(long id);

    @Query("SELECT * FROM Movie ORDER BY voteAverage DESC")
    List<Movie> getMovies();

    @Query("DELETE FROM Movie")
    abstract void deleteAllMovies();
}
