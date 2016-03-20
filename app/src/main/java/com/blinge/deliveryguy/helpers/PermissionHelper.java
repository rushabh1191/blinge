package com.blinge.deliveryguy.helpers;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;

import com.blinge.deliveryguy.BlingeBaseActivity;

/**
 * Created by Rushabh on 10/21/15.
 */
public class PermissionHelper {


    public static boolean isPermitted(String permission,Context context){

        int permissionStatus=context.checkCallingOrSelfPermission(permission);
        return permissionStatus== PackageManager.PERMISSION_GRANTED;
    }


    public static void requestPermission(final BlingeBaseActivity context, String permission,
                                         String permissionDetails, int requestId){
        if(!isPermitted(permission,context)) {
            if (context.shouldShowRequestPermissionRationale(permission)) {
                new ConfirmationWindow(context, "Permission needed", permissionDetails, "Ok", "Cancel") {
                    @Override
                    protected void setPositiveResponse() {
                        super.setPositiveResponse();
                        context.startActivity(new Intent
                                (android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + context.getPackageName())));
                    }
                };
            } else {
                ActivityCompat.requestPermissions(context,
                        new String[]{permission},
                        requestId);
            }
        }
    }



}
