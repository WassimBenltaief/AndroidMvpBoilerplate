package com.wassim.androidmvpbase.ui.views.main;

import com.wassim.androidmvpbase.data.local.database.Movie;
import com.wassim.androidmvpbase.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainPresenter extends BasePresenter<MainMvp.View>
        implements MainMvp.Presenter{

    private final String mTAG = "MainPresenter";
    private MainProvider mProvider;
    private CompositeSubscription mCompositeSubscription;

    @Inject
    MainPresenter(MainProvider provider) {
        mProvider = provider;
        mCompositeSubscription = new CompositeSubscription();
        mProvider.syncMovies();
    }

    void syncMovies() {
        mProvider.syncMovies();
    }

    void loadMovies() {
        if(isViewAttached()){
            getMvpView().showProgress();
            mCompositeSubscription.add(mProvider.getMovies()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<List<Movie>>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {

                            if (isViewAttached()) {
                                getMvpView().hideProgress();
                                getMvpView().showError();
                            }
                        }

                        @Override
                        public void onNext(List<Movie> movies) {
                            if (isViewAttached()) {
                                getMvpView().hideProgress();
                                if (movies.isEmpty()) {
                                    getMvpView().showEmpty();
                                } else {
                                    getMvpView().showMovies(movies);
                                }
                            }
                        }
                    }));
        }
    }

    @Override
    public void attachToProvider() {
        mProvider.attachPresenter(this);
    }

    @Override
    public void detachFromProvider() {
        mProvider.detachPresenter();
        if (!mCompositeSubscription.isUnsubscribed()) {
            mCompositeSubscription.unsubscribe();
        }
    }
}
