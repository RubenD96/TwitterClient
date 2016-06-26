package nl.saxion.robbins.twitterclient.model;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.oauth.OAuth10aService;

import nl.saxion.robbins.twitterclient.activity.LoginActivity;

/**
 * @author Ruben
 * @author Robbin
 *
 *         AuthManager handles the login of the user on this application
 */
public class AuthManager {

    private static AuthManager instance;
    private OAuth10aService service;
    private OAuth1RequestToken requestToken;
    private OAuth1AccessToken accessToken;

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

    /**
     * Gets the current service
     * @return service
     */
    public OAuth10aService getService() {
        return service;
    }

    /**
     * Gets the current request token
     * @return the current request token
     */
    public OAuth1RequestToken getRequestToken() {
        requestToken = service.getRequestToken();
        return requestToken;
    }

    public OAuth1AccessToken getAccessToken(String verifier) {
        return service.getAccessToken(this.requestToken, verifier);
    }

    public OAuth1AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(OAuth1AccessToken accessToken) {
        this.accessToken = accessToken;
    }

}
