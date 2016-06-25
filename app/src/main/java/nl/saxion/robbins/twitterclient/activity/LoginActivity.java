package nl.saxion.robbins.twitterclient.activity;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.scribejava.core.builder.api.DefaultApi10a;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;

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
        RequestTokenTask requestTokenTask = new RequestTokenTask();
        requestTokenTask.execute();

        wvLogin.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http://www.google.nl")) {
                    Uri uri = Uri.parse(url);
                    String verifier = uri.getQueryParameter("oauth_verifier");

                    new AccessTokenTask(verifier).execute();
                    finish();
                    return true;
                }
                return false;
            }
        });
    }

    private class RequestTokenTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            requestToken = AuthManager.getInstance().getRequestToken();
            String url = AuthManager.getInstance().getService().getAuthorizationUrl(requestToken) + "?oauth_token=" + requestToken.getToken();
            return url;
        }

        @Override
        protected void onPostExecute(String url) {
            wvLogin.loadUrl(url);
        }
    }

    private class AccessTokenTask extends AsyncTask<String, Void, String> {
        String verifier;

        public AccessTokenTask(String verifier) {
            this.verifier = verifier;
        }

        @Override
        protected String doInBackground(String... params) {
            OAuth1AccessToken accessToken = AuthManager.getInstance().getAccessToken(verifier);
            String strAccessToken = accessToken.getToken();
            AuthManager.getInstance().setAccessToken(accessToken);

            return strAccessToken;
        }

        @Override
        protected void onPostExecute(String s) {
            if (requestToken.getToken().equals(s)) {
                finish();
            }
        }
    }

    public static class TwitterAPI extends DefaultApi10a {
        private static TwitterAPI instance;

        public static TwitterAPI getInstance(){
            if(instance == null){
                instance = new TwitterAPI();
            }
            return instance;
        }

        @Override
        public String getRequestTokenEndpoint() {
            return "https://api.twitter.com/oauth/request_token";
        }

        @Override
        public String getAccessTokenEndpoint() {
            return "https://api.twitter.com/oauth/access_token";
        }

        @Override
        public String getAuthorizationUrl(OAuth1RequestToken requestToken) {
            return "https://api.twitter.com/oauth/authorize";
        }
    }

}
