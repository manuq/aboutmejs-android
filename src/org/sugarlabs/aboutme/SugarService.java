package org.sugarlabs.aboutme;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;

public class SugarService extends Service {
	private final IBinder mBinder = new LocalBinder();
	public static final String SETTINGS = "SugarSettings";
	SharedPreferences sugarSettings;
	
	public class LocalBinder extends Binder {
        SugarService getService() {
            // Return this instance of LocalService so clients can call public methods
            return SugarService.this;
        }
    }
	@Override
	public IBinder onBind(Intent arg0) {
		sugarSettings = getSharedPreferences(SETTINGS, 0);
		SharedPreferences.Editor editor = sugarSettings.edit();
		editor.putString("user-color", "#FFFF00,#00FFFF");
		editor.commit();
		return mBinder;
	}

	/** method for clients */
	public String getXOColor() {
        // I don't know how to use arrays in java yet :)
		return sugarSettings.getString("user-color", "");
    }
}
