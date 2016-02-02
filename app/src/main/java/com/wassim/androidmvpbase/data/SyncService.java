package com.wassim.androidmvpbase.data;

//**

import android.content.Context;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.OneoffTask;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;
import com.google.android.gms.gcm.TaskParams;

/**
* Created by ltaief on 10/22/2015.
* Background service for synchronizing data
*/
public class SyncService extends GcmTaskService {
    private static final String TAG = SyncService.class.getSimpleName();
    public static final String GCM_REPEAT_TAG = "repeat|[7200,1800]";
    public static final String GCM_ONEOFF_TAG = "oneoff|[0,0]";

    @Override
    public void onInitializeTasks() {
        super.onInitializeTasks();
    }

    @Override
    public int onRunTask(TaskParams taskParams) {
         /*
            do service operations here
         */
        Log.d("Sync", "hitted");
        return GcmNetworkManager.RESULT_SUCCESS;
    }

    public static void scheduleRepeat(Context context) {
        //in this method, single Repeating task is scheduled
        // (the target service that will be called is MyTaskService.class)
        try {
            PeriodicTask periodic = new PeriodicTask.Builder()
                    //specify target service - must extend GcmTaskService
                    .setService(SyncService.class)
                    //repeat every 60*60*2 seconds
                    .setPeriod(60 * 60 * 2)
                    //specify how much earlier the task can be executed (in seconds)
                    .setFlex(120)
                    //tag that is unique to this task (can be used to cancel task)
                    .setTag(GCM_REPEAT_TAG)
                    //whether the task persists after device reboot
                    .setPersisted(true)
                    //if another task with same tag is already scheduled, replace it with this task
                    .setUpdateCurrent(true)
                    //set required network state, this line is optional
                    .setRequiredNetwork(Task.NETWORK_STATE_ANY)
                    //request that charging must be connected, this line is optional
                    .setRequiresCharging(false)
                    .build();
            GcmNetworkManager.getInstance(context).schedule(periodic);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            Crashlytics.logException(e);
        }
    }

    public static void scheduleOneOff(Context context) {
        //in this method, single OneOff task is scheduled
        // (the target service that will be called is MyTaskService.class)
        try {
            OneoffTask oneoff = new OneoffTask.Builder()
                    //specify target service - must extend GcmTaskService
                    .setService(SyncService.class)
                    //tag that is unique to this task (can be used to cancel task)
                    .setTag(GCM_ONEOFF_TAG)
                    //executed between 0 - 10s from now
                    .setExecutionWindow(0, 1)
                    //set required network state, this line is optional
                    .setRequiredNetwork(Task.NETWORK_STATE_ANY)
                    //request that charging must be connected, this line is optional
                    .setRequiresCharging(false)
                    //if another task with same tag is already scheduled, replace it with this task
                    .setUpdateCurrent(true)
                    .build();
            GcmNetworkManager.getInstance(context).schedule(oneoff);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            Crashlytics.logException(e);
        }
    }

    public static void cancelOneOff(Context context) {
        GcmNetworkManager
                .getInstance(context)
                .cancelTask(GCM_ONEOFF_TAG, SyncService.class);
    }

    public static void cancelRepeat(Context context) {
        GcmNetworkManager
                .getInstance(context)
                .cancelTask(GCM_REPEAT_TAG, SyncService.class);
    }

    public static void cancelAll(Context context) {
        GcmNetworkManager
                .getInstance(context)
                .cancelAllTasks(SyncService.class);
    }
}