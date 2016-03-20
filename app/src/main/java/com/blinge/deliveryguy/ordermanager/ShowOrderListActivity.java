package com.blinge.deliveryguy.ordermanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.blinge.deliveryguy.BlingeBaseActivity;
import com.blinge.deliveryguy.R;

public class ShowOrderListActivity extends BlingeBaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_show_order_list);

        Bundle bundle=getIntent().getExtras();
        ShowOrderListFragment fragment=ShowOrderListFragment.getInstance(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,fragment).commit();

    }

    public static Intent getIntentToStartActivity(String status,String type,Context context,String title){
        Intent intent=new Intent(context,ShowOrderListActivity.class);
        intent.putExtra(ShowOrderListFragment.ARG_STATUS,status);
        intent.putExtra(ShowOrderListFragment.ARG_TYPE,type);
        intent.putExtra(ShowOrderListFragment.ARG_TITLE,title);
        return intent;
    }

}
