package nl.saxion.robbins.twitterclient.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.oauth.OAuth10aService;

import nl.saxion.robbins.twitterclient.R;
import nl.saxion.robbins.twitterclient.model.AuthManager;

public class LoginActivity extends AppCompatActivity {

    private WebView wvLogin;
    private OAuth1RequestToken requestToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        wvLogin = (WebView) findViewById(R.id.wv_login);
        RequestTokenTask requestTokenTask = new RequestTokenTask(AuthManager.getInstance().getService());
        requestTokenTask.execute();
    }

    private class RequestTokenTask extends AsyncTask<String, Void, String> {

        OAuth10aService service;

        public RequestTokenTask(OAuth10aService service) {
            this.service = service;
        }

        @Override
        protected String doInBackground(String... params) {
            requestToken = service.getRequestToken();
            String authUrl = service.getAuthorizationUrl(requestToken);
            return authUrl;
        }

        @Override
        protected void onPostExecute(String authUrl) {
            wvLogin.loadUrl(authUrl);
            wvLogin.setWebViewClient(new WebViewClient() {

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if (url.startsWith("http://www.google.nl")) {
                        int equalSign = url.lastIndexOf("=");
                        Log.i("" + equalSign, "shouldOverrideUrlLoading: hoi");
                        String verifier = url.substring(equalSign + 1);
                        Log.i("hoi", url);
                        Log.d("doei", verifier);

                        new AccessTokenTask(service, verifier).execute();
                        return true;
                    }
                    return false;
                }
            });
        }
    }

    private class AccessTokenTask extends AsyncTask<String, Void, String> {
        OAuth10aService service;
        String verifier;

        public AccessTokenTask(OAuth10aService service, String verifier) {
            this.service = service;
            this.verifier = verifier;
        }

        @Override
        protected String doInBackground(String... params) {
            OAuth1AccessToken accessToken = service.getAccessToken(requestToken, verifier);
            String strAccessToken = accessToken.getToken();

            return strAccessToken;
        }

        @Override
        protected void onPostExecute(String s) {
            if (requestToken.getToken().equals(s)) {
                finish();
            }
        }
    }
}
