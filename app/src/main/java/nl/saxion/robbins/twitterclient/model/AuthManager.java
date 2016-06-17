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
        final OAuth10aService service = new ServiceBuilder()
                .apiKey("jlKDxCHl89Qjz1rJMEVGeDqP8")
                .apiSecret("roMZVAfGnAov9m4vdsHrq7gzPuW8dW1knHMOlfdABkNOFbBkLw")
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
