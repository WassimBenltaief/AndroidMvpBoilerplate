package com.wassim.androidmvpbase.ui.views.single;

import com.wassim.androidmvpbase.data.DataManager;
import com.wassim.androidmvpbase.data.model.Movie;
import com.wassim.androidmvpbase.ui.base.BaseProvider;

import javax.inject.Inject;

/**
 * Created by wassim on 6/23/16.
 */

class SingleMovieProvider extends BaseProvider<SingleMovieMvp.Presenter> {

    private final DataManager mDataManager;

    @Inject
    public SingleMovieProvider(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    Boolean verifyMovie(int id) {
        return mDataManager.getDatabaseHelper().findMovie(id) != null;
    }

    void removeMovie(Movie movie) {
        mDataManager.getDatabaseHelper().removeMovie(movie);
    }

    void addMovie(Movie movie) {
        mDataManager.getDatabaseHelper().addMovie(movie);
    }
}
