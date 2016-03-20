package com.blinge.deliveryguy.ordermanager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.blinge.deliveryguy.BlingeBaseActivity;
import com.blinge.deliveryguy.R;
import com.blinge.deliveryguy.helpers.BlingeUtilities;
import com.blinge.deliveryguy.helpers.ConfirmationWindow;
import com.blinge.deliveryguy.helpers.PermissionHelper;
import com.blinge.deliveryguy.loginmanager.BlingeLogin;
import com.blinge.deliveryguy.task.LocationService;
import com.parse.CountCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BlingeLandingPage extends BlingeBaseActivity {


    private static final int LOCATION_PERMISSION = 1;
    @Bind(R.id.btn_pending_delivery)
    Button btnPendingDelivery;
    @Bind(R.id.btn_pending_pickup)
    Button btnPendingPickup;
    @Bind(R.id.btn_completed_delivery)
    Button btnCompletedDelivery;
    @Bind(R.id.btn_completed_pickup)
    Button btnCompletedPickup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blinge_landing_page);
        ButterKnife.bind(this);

        setTag(OrderInformation.TYPE_DELIVER, OrderInformation.STATUS_PENDING, btnPendingDelivery, "Pending Deliveries");
        setTag(OrderInformation.TYPE_PICKUP, OrderInformation.STATUS_PENDING, btnPendingPickup,"Pending Pickups");
        setTag(OrderInformation.TYPE_DELIVER, OrderInformation.STATUS_COMPLETED, btnCompletedDelivery,"Completed Deliveries");
        setTag(OrderInformation.TYPE_PICKUP, OrderInformation.STATUS_COMPLETED, btnCompletedPickup, "Completed Pickups");

        if(!PermissionHelper.isPermitted(Manifest.permission.ACCESS_FINE_LOCATION,this)) {
            PermissionHelper.requestPermission(this, Manifest.permission.ACCESS_FINE_LOCATION,
                    "Allow location permission", LOCATION_PERMISSION);
        }
        else {
            startLocationService();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            startLocationService();
        }
    }

    void startLocationService(){
        Intent intent = new Intent(this, LocationService.class);
        startService(intent);
        if(!BlingeUtilities.isGPSEnabled(this)) {
            showStartGSPDialog();
        }

    }
    @Override
    protected void onStart() {
        super.onStart();
        getCount(OrderInformation.TYPE_DELIVER, OrderInformation.STATUS_PENDING, btnPendingDelivery
                , "Pending Deliveries");
        getCount(OrderInformation.TYPE_PICKUP, OrderInformation.STATUS_PENDING, btnPendingPickup
                , "Pending Pickups");
        getCount(OrderInformation.TYPE_DELIVER, OrderInformation.STATUS_COMPLETED, btnCompletedDelivery
                , "Completed Deliveries");
        getCount(OrderInformation.TYPE_PICKUP, OrderInformation.STATUS_COMPLETED, btnCompletedPickup
                , "Completed Pickups");
    }

    @OnClick({R.id.btn_completed_pickup, R.id.btn_completed_delivery, R.id.btn_pending_pickup, R.id.btn_pending_delivery})
    void showOrderList(View view) {

        String tag[] = getTag(view);
        Intent intent = ShowOrderListActivity.getIntentToStartActivity(tag[1], tag[0], this,tag[2]);
        startActivity(intent);
    }

    @OnClick(R.id.btn_logout)
    void logout(){

        new ConfirmationWindow(this,"Confirm Logout","Are you sure you want to logout","OK","Cancel"){

            @Override
            protected void setPositiveResponse() {
                super.setPositiveResponse();
                ParseUser.logOut();
                finish();
                Intent intent=new Intent(BlingeLandingPage.this, BlingeLogin.class);
                startActivity(intent);

                stopService(new Intent(BlingeLandingPage.this,LocationService.class));
            }
        };

    }

    void showStartGSPDialog() {

        new ConfirmationWindow(this, "GPS is disabled", "Please enable " +
                "GPS to detect your location", "OK", "") {
            @Override
            protected void setPositiveResponse() {
                super.setPositiveResponse();
                Intent callGPSSettingIntent = new Intent(
                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(callGPSSettingIntent);
            }
        };
    }

    void setTag(String type, String status, Button btn,String title) {
        btn.setTag(type + ":" + status+":"+title);
    }

    String[] getTag(View btn) {
        return btn.getTag().toString().split(":");
    }

    void getCount(String type, String status, final Button btn, final String textToBeShown) {
        ParseQuery query = ParseQuery.getQuery(OrderInformation.class);
        query.whereEqualTo("orderStatus", status);
        query.whereEqualTo("orderType", type);


        query.countInBackground(new CountCallback() {
            @Override
            public void done(int count, ParseException e) {
                if(e!=null) {
                    btn.setText(textToBeShown + "(" + count + ")");
                }
            }
        });
    }

    public static Intent getIntentToStartThisActivity(Context context) {

        Intent intent = new Intent(context, BlingeLandingPage.class);
        return intent;
    }

}
