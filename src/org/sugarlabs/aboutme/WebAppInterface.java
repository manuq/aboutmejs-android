package org.sugarlabs.aboutme;

import android.app.Activity;
import android.content.Context;
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
    public String getXOColor() {
        // I don't know how to use arrays in java yet :)
        return "#FFFF00,#00FFFF";
    }
}
