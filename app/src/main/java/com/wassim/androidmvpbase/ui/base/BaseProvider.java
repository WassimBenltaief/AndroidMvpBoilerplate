package com.wassim.androidmvpbase.ui.base;

/**
 * Created by beltaief on 3/22/2016.
 */
public class BaseProvider<T extends MvpView> implements Provider<T> {

    private T mMvpPresenter;

    @Override
    public void attachPresenter(T mvpPresenter) {
        mMvpPresenter = mvpPresenter;
    }

    @Override
    public void detachPresenter() {
        mMvpPresenter = null;
    }

    public boolean isPresenterAttached() {
        return mMvpPresenter != null;
    }

    public T getMvpPresenter() {
        return mMvpPresenter;
    }

    public void checkPresenterAttached() {
        if (!isPresenterAttached()) throw new MvpPresenterNotAttachedException();
    }

    public static class MvpPresenterNotAttachedException extends RuntimeException {
        public MvpPresenterNotAttachedException() {
            super("Please call Provider.attachPresenter(MvpPresenter) before" +
                    " requesting data to the Provider");
        }
    }
}
