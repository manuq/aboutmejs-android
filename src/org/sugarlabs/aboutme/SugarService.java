package org.sugarlabs.aboutme;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

public class SugarService extends Service {
	public static final String SETTINGS = "SugarSettings";
	SharedPreferences sugarSettings;
	
	/** Command to the service to display a message */
    static final int MSG_GET_XO_COLOR = 1;
    static final int MSG_SET_XO_COLOR = 2;

    /**
     * Handler of incoming messages from clients.
     */
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            String colors;
            switch (msg.what) {
            case MSG_GET_XO_COLOR:
        		colors = sugarSettings.getString("user-color", "#FFFF00,#00FFFF");
        		Messenger mClient = msg.replyTo;
        		Message replyMsg = Message.obtain(null, MSG_GET_XO_COLOR, 0, 0);
        		replyMsg.obj = colors;
                try {
                    mClient.send(replyMsg);
                } catch (RemoteException e) {
                	// what to do here?
                }
                break;
            case MSG_SET_XO_COLOR:
            	colors = ((String) msg.obj);
        		SharedPreferences.Editor editor = sugarSettings.edit();
        		editor.putString("user-color", colors);
        		editor.commit();
                break;
            default:
                super.handleMessage(msg);
            }
        }
    }
    
    /**
     * Target we publish for clients to send messages to IncomingHandler.
     */
    final Messenger mMessenger = new Messenger(new IncomingHandler());

    @Override
	public IBinder onBind(Intent intent) {
		sugarSettings = getSharedPreferences(SETTINGS, 0);
        return mMessenger.getBinder();
	}
}
