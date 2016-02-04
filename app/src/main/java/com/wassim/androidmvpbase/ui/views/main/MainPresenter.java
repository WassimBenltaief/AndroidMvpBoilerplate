package com.wassim.androidmvpbase.ui.views.main;

import com.wassim.androidmvpbase.data.DataManager;
import com.wassim.androidmvpbase.data.model.Movie;
import com.wassim.androidmvpbase.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

public class MainPresenter extends BasePresenter<MainMvpView> {
    private final String mTAG = "MainPresenter";
    private DataManager mDataManager;
    private CompositeSubscription mCompositeSubscription;

    @Inject
    public MainPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
        this.mCompositeSubscription = new CompositeSubscription();
        registerEventHandler();
    }

    private void registerEventHandler() {
        mCompositeSubscription.add(mDataManager.getEventPoster().get().subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                if(o instanceof Movie){
                    // do something with this movie
                }
            }
        }));
    }

    public void loadMovies() {
        mCompositeSubscription.add(mDataManager.getMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Movie>>() {
                    @Override
                    public void onCompleted() {
                        Timber.d("MainPresenter.loadMovies().getMovies() completed.");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "MainPresenter.loadMovies.getMovies : " +
                                    "There was an error loading the movies");
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(List<Movie> movies) {
                        Timber.d("MainPresenter.loadMovies.getMovies loaded " +
                                movies.size());
                        if (movies.isEmpty()) {
                            getMvpView().showEmpty();
                        } else {
                            getMvpView().showMovies(movies);
                        }
                    }
                }));
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mCompositeSubscription != null && mCompositeSubscription.isUnsubscribed())
            mCompositeSubscription.unsubscribe();
    }
}
