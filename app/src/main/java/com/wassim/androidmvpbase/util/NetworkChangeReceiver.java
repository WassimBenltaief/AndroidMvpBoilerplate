package com.wassim.androidmvpbase.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.wassim.androidmvpbase.App;
import com.wassim.androidmvpbase.data.model.NetworkConnectivity;

/**
 * Created by Wassim on 09/02/2016.
 */
public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {
        boolean status = NetworkUtil.isNetworkConnected(context);

        RxEventBusHelper rxBus = App.get(context).getComponent().datamanager().getEventPoster();
        Log.d("NetworkChangeReceiver", "changed");
        if(rxBus.hasObservers()){
            rxBus.send(new NetworkConnectivity(status));
        }
    }
}