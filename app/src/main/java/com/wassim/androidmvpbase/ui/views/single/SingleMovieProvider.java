package com.wassim.androidmvpbase.ui.views.single;

import com.wassim.androidmvpbase.data.DataManager;
import com.wassim.androidmvpbase.data.local.database.Movie;
import com.wassim.androidmvpbase.ui.base.BaseProvider;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by wassim on 6/23/16.
 */

class SingleMovieProvider extends BaseProvider<SingleMovieMvp.Presenter> {

    private final DataManager mDataManager;

    @Inject
    public SingleMovieProvider(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    Observable<Movie> verifyMovie(long id) {
        return mDataManager.getDatabaseHelper()
                .verifyMovie(id);
    }

    public void uncheck(Movie movie) {
        mDataManager.getDatabaseHelper().uncheck(movie);
    }

    public void check(Movie movie) {
        mDataManager.getDatabaseHelper().check(movie);
    }
}
