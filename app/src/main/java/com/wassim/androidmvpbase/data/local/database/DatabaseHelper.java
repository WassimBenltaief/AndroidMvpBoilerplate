package com.wassim.androidmvpbase.data.local.database;

import android.database.sqlite.SQLiteDatabase;

import com.squareup.sqlbrite.BriteDatabase;
import com.wassim.androidmvpbase.MovieModel;
import com.wassim.androidmvpbase.data.model.RemoteMovie;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class DatabaseHelper {
    private static final String TAG = DatabaseHelper.class.getSimpleName();
    private final BriteDatabase mDb;

    @Inject
    public DatabaseHelper(BriteDatabase db) {
        mDb = db;
    }

    public Observable<Movie> verifyMovie(long remoteId) {
        return mDb.createQuery(MovieModel.TABLE_NAME, Movie.SELECT_BY_ID, new String[]{"" + remoteId})
                .mapToOne(Movie.MAPPER_BY_ID::map);
    }

    public Observable<List<Movie>> loadMovies() {
        Observable<List<Movie>> movies = mDb.createQuery(MovieModel.TABLE_NAME, Movie.SELECT_ALL)
                .mapToList(Movie.MAPPER_ALL::map);
        return movies;
    }

    public long addMovie(RemoteMovie movie) {
        return mDb.insert(
                MovieModel.TABLE_NAME,
                Movie.FACTORY.marshal()
                        .id(movie.id)
                        .genre(movie.genre)
                        .image(movie.image)
                        .rating(movie.rating)
                        .releaseYear(movie.releaseYear)
                        .synopsis(movie.synopsis)
                        .title(movie.title)
                        .checked(movie.checked)
                        .asContentValues(),
                SQLiteDatabase.CONFLICT_REPLACE
        );
    }

    public void removeMovie(Movie movie) {
        mDb.delete(Movie.TABLE_NAME, Movie.ID + "?= ?s", movie.id()+"");
    }

    public void saveMovies(final List<RemoteMovie> movies) {
        BriteDatabase.Transaction transaction = mDb.newTransaction();
        try {
            for (RemoteMovie movie : movies) {
                addMovie(movie);
            }
            transaction.markSuccessful();
        } finally {
            transaction.end();
        }

    }

    public void uncheck(Movie movie) {
        long index = mDb.insert(
                MovieModel.TABLE_NAME,
                Movie.FACTORY.marshal()
                        .id(movie.id())
                        .genre(movie.genre())
                        .image(movie.image())
                        .rating(movie.rating())
                        .releaseYear(movie.releaseYear())
                        .synopsis(movie.synopsis())
                        .title(movie.title())
                        .checked(0L)
                        .asContentValues(),
                SQLiteDatabase.CONFLICT_REPLACE
        );
    }

    public void check(Movie movie) {
        long index = mDb.insert(
                MovieModel.TABLE_NAME,
                Movie.FACTORY.marshal()
                        .id(movie.id())
                        .genre(movie.genre())
                        .image(movie.image())
                        .rating(movie.rating())
                        .releaseYear(movie.releaseYear())
                        .synopsis(movie.synopsis())
                        .title(movie.title())
                        .checked(1L)
                        .asContentValues(),
                SQLiteDatabase.CONFLICT_REPLACE
        );
    }
}
