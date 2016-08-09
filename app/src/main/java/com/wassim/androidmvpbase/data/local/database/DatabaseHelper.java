package com.wassim.androidmvpbase.data.local.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite.Query;
import com.wassim.androidmvpbase.MovieModel;
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
    private static final String TAG = DatabaseHelper.class.getSimpleName();
    private final PreferencesHelper mPreferencesHelper;
    private final RxEventBusHelper mEventPoster;
    private final BriteDatabase mDb;

    @Inject
    public DatabaseHelper(PreferencesHelper preferencesHelper,
                          RxEventBusHelper rxEventBusHelper, BriteDatabase db) {
        mPreferencesHelper = preferencesHelper;
        mEventPoster = rxEventBusHelper;
        mDb = db;
    }

    public Observable<Movie> findMovie(long remoteId) {
        return mDb.createQuery(MovieModel.TABLE_NAME, Movie.SELECT_BY_ID, new String[]{"" + remoteId})
                .map(new Func1<Query, Movie>() {
                    @Override
                    public Movie call(Query query) {
                        Cursor cursor = query.run();
                        // TODO parse data...
                        try {
                            if (!cursor.moveToNext()) {
                                throw new AssertionError("No rows");
                            }
                            return Movie.MAPPER_BY_ID.map(cursor);
                        } finally {
                            cursor.close();
                        }
                    }
                });
    }

    public Observable<Movie> loadMovies() {
        return mDb.createQuery(MovieModel.TABLE_NAME, Movie.SELECT_BY_ID, new String[]{""})
                .map(new Func1<Query, Movie>() {
                    @Override
                    public Movie call(Query query) {
                        Cursor cursor = query.run();
                        // TODO parse data...
                        try {
                            if (!cursor.moveToNext()) {
                                throw new AssertionError("No rows");
                            }
                            return Movie.MAPPER_BY_ID.map(cursor);
                        } finally {
                            cursor.close();
                        }
                    }
                });
    }

    public void addMovie(MovieModel movie) {
        long index = mDb.insert(MovieModel.TABLE_NAME,
                Movie.FACTORY.marshal(movie).asContentValues(), SQLiteDatabase.CONFLICT_REPLACE);
        Log.d(TAG, "index :" + index);
    }

    public void removeMovie(Movie movie) {
        //mDb.delete(Movie.TABLE_NAME, Movie.ID + "?= ?s", movie.id());
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
