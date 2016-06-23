package com.wassim.androidmvpbase.data.local.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PreferencesHelper {

    public static final String PREF_FILE_NAME = "promo";
    private final SharedPreferences mPref;
    private Context mContext;

    @Inject
    public PreferencesHelper(Context context) {
        mContext = context;
        mPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    public Context getContext(){
        return mContext;
    }
    public void clear() {
        mPref.edit().clear().apply();
    }


    public void setLongValue(String key, long value) {
        mPref.edit()
                .putLong(key, value)
                .apply();
    }

    public void setStringValue(String key, String value) {
        mPref.edit()
                .putString(key, value)
                .apply();
    }

    public void setBooleanValue(String key, boolean value) {
        mPref.edit()
                .putBoolean(key, value)
                .apply();
    }

    public long getLongValue(String key) {
        return mPref.getLong(key, 0);
    }

    public String getStringValue(String key) {
        return mPref.getString(key, null);
    }

    public boolean getBooleanValue(String key) {
        return mPref.getBoolean(key, false);
    }

}