package com.example.shiftingsystem;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class bgWorker extends IntentService {

    public bgWorker() {
        // Used to name the worker thread, important only for debugging.
        super("notofication-service");
    }

    @Override
    public void onCreate() {
        super.onCreate(); // if you override onCreate(), make sure to call super().
        // If a Context object is needed, call getApplicationContext() here.
        client cl = new client();
        cl.callAsynchronousTask();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // This describes what will happen when service is triggered
    }
}