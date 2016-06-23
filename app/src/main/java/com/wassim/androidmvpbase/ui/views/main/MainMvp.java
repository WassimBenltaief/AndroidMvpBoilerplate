package com.wassim.androidmvpbase.ui.views.main;

import com.wassim.androidmvpbase.data.model.Movie;
import com.wassim.androidmvpbase.ui.base.MvpView;

import java.util.List;

interface MainMvp {

    interface View extends MvpView {
        void showMovies(List<Movie> movies);
        void showEmpty();
        void showError();
        void showProgress();
        void hideProgress();
    }
    interface Presenter extends MvpView {
        void attachToProvider();
        void detachFromProvider();
    }

}
