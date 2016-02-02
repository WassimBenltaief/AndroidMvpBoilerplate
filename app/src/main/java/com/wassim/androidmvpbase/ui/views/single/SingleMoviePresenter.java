package com.wassim.androidmvpbase.ui.views.single;

import com.wassim.androidmvpbase.data.DataManager;
import com.wassim.androidmvpbase.data.model.Movie;
import com.wassim.androidmvpbase.ui.base.BasePresenter;

import javax.inject.Inject;

public class SingleMoviePresenter extends BasePresenter<SingleMovieMvpView> {

    private final String mTAG = "SingleMoviePresenter";

    private DataManager mDataManager;


    @Inject
    public SingleMoviePresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    void checkFavorites(Movie movie) {
        Boolean exist = mDataManager.verifyMovie(movie.getId()) == true;
        if (exist) {
            getMvpView().favoritesChecked(true, false);
        } else {
            getMvpView().favoritesChecked(false, false);
        }
    }


    void modifyFavorites(Movie movie) {
        Boolean exist = mDataManager.verifyMovie(movie.getId()) == true;
        if (exist) {
            mDataManager.removeMovie(movie);
            getMvpView().favoritesChecked(false, true);
        } else {
            mDataManager.addMovie(movie);
            getMvpView().favoritesChecked(true, true);
        }
    }

}
