package com.wassim.androidmvpbase.data.local.preferences;

import net.orange_box.storebox.annotations.method.KeyByString;

/**
 * Created by ltaief on 1/5/2016.
 */
public interface Preferences {
    @KeyByString("first_time_preferences")
    boolean isSynced();

    @KeyByString("first_time_preferences")
    void setIsSynced(boolean value);
}
