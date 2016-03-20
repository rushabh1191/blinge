package com.blinge.deliveryguy.helpers;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
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

    public static boolean isGPSEnabled(Context context){
        final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        boolean isgpsEnabled = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        return isgpsEnabled;
    }

    public static void callHelpline(Context context){
        call(context.getResources().getString(R.string.help_line),context);
    }
}
