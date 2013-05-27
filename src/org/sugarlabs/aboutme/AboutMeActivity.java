package org.sugarlabs.aboutme;

import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.util.Log;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;
import android.webkit.ConsoleMessage;

import org.sugarlabs.aboutme.WebAppInterface;

public class AboutMeActivity extends Activity {
    Messenger mService = null;
    boolean mBound = false;
    WebAppInterface webAppInterface;
    WebView webView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Remove title bar as we already have the sugar toolbar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_about_me);

        // Configure the webview setup in the xml layout
        webView = (WebView) findViewById(R.id.webview);
        webAppInterface = new WebAppInterface(this);
        webView.addJavascriptInterface(webAppInterface, "AndroidActivity");

        // Allow javascript
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // This setting defaults to false since API level 16
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            webSettings.setAllowFileAccessFromFileURLs(true);
    }

        // Make sure links in the webview is handled by the webview
        // and not sent to a full browser
        webView.setWebViewClient(new WebViewClient());

        // Send javascript console messages to Eclipse LogCat
        webView.setWebChromeClient(new WebChromeClient() {
          public boolean onConsoleMessage(ConsoleMessage cm) {
            Log.d("Sugar Activity", cm.message() + " -- From line "
                  + cm.lineNumber() + " of "
                  + cm.sourceId() );
            return true;
          }
        });

        // Finally, load the Sugar activity
        webView.loadUrl("file:///android_asset/index.html");
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, SugarService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    /**
     * Handler of incoming messages from service.
     */
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
        	webAppInterface.messageCallback(msg);
        }
    }
    
    /**
     * Target we publish for clients to send messages to IncomingHandler.
     */
    final Messenger mMessenger = new Messenger(new IncomingHandler());

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            mService = new Messenger(service);
            mBound = true;
        }

        public void onServiceDisconnected(ComponentName className) {
            mService = null;
            mBound = false;
        }
    };

    public void sendMessage(Message msg) {
        msg.replyTo = mMessenger;
        if (!mBound) return;
        try {
            mService.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
