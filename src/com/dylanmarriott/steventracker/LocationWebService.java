package com.dylanmarriott.steventracker;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

public class LocationWebService extends AsyncTask<String, String, Boolean> {

public LocationWebService() {
}

@Override
protected Boolean doInBackground(String... arg0) {

	try {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost("http://192.168.1.101:8000/");
		post.setEntity(new StringEntity(arg0[1], "UTF8"));
		post.setHeader("Content-type", "application/json");
		client.execute(post);
	} catch (UnsupportedEncodingException e) {
		e.printStackTrace();
	} catch (ClientProtocolException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	
    return null;
}
}