package com.dylanmarriott.steventracker;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

public class LocationService extends Service implements ILocation {

	WakeLock _wakeLock;
	private LocationHelper _locationHelper;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
	    super.onCreate();
	    PowerManager pm = (PowerManager) getSystemService(Service.POWER_SERVICE);
	    _wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "DoNotSleep");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		_locationHelper = new LocationHelper(this,this);
		_locationHelper.register();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		_locationHelper.unregister();
		super.onDestroy();
	}

	@Override
	public void locationUpdated(MyLocation location) {
		try {
			JSONObject json = new JSONObject();
			JSONObject locationJson = new JSONObject();
			locationJson.put("latitude", location.getLatitude());
			locationJson.put("longitude", location.getLongitude());
			locationJson.put("time", location.getTime());
			locationJson.put("accuracy", location.getAccuracy());
			locationJson.put("speed", location.getSpeed());
			locationJson.put("altitude", location.getAltitude());
			json.put("device", "1");
			json.put("location", locationJson);
			new LocationWebService().execute(new String[] { "http://192.168.1.101:8000/", json.toString() });
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
