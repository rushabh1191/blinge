package com.blinge.deliveryguy;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by rushabh on 18/03/16.
 */
public class BlingeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this);

    }
}
