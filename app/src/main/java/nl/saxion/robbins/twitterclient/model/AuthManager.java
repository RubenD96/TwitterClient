package nl.saxion.robbins.twitterclient.model;

import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.oauth.OAuth10aService;

import nl.saxion.robbins.twitterclient.activity.LoginActivity;

/**
 * Created by Ruben on 6/17/2016.
 */
public class AuthManager {

    private static AuthManager instance;
    private OAuth10aService service;
    OAuth1RequestToken requestToken;
    OAuth1AccessToken accessToken;

    private AuthManager() {
        service = new ServiceBuilder()
                .apiKey("QHBkxB4E3VoqAEuuIYGkrOnrP")
                .apiSecret("WX5p3Kfu4ILF0bqj6OxhY7Uw3eSicaln6g168UGahj5KFxcU2D")
                .callback("http://www.google.nl")
                .build(LoginActivity.TwitterAPI.getInstance());
    }

    public static AuthManager getInstance() {
        if (instance == null) {
            instance = new AuthManager();
        }
        return instance;
    }

    public OAuth10aService getService() {
        return service;
    }

    public OAuth1RequestToken getRequestToken()
    {
        requestToken = service.getRequestToken();
        return requestToken;
    }

    public void setRequestToken(OAuth1RequestToken requestToken) {
        this.requestToken = requestToken;
    }

    public OAuth1AccessToken getAccessToken(String verifier) {
        return service.getAccessToken(this.requestToken,verifier);
    }

    public OAuth1AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(OAuth1AccessToken accessToken) {
        this.accessToken = accessToken;
    }
}
