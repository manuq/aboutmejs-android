package org.sugarlabs.aboutme;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.webkit.JavascriptInterface;

public class WebAppInterface {
    Context mContext;

    static final int MSG_GET_XO_COLOR = 1;
    static final int MSG_SET_XO_COLOR = 2;

    /** Instantiate the interface and set the context */
    WebAppInterface(Context c) {
        mContext = c;
    }

    @JavascriptInterface
    public void stop() {
    	// Maybe is better to stop it, not finish it
    	((Activity) mContext).finish();
    }

    @JavascriptInterface
    public void getXOColor() {
      Message msg = Message.obtain(null, MSG_GET_XO_COLOR, 0, 0);
      ((AboutMeActivity) mContext).sendMessage(msg);
    }

    @JavascriptInterface
    public void setXOColor(String colors) {
        Message msg = Message.obtain(null, MSG_SET_XO_COLOR, 0, 0);
        Bundle bundle = new Bundle();
        bundle.putString("colors", colors);
        msg.setData(bundle);
    	((AboutMeActivity) mContext).sendMessage(msg);
    }
    
    void messageCallback(Message msg) {
        switch (msg.what) {
        case MSG_GET_XO_COLOR:
        	Bundle data = msg.getData();
        	String colors = data.getString("colors");
        	((AboutMeActivity) mContext).webView.loadUrl("javascript:activity = require('sugar-html-activity/activity');activity.runAndroidCallback('" + colors + "')");
            break;
        }
    }
}
