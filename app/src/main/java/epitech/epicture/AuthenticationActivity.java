package epitech.epicture;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.*;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class AuthenticationActivity extends AppCompatActivity {

    private String musername = null;
    private String maccessToken = null;
    private String mrefreshToken = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        String method = getIntent().getStringExtra("method");
        switch (method) {
            case "login":
                login();
                break;
            case "logout":
                logout();
                break;
            default:
                System.out.println("Authentication: method not found");
                break;
        }
    }

    private void login() {
        WebView imgurWebView = (WebView) findViewById(R.id.webview);
        imgurWebView.setBackgroundColor(Color.TRANSPARENT);
        imgurWebView.loadUrl(getString(R.string.api_imgur_login));
        imgurWebView.getSettings().setBuiltInZoomControls(false);
        imgurWebView.getSettings().setDisplayZoomControls(false);
        imgurWebView.getSettings().setJavaScriptEnabled(true);
        imgurWebView.getSettings().setSupportZoom(true);
        imgurWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        imgurWebView.getSettings().setSupportMultipleWindows(true);
        imgurWebView.getSettings().setUserAgentString("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.13; rv:61.0) Gecko/20100101 Firefox/61.0");
        imgurWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (url.contains("www.getpostman.com/oauth2/callback?state=APPLICATION_STATE")) {
                    splitUrl(url, view);
                    setResult(Activity.RESULT_OK, new Intent()
                            .putExtra("username", musername)
                            .putExtra("accessToken", maccessToken)
                            .putExtra("refreshToken", mrefreshToken));
                    finish();
                }
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
    }

    private void logout() {

        android.webkit.CookieManager cookieManager = CookieManager.getInstance();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.removeAllCookies(new ValueCallback<Boolean>() {
                @Override
                public void onReceiveValue(Boolean aBoolean) {
                    Log.d("MDR", "Cookie removed: " + aBoolean);
                }
            });
        } else cookieManager.removeAllCookie();
        finish();
    }

    private void splitUrl(String url, WebView view) {
        String[] outerSplit = url.split("\\#")[1].split("\\&");
        int index = 0;

        for (String s : outerSplit) {
            String[] innerSplit = s.split("\\=");
            switch (index) {
                // Access Token
                case 0:
                    maccessToken = innerSplit[1];
                    break;
                // Refresh Token
                case 3:
                    mrefreshToken = innerSplit[1];
                    break;
                // Username
                case 4:
                    musername = innerSplit[1];
                    break;
                default:
            }
            index++;
        }
    }
}
