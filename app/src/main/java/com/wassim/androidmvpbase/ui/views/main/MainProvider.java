package com.wassim.androidmvpbase.ui.views.main;

import com.wassim.androidmvpbase.data.DataManager;
import com.wassim.androidmvpbase.data.model.Movie;
import com.wassim.androidmvpbase.ui.base.BaseProvider;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

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

        return mDataManager.getApiService()
                .getMovies()
                .concatMap(new Func1<List<Movie>, Observable<Movie>>() {
                    @Override
                    public Observable<Movie> call(List<Movie> movies) {
                        return mDataManager.getDatabaseHelper().saveMovies(movies);
                    }
                })
                .toList();

        // if async / callback use interface to call presenter :
        /*if(isPresenterAttached()) {
            getMvpPresenter().method();
        }*/

    }
}
