package com.wassim.androidmvpbase.ui.base;

/**
 * Created by beltaief on 3/22/2016.
 */
public interface Provider<V extends MvpView> {

    void attachPresenter(V mvpView);

    void detachPresenter();
}