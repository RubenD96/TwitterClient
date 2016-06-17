package nl.saxion.robbins.twitterclient.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.oauth.OAuth10aService;

import nl.saxion.robbins.twitterclient.R;
import nl.saxion.robbins.twitterclient.model.AuthManager;

public class LoginActivity extends AppCompatActivity {

    WebView wvLogin;

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
            final OAuth1RequestToken requestToken = service.getRequestToken();
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
                        new AccesTokenTask().execute();
                    }
                    return false;
                }
            });
        }
    }

    private class AccesTokenTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            finish();
        }
    }
}
