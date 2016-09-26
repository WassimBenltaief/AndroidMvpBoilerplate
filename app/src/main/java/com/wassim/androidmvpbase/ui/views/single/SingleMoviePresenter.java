package com.wassim.androidmvpbase.ui.views.single;

import com.wassim.androidmvpbase.data.local.database.Movie;
import com.wassim.androidmvpbase.ui.base.BasePresenter;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

public class SingleMoviePresenter extends BasePresenter<SingleMovieMvp.View>
        implements SingleMovieMvp.Presenter {

    private final String mTAG = "SingleMoviePresenter";

    private SingleMovieProvider mProvider;

    @Inject
    public SingleMoviePresenter(SingleMovieProvider provider) {
        this.mProvider = provider;
    }

    void getMovie(long movieId) {
        mProvider.verifyMovie(movieId).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Movie>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Timber.e("Error verify movie" + e.getMessage());
            }

            @Override
            public void onNext(Movie movie) {
                if (isViewAttached()) {
                    getMvpView().onMovieLoaded(movie);
                }
            }
        });

    }


    void modifyFavorites(Movie movie) {
        if (movie.checked() == 1) {
            mProvider.uncheck(movie);
            if (isViewAttached()) {
                getMvpView().favoritesChecked(false, true);
            }
        } else {
            mProvider.check(movie);
            if (isViewAttached()) {
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
