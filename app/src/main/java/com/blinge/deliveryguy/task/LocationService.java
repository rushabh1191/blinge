package com.blinge.deliveryguy.task;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;

import com.blinge.deliveryguy.helpers.Logger;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseUser;


public class LocationService extends Service implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    protected GoogleApiClient googleApiClient;
    protected LocationRequest locationRequester;

    final int INTERVAL_IN_SECOND = 30*60;
    final int FASTEST_INTERVAL = INTERVAL_IN_SECOND / 2;



    boolean isRequestingUpdates = false;


    public LocationService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        registerReceiver
                (gpsReceiver, new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));
        locationRequester = new LocationRequest();
        locationRequester.setInterval(INTERVAL_IN_SECOND * 1000);
        locationRequester.setFastestInterval(FASTEST_INTERVAL * 1000);
        locationRequester.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        isRequestingUpdates = false;

        googleApiClient.connect();

    }

    BroadcastReceiver gpsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates
                (googleApiClient,
                        locationRequester, this);
        isRequestingUpdates = true;

    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        isRequestingUpdates = false;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Intent restartService = new Intent(getApplicationContext(), this.getClass());
        restartService.setPackage(getPackageName());
        PendingIntent restartServicePI = PendingIntent.getService(
                getApplicationContext(), 1, restartService,
                PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmService = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmService.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 1000, restartServicePI);
    }

    @Override
    public void onConnected(Bundle bundle) {

        if (!isRequestingUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

        if (isRequestingUpdates) {
            stopLocationUpdates();
        }

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isRequestingUpdates) {
            googleApiClient.disconnect();
        }

        unregisterReceiver(gpsReceiver);
    }

    @Override
    public void onLocationChanged(Location location) {
        Logger.log("beta","Sending update received");
        if(location!=null) {
            ParseGeoPoint point = new ParseGeoPoint(location.getLatitude(), location.getLongitude());
            if (ParseUser.getCurrentUser() != null) {
                Logger.log("beta","Sending location");
                String userId = ParseUser.getCurrentUser().getObjectId();
                ParseObject query = ParseObject.create("_User");
                query.setObjectId(userId);
                query.put("currentLocation", point);
                query.saveInBackground();
            }
        }


    }



    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
