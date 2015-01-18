package com.dylanmarriott.steventracker;

public class MyLocation {
    private double latitude;
    private double longitude;
    private long time;
    private double accuracy;
    private float speed;
    private double altitude;

    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public double getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}
	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public double getAltitude() {
		return altitude;
	}
	public void setAltitude(double d) {
		this.altitude = d;
	}
}