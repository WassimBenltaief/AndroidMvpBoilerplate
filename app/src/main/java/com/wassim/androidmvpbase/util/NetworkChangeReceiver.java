package com.wassim.androidmvpbase.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Wassim on 09/02/2016.
 */
public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {
        boolean status = NetworkUtil.isNetworkConnected(context);

        // post event
    }
}