package com.wassim.androidmvpbase.data.local.database;

import com.activeandroid.query.Select;
import com.wassim.androidmvpbase.data.local.preferences.PreferencesHelper;
import com.wassim.androidmvpbase.data.model.Movie;
import com.wassim.androidmvpbase.util.RxEventBusHelper;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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

    public Observable<Movie> saveMovies(final List<Movie> movies) {
        return Observable.create(new Observable.OnSubscribe<Movie>() {
            @Override
            public void call(Subscriber<? super Movie> subscriber) {
                for (Movie movie : movies) {
                    addMovie(movie);
                    subscriber.onNext(movie);
                }
                postEvent(movies);
                subscriber.onCompleted();
            }
        });
    }

    private void postEvent(List<Movie> movies) {
        mEventPoster.post(movies);
    }
}
