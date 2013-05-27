package org.sugarlabs.aboutme;

import android.app.Activity;
import android.content.Context;
import android.os.Message;
import android.webkit.JavascriptInterface;

public class WebAppInterface {
    Context mContext;

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
        Message msg = Message.obtain(null, SugarService.MSG_GET_XO_COLOR, 0, 0);
    	((AboutMeActivity) mContext).sendMessage(msg);
    }

    @JavascriptInterface
    public void setXOColor(String colors) {
        Message msg = Message.obtain(null, SugarService.MSG_SET_XO_COLOR, 0, 0);
        msg.obj = colors;
    	((AboutMeActivity) mContext).sendMessage(msg);
    }
    
    void messageCallback(Message msg) {
        switch (msg.what) {
        case SugarService.MSG_GET_XO_COLOR:
        	((AboutMeActivity) mContext).webView.loadUrl("javascript:activity = require('sugar-html-activity/activity');activity.runAndroidCallback('" + msg.obj + "')");
            break;
        }
    }
}
