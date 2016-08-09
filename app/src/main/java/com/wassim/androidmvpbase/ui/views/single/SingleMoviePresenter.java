package com.wassim.androidmvpbase.ui.views.single;

import com.wassim.androidmvpbase.data.model.Movie;
import com.wassim.androidmvpbase.ui.base.BasePresenter;

import javax.inject.Inject;

public class SingleMoviePresenter extends BasePresenter<SingleMovieMvp.View>
        implements SingleMovieMvp.Presenter {

    private final String mTAG = "SingleMoviePresenter";

    private SingleMovieProvider mProvider;

    @Inject
    public SingleMoviePresenter(SingleMovieProvider provider) {
        this.mProvider = provider;
    }

    void checkFavorites(Movie movie) {
        Boolean exist = mProvider.verifyMovie(movie.id());
        if (exist) {
            if(isViewAttached()) {
                getMvpView().favoritesChecked(true, false);
            }
        } else {
            if(isViewAttached()) {
                getMvpView().favoritesChecked(false, false);
            }
        }
    }


    void modifyFavorites(Movie movie) {
        Boolean exist = mProvider.verifyMovie(movie.id());
        if (exist) {
            mProvider.removeMovie(movie);
            if(isViewAttached()) {
                getMvpView().favoritesChecked(false, true);
            }
        } else {
            mProvider.addMovie(movie);
            if(isViewAttached()) {
                getMvpView().favoritesChecked(true, true);
            }
        }
    }

    @Override
    public void attachToProvider() {
        mProvider.attachPresenter(this);
    }

    @Override
    public void detachFromProvider() {
        mProvider.detachPresenter();
        /*if (!mCompositeSubscription.isUnsubscribed()) {
            mCompositeSubscription.unsubscribe();
        }*/
    }
}
