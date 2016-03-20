package com.blinge.deliveryguy.ordermanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.blinge.deliveryguy.BlingeBaseActivity;
import com.blinge.deliveryguy.R;
import com.parse.CountCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BlingeLandingPage extends BlingeBaseActivity {


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

        setTag(OrderInformation.TYPE_DELIVER, OrderInformation.STATUS_PENDING, btnPendingDelivery,"Pending Deliveries");
        setTag(OrderInformation.TYPE_PICKUP, OrderInformation.STATUS_PENDING, btnPendingPickup,"Pending Pickups");
        setTag(OrderInformation.TYPE_DELIVER, OrderInformation.STATUS_COMPLETED, btnCompletedDelivery,"Completed Deliveries");
        setTag(OrderInformation.TYPE_PICKUP, OrderInformation.STATUS_COMPLETED, btnCompletedPickup,"Completed Pickups");


    }

    @Override
    protected void onStart() {
        super.onStart();
        getCount(OrderInformation.TYPE_DELIVER, OrderInformation.STATUS_PENDING, btnPendingDelivery
                ,"Pending Deliveries");
        getCount(OrderInformation.TYPE_PICKUP, OrderInformation.STATUS_PENDING, btnPendingPickup
                ,"Pending Pickups");
        getCount(OrderInformation.TYPE_DELIVER, OrderInformation.STATUS_COMPLETED, btnCompletedDelivery
                ,"Completed Deliveries");
        getCount(OrderInformation.TYPE_DELIVER, OrderInformation.STATUS_COMPLETED, btnCompletedPickup
                ,"Completed Pickups");
    }

    @OnClick({R.id.btn_completed_pickup, R.id.btn_completed_delivery, R.id.btn_pending_pickup, R.id.btn_pending_delivery})
    void showOrderList(View view) {

        String tag[] = getTag(view);
        Intent intent = ShowOrderListActivity.getIntentToStartActivity(tag[1], tag[0], this,tag[2]);
        startActivity(intent);
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
                btn.setText(textToBeShown + "(" + count + ")");
            }
        });
    }

    public static Intent getIntentToStartThisActivity(Context context) {

        Intent intent = new Intent(context, BlingeLandingPage.class);
        return intent;
    }

}
