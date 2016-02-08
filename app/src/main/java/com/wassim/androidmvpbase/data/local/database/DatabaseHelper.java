package com.wassim.androidmvpbase.data.local.database;

import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.query.Select;
import com.wassim.androidmvpbase.data.local.preferences.PreferencesHelper;
import com.wassim.androidmvpbase.data.model.Movie;
import com.wassim.androidmvpbase.data.model.SyncTask;
import com.wassim.androidmvpbase.util.RxEventBusHelper;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

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
        return Observable.create(new Observable.OnSubscribe<Movie>() {
            @Override
            public void call(final Subscriber<? super Movie> subscriber) {
                if (subscriber.isUnsubscribed()) return;
                Observable.from(movies)
                        .observeOn(Schedulers.io())
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Movie>() {
                            @Override
                            public void onCompleted() {
                                Log.d("DatabaseHelper","saveMovies.onCompleted");
                                Log.d("DatabaseHelper","saveMovies.onCompleted send SyncTask");
                                if(mEventPoster.hasObservers()) {
                                    Log.d("DatabaseHelper","RxBus has Observable");
                                    mEventPoster.send(new SyncTask());
                                }
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Timber.w(e, "Error syncing");
                            }

                            @Override
                            public void onNext(Movie movie) {
                                addMovie(movie);
                                subscriber.onNext(movie);
                            }
                        });
            }
        });
    }
}
