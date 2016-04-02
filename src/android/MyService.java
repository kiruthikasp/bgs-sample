package com.red_folder.phonegap.plugin.backgroundservice.sample;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.widget.Toast; 
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

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
import android.content.pm.PackageManager;

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
	    	    private final static long ONCE_PER_DAY = 1000*60*60*24;

		    //private final static int ONE_DAY = 1;
		    private final static int TWO_AM = 9;
		    private final static int ZERO_MINUTES = 0;
	        public void run() 
	        {
	        	long currennTime = System.currentTimeMillis();
	        	long stopTime = currennTime + 2000;//provide the 2hrs time it should execute 1000*60*60*2
		          while(stopTime != System.currentTimeMillis()){
		              // Do your Job Here
		            System.out.println("Start Job"+stopTime);
		            System.out.println("End Job"+System.currentTimeMillis());
		          }
        	   start();		
                   toastHandler.sendEmptyMessage(0);
                   
	        }
	    }    
	private static Date getTomorrowMorning2AM(){

        Date date2am = new java.util.Date(); 
           date2am.setHours(TWO_AM); 
           date2am.setMinutes(ZERO_MINUTES); 

           return date2am;
      }
    //call this method from your servlet init method
    public static void startTask(){
        MyTimerTask task = new MyTimerTask();
        Timer timer = new Timer();  
        timer.schedule(task,getTomorrowMorning2AM(),1000*10);// for your case u need to give 1000*60*60*24
    }
    public static void main(String args[]){
        start();		
        toastHandler.sendEmptyMessage(0);

    }
    
	public void start() {
		
		Intent LaunchIntent;
		
		try {
		
			//LaunchIntent = this.getActivity().getPackageManager().getLaunchIntentForPackage(com_name);
			
			//this.getActivity().startActivity(LaunchIntent);
			LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.pinnacle.hr");
			startActivity(LaunchIntent);

		} catch (Exception e) {
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
