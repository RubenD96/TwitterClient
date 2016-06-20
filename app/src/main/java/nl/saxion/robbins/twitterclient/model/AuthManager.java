package nl.saxion.robbins.twitterclient.model;

import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.oauth.OAuth10aService;

/**
 * Created by Ruben on 6/17/2016.
 */
public class AuthManager {

    private static AuthManager instance;
    private OAuth10aService service;

    private AuthManager() {
        service = new ServiceBuilder()
                .apiKey("QHBkxB4E3VoqAEuuIYGkrOnrP")
                .apiSecret("WX5p3Kfu4ILF0bqj6OxhY7Uw3eSicaln6g168UGahj5KFxcU2D")
                .callback("http://www.google.nl")
                .build(TwitterApi.instance());
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
}
