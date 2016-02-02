package com.wassim.androidmvpbase.ui.views.single;

import com.wassim.androidmvpbase.ui.base.MvpView;

public interface SingleMovieMvpView extends MvpView {
    void favoritesChecked(boolean state, boolean btnClicked);
}
