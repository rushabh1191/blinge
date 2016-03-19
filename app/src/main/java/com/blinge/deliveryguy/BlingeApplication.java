package com.blinge.deliveryguy;

import android.app.Application;

import com.blinge.deliveryguy.ordermanager.OrderInformation;
import com.parse.Parse;
import com.parse.ParseRole;

/**
 * Created by rushabh on 18/03/16.
 */
public class BlingeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this);
        ParseRole.registerSubclass(OrderInformation.class);
    }
}
