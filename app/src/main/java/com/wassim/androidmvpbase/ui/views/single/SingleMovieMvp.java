package com.wassim.androidmvpbase.ui.views.single;

import com.wassim.androidmvpbase.data.local.database.Movie;
import com.wassim.androidmvpbase.ui.base.MvpView;

public interface SingleMovieMvp {

    interface View extends MvpView {
        void favoritesChecked(boolean state, boolean btnClicked);

        void onMovieLoaded(Movie movie);
    }

    interface Presenter extends MvpView {
        void attachToProvider();

        void detachFromProvider();
    }

}
