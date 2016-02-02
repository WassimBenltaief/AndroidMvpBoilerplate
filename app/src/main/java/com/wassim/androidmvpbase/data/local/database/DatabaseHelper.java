package com.wassim.androidmvpbase.data.local.database;

import com.activeandroid.query.Select;
import com.wassim.androidmvpbase.data.local.preferences.PreferencesHelper;
import com.wassim.androidmvpbase.data.model.Movie;
import com.wassim.androidmvpbase.util.EventPosterHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DatabaseHelper {
    private final PreferencesHelper mPreferencesHelper;
    private final EventPosterHelper mEventPoster;

    @Inject
    public DatabaseHelper(PreferencesHelper preferencesHelper,
                          EventPosterHelper eventPosterHelper) {
        mPreferencesHelper = preferencesHelper;
        mEventPoster = eventPosterHelper;
    }

    public DBMovie findMovie(int remoteId) {
        DBMovie dbMovie = new Select()
                .from(DBMovie.class)
                .where("mRemoteId = ?", remoteId)
                .executeSingle();
            return dbMovie;
    }

    public void addMovie(Movie movie) {

        if(findMovie(movie.getId())== null){
            DBMovie dbMovie = new DBMovie();
            dbMovie.setRemoteId(movie.getId());
            dbMovie.setTitle(movie.getTitle());
            dbMovie.setImage(movie.getImage());
            dbMovie.setRating(movie.getRating());
            dbMovie.setGenre(movie.getGenre());
            dbMovie.setReleaseYear(movie.getReleaseYear());
            dbMovie.setSynopsis(movie.getSynopsis());
            dbMovie.save();
        }

    }

    public void removeMovie(Movie movie) {
        DBMovie dbMovie = findMovie(movie.getId());
        if (dbMovie != null)
            dbMovie.delete();
    }
}
