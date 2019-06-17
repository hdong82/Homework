package com.leo.test.bookshelf;

import androidx.multidex.MultiDexApplication;

import timber.log.Timber;

public class App extends MultiDexApplication {
    private final String TAG = "com.leo.test.bookshelf";
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        this.instance = this;
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            Timber.tag(TAG);
        }
    }

    public static App getInstance() {
        return instance;
    }
}
