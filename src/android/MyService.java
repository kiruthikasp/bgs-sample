package com.red_folder.phonegap.plugin.backgroundservice.sample;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.widget.Toast; 
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import android.util.Log;
import android.widget.Toast;
import android.os.Binder;
import android.os.IBinder;
import android.os.Handler;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.os.Messenger;
import android.widget.Toast;

import com.red_folder.phonegap.plugin.backgroundservice.BackgroundService;

public class MyService extends BackgroundService {
	
	private final static String TAG = MyService.class.getSimpleName();
	
	private String mHelloTo = "World";
	private static Timer timer = new Timer(); 
	    private Context ctx;
	
	    public IBinder onBind(Intent arg0) 
	    {
	          return null;
	    }
	
    public void onCreate() {
    	          super.onCreate();
	          ctx = this; 
	          startService();
    }
 
	public int onStartCommand(Intent intent, int flags, int startId) {
	    Log.i("LocalService", "Received start id " + startId + ": " + intent);
	    Toast.makeText(this, "LocalServic Received start id "+ startId , Toast.LENGTH_SHORT).show();
	    // We want this service to continue running until it is explicitly
	    // stopped, so return sticky.
	    return START_STICKY;
	}
	    private void startService()
	    {           
	        timer.scheduleAtFixedRate(new mainTask(), 0, 5000);
	    }
	
	    private class mainTask extends TimerTask
	    { 
	        public void run() 
	        {
	        	
        	String serverUrl = "http://rajaserver01.go.dyndns.org:7777/hr_signin";
				
	        Map<String, String> params = new HashMap<String, String>();
		params.put("dbname","test_erp_data");
		params.put("uname","admin");
		params.put("pwd","admin@123");
		params.put("ip", "localhost");
		params.put("wifi", "Pinnacle7");
		params.put("device","Test Mobile")
		
		if (NetworkUtil.networkAvailable(ctx)) {
			try {
				postRequest(serverUrl, params, ctx);
			} catch (IOException e) {

				e.printStackTrace();
			}
		} else {

		Toast.makeText(getApplicationContext(), "No internet found!", Toast.LENGTH_SHORT).show();
		}

	            toastHandler.sendEmptyMessage(0);
	        }
	    }    
	
	
		
		private static void postRequest(String endpoint,
			final Map<String, String> params, Context context)
			throws IOException {

		URL url;
		if (NetworkUtil.isConnectingToInternet(context)) {
			try {

				url = new URL(endpoint);
				StringRequest request = VolleyHelper
						.sendVolleyPOSTRequestWithParameter(url.toString(),
								params);
				VolleySingleton.getInstance(context).addToRequestQueue(request);

			} catch (MalformedURLException e) {
				throw new IllegalArgumentException("invalid url: " + endpoint);
			} catch (Exception e) {

			}
		}

	}
	
	    public void onDestroy() 
	    {
	          super.onDestroy();
	          Toast.makeText(this, "Service Stopped ...", Toast.LENGTH_SHORT).show();
	    }
	
	    private final Handler toastHandler = new Handler()
	    {
	        @Override
	        public void handleMessage(Message msg)
	        {
	            Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_SHORT).show();
	        }
	    };    
	@Override
	protected JSONObject doWork() {
		JSONObject result = new JSONObject();
		
		try {
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
			String now = df.format(new Date(System.currentTimeMillis())); 

			String msg = "Hello " + this.mHelloTo + " - its currently " + now;
			result.put("Message", msg);
			Toast.makeText(this.getApplicationContext(), "this is my Toast message!!! =)",
   			Toast.LENGTH_LONG).show();
			Log.d(TAG, msg);
			Toast.makeText(getApplicationContext(), "do work!!", Toast.LENGTH_SHORT).show();
			 Log.i("Service", "doSomethingOnService() called");
		} catch (JSONException e) {
		}
		
		return result;	
	}
	
	@Override
	protected JSONObject getConfig() {
		JSONObject result = new JSONObject();
		
		try {
			result.put("HelloTo", this.mHelloTo);
			Toast.makeText(getApplicationContext(), "get config", Toast.LENGTH_SHORT).show();
		} catch (JSONException e) {
		}
		
		return result;
	}

	@Override
	protected void setConfig(JSONObject config) {
		try {
			if (config.has("HelloTo"))
				this.mHelloTo = config.getString("HelloTo");
				Toast.makeText(getApplicationContext(), "setconfig", Toast.LENGTH_SHORT).show();
		} catch (JSONException e) {
		}
		
	}     

	@Override
	protected JSONObject initialiseLatestResult() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onTimerEnabled() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onTimerDisabled() {
		// TODO Auto-generated method stub
		
	}


}
