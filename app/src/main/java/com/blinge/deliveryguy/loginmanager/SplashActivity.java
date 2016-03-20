package com.blinge.deliveryguy.loginmanager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.blinge.deliveryguy.BlingeBaseActivity;
import com.blinge.deliveryguy.ordermanager.BlingeLandingPage;
import com.blinge.deliveryguy.R;
import com.parse.ParseUser;

public class SplashActivity extends BlingeBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               initializeApp();
           }
       },3000);

    }

    void initializeApp(){
        Class activityToBeLoaded;
        if(ParseUser.getCurrentUser()==null){

            activityToBeLoaded=BlingeLogin.class;
        }
        else {
            activityToBeLoaded=BlingeLandingPage.class;
        }

        Intent intent=new Intent(this,activityToBeLoaded);
        startActivity(intent);
        finish();
    }
}
