package com.blinge.deliveryguy.ordermanager;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.blinge.deliveryguy.BlingeBaseActivity;
import com.blinge.deliveryguy.R;
import com.blinge.deliveryguy.helpers.BlingeParseObject;
import com.blinge.deliveryguy.helpers.ParseProxyObject;

public class OrderDetailsActivity extends BlingeBaseActivity {

    OrderInformation orderInformation;

    String ARG_ORDER_INFO="order_info";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        Bundle bundle=getIntent().getExtras();

        orderInformation= (OrderInformation)
                new BlingeParseObject((ParseProxyObject) bundle.getSerializable(ARG_ORDER_INFO));
    }


    public Intent getIntentToStartActivity(OrderInformation orderInformation,Context context){
        ParseProxyObject parseProxyObject=new ParseProxyObject(orderInformation);
        Bundle bundle=new Bundle();
        bundle.putSerializable(ARG_ORDER_INFO, parseProxyObject);
        Intent intent=new Intent(context,OrderDetailsActivity.class);
        intent.putExtra(ARG_ORDER_INFO,parseProxyObject);
        return intent;

    }
}
