package com.wassim.androidmvpbase.data.local.preferences;

import android.content.Context;

import com.wassim.androidmvpbase.injection.ApplicationContext;

import net.orange_box.storebox.StoreBox;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PreferencesHelper {
    private Preferences mPreferences;

    @Inject
    public PreferencesHelper(@ApplicationContext Context context) {
        mPreferences = StoreBox.create(context, Preferences.class);
    }

    public Preferences getPreferences() {
        return mPreferences;
    }

}
