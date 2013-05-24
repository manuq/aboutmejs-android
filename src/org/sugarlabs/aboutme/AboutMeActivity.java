package org.sugarlabs.aboutme;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;
import android.webkit.ConsoleMessage;
import org.sugarlabs.aboutme.WebAppInterface;

public class AboutMeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Remove title bar as we already have the sugar toolbar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_about_me);

        // Configure the webview setup in the xml layout
        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.addJavascriptInterface(new WebAppInterface(this), "AndroidActivity");

        // Allow javascript
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // This setting defaults to false since API level 16
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            webSettings.setAllowFileAccessFromFileURLs(true);
    }

        // Make sure links in the webview is handled by the webview
        // and not sent to a full browser
        myWebView.setWebViewClient(new WebViewClient());

        // Send javascript console messages to Eclipse LogCat
        myWebView.setWebChromeClient(new WebChromeClient() {
          public boolean onConsoleMessage(ConsoleMessage cm) {
            Log.d("Sugar Activity", cm.message() + " -- From line "
                  + cm.lineNumber() + " of "
                  + cm.sourceId() );
            return true;
          }
        });

        // Finally, load the Sugar activity
        myWebView.loadUrl("file:///android_asset/index.html");
    }
    
}
