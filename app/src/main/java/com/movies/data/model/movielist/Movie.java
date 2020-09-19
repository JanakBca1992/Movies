package com.movies.data.model.movielist;

import androidx.databinding.BaseObservable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Objects;

@Entity(tableName = "Movie")
public class Movie extends BaseObservable {
    @SerializedName("id")
    @PrimaryKey
    private long id;

    @SerializedName("title")
    private String title;

    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("popularity")
    private double popularity;

    @SerializedName("vote_count")
    private long voteCount;

    @SerializedName("vote_average")
    private double voteAverage;

    @SerializedName("video")
    private boolean isVideo;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("adult")
    private boolean isAdult;

    @SerializedName("backdrop_path")
    private String backgroundPath;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("genre_ids")
    private ArrayList<String> genreIds;

    @SerializedName("overview")
    private String overview;

    @SerializedName("release_date")
    private String releaseDate;

    public Movie() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public long getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(long voteCount) {
        this.voteCount = voteCount;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public boolean isVideo() {
        return isVideo;
    }

    public void setVideo(boolean video) {
        isVideo = video;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public void setAdult(boolean adult) {
        isAdult = adult;
    }

    public String getBackgroundPath() {
        return backgroundPath;
    }

    public void setBackgroundPath(String backgroundPath) {
        this.backgroundPath = backgroundPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public ArrayList<String> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(ArrayList<String> genreIds) {
        this.genreIds = genreIds;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Movie)) {
            return false;
        }
        Movie movie = (Movie) o;
        return getId() == movie.getId() &&
                Double.compare(movie.getPopularity(), getPopularity()) == 0 &&
                getVoteCount() == movie.getVoteCount() &&
                Double.compare(movie.getVoteAverage(), getVoteAverage()) == 0 &&
                isVideo() == movie.isVideo() &&
                isAdult() == movie.isAdult() &&
                getTitle().equals(movie.getTitle()) &&
                getOriginalTitle().equals(movie.getOriginalTitle()) &&
                getPosterPath().equals(movie.getPosterPath()) &&
                getBackgroundPath().equals(movie.getBackgroundPath()) &&
                getOriginalLanguage().equals(movie.getOriginalLanguage()) &&
                getGenreIds().equals(movie.getGenreIds()) &&
                getOverview().equals(movie.getOverview()) &&
                getReleaseDate().equals(movie.getReleaseDate());
    }
}
