package com.blinge.deliveryguy.helpers;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

    public static void showNetworkNotAvailableDialog(Context context) {

        new ConfirmationWindow(context, "Network unavailable", "Please check your network connection", "Ok", "");
    }
    public static boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null)
                    for (int i = 0; i < info.length; i++)
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }

            }
        } catch (Exception e)

        {

        }
        return false;
    }

    public static void callHelpline(Context context){
        call(context.getResources().getString(R.string.help_line),context);
    }
}
