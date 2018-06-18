package com.specifix.pureleagues;

import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.parse.Parse;
import com.parse.ParseInstallation;

import io.fabric.sdk.android.Fabric;

public class App extends MultiDexApplication {
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        Fabric.with(this, new Crashlytics());

        Parse.enableLocalDatastore(this);
        Parse.initialize(this);
        //Parse.initialize(this, "72C6FFA9CF4C2", "B21EC6C43FBC1998D74BEDBEE9D69");

        ParseInstallation.getCurrentInstallation().saveInBackground();
    }

    public static App getInstance() {
        return instance;
    }
}
