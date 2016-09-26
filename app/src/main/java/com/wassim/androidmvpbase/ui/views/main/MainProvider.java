package com.wassim.androidmvpbase.ui.views.main;

import com.wassim.androidmvpbase.data.DataManager;
import com.wassim.androidmvpbase.data.local.database.Movie;
import com.wassim.androidmvpbase.ui.base.BaseProvider;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by wassim on 6/23/16.
 */

public class MainProvider extends BaseProvider<MainMvp.Presenter> {

    private final DataManager mDataManager;

    @Inject
    public MainProvider(DataManager dataManager) {
        this.mDataManager = dataManager;
    }


    Observable<List<Movie>> getMovies() {
        return mDataManager.getDatabaseHelper().loadMovies();
    }

    void syncMovies() {
        mDataManager.getApiService()
                .getMovies()
                .subscribeOn(Schedulers.io())
                .subscribe(movies ->
                        mDataManager.getDatabaseHelper().saveMovies(movies)
                );
    }
}
