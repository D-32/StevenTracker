package com.dylanmarriott.steventracker;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;

public class LocationHelper {

    private MyLocation location;
    Context context;

    LocationManager lm;
    boolean gps_enabled=false;
    boolean network_enabled=false;
    ILocation activity;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (activity != null) {
                activity.locationUpdated(location);
            }
            handler.postDelayed(this, 1000);
        }
    };

    private LocationHelper(){
    }

    public LocationHelper(Context context){
        this();

        this.context = context;
        location = new MyLocation();
    }

    public LocationHelper(Context context, ILocation activity){
        this(context);

        this.activity = activity;
    }

    LocationListener locationListenerGps = new LocationListener() {
        public void onLocationChanged(Location location) {
            updateLocation(location);
        }
        public void onProviderDisabled(String provider) {}
        public void onProviderEnabled(String provider) {}
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };

    LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
        	updateLocation(location);
        }
        public void onProviderDisabled(String provider) {}
        public void onProviderEnabled(String provider) {}
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };

    private void updateLocation(Location newLocation){
        if(newLocation != null && newLocation.getTime() > location.getTime()){
            location.setLatitude(newLocation.getLatitude());
            location.setLongitude(newLocation.getLongitude());
            location.setTime(newLocation.getTime());
            location.setAccuracy(newLocation.getAccuracy());
            location.setSpeed(newLocation.getSpeed());
            location.setAltitude(newLocation.getAltitude());
        }
    }

    public MyLocation getLocation() {
        return location;
    }

    public void register() {
        if(lm==null){
            lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        }

        //exceptions will be thrown if provider is not permitted.
        try{gps_enabled=lm.isProviderEnabled(LocationManager.GPS_PROVIDER);}catch(Exception ex){}
        try{network_enabled=lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);}catch(Exception ex){}

        //don't start listeners if no provider is enabled
        if(gps_enabled || network_enabled){
            if(gps_enabled){
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGps);
            }
            if(network_enabled){
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerNetwork);
            }

            runnable.run();
        }
    }

    public void unregister() {
        lm.removeUpdates(locationListenerGps);
        lm.removeUpdates(locationListenerNetwork);
    }
}
