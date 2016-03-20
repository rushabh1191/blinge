package com.blinge.deliveryguy.helpers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.blinge.deliveryguy.R;

/**
 * Created by rushabh on 20/03/16.
 */
public class BlingeUtilities {

    public static void call(String number,Context context){
        Intent intent=new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + number));
        context.startActivity(intent);
    }

    public static void callHelpline(Context context){
        call(context.getResources().getString(R.string.help_line),context);
    }
}
