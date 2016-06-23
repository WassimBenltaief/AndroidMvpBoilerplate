package com.wassim.androidmvpbase.data.local.database;

import com.activeandroid.query.Select;
import com.wassim.androidmvpbase.data.local.preferences.PreferencesHelper;
import com.wassim.androidmvpbase.data.model.Movie;
import com.wassim.androidmvpbase.util.RxEventBusHelper;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Func1;

@Singleton
public class DatabaseHelper {
    private final PreferencesHelper mPreferencesHelper;
    private final RxEventBusHelper mEventPoster;

    @Inject
    public DatabaseHelper(PreferencesHelper preferencesHelper,
                          RxEventBusHelper rxEventBusHelper) {
        mPreferencesHelper = preferencesHelper;
        mEventPoster = rxEventBusHelper;
    }

    public DBMovie findMovie(int remoteId) {
        return new Select()
                .from(DBMovie.class)
                .where("mRemoteId = ?", remoteId)
                .executeSingle();
    }

    public Observable<Movie> loadMovies() {
        List<DBMovie> movies = new Select().from(DBMovie.class).execute();
        return Observable.from(movies)
                .map(new Func1<DBMovie, Movie>() {
                    @Override
                    public Movie call(DBMovie dbMovie) {
                        Movie movie = new Movie();
                        movie.setId(dbMovie.getRemoteId());
                        movie.setGenre(dbMovie.getGenre());
                        movie.setImage(dbMovie.getImage());
                        movie.setRating(dbMovie.getRating());
                        movie.setReleaseYear(dbMovie.getReleaseYear());
                        movie.setSynopsis(dbMovie.getSynopsis());
                        movie.setTitle(dbMovie.getTitle());
                        return movie;
                    }
                });
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

    public Observable<Movie> saveMovies(final List<Movie> movies) {
        return Observable.from(movies)
                .concatMap(new Func1<Movie, Observable<Movie>>() {
                    @Override
                    public Observable<Movie> call(Movie movie) {
                        addMovie(movie);
                        return Observable.just(movie);
                    }
                });
    }
}
