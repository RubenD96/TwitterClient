package nl.saxion.robbins.twitterclient.model;

import android.os.AsyncTask;

import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;

import nl.saxion.robbins.twitterclient.activity.MainActivity;

/**
 * @author Ruben
 * @author Robbin
 *
 *         RequestHandler handles all requests done by the user, GET and POST alike.
 */
public class RequestHandler extends AsyncTask<Void, Void, String> {

    public static final int GET_REQUEST = 1;
    public static final int POST_REQUEST = 2;

    private AuthManager authManager;
    private TwitterModel model;
    private String url;
    private int request_type;
    private String parameter;

    public RequestHandler(TwitterModel model, String url, int request_type) {
        authManager = AuthManager.getInstance();
        this.model = model;
        this.url = url;
        this.request_type = request_type;
    }

    /**
     * Parameter constructor for posting a tweet
     * @param model
     * @param url
     * @param request_type
     * @param parameter tweet text
     */
    public RequestHandler(TwitterModel model, String url, int request_type, String parameter) {
        authManager = AuthManager.getInstance();
        this.model = model;
        this.url = url;
        this.request_type = request_type;
        this.parameter = parameter;
    }

    @Override
    protected String doInBackground(Void... params) {
        String responseStr = null;
        OAuthRequest request = null;
        if (request_type == GET_REQUEST) {
            request = new OAuthRequest(Verb.GET, url, authManager.getService());
        } else if (request_type == POST_REQUEST) {
            request = new OAuthRequest(Verb.POST, url, authManager.getService());
            if (url.startsWith("https://api.twitter.com/1.1/statuses/update.json")) {
                if (parameter != null) {
                    request.addParameter("status", parameter);
                } else {
                    System.out.println("Use a different constructor for RequestHandler or fill the 4th variable with a String");
                }
            }
            System.out.println(request.toString());
        }

        authManager.getService().signRequest(AuthManager.getInstance().getAccessToken(), request);
        Response response = request.send();
        System.out.println(response.getCode());

        if (response.isSuccessful()) {
            System.out.println(response.getBody());
            responseStr = response.getBody();
        } else {
            System.out.println("RESPONSE WAS NOT SUCCESSFUL");
        }
        return responseStr;
    }

    @Override
    protected void onPostExecute(String strJson) {
        if (request_type == GET_REQUEST) {
            if (url.startsWith("https://api.twitter.com/1.1/statuses/home_timeline.json")) {
                model.setTimeline(strJson);
            } else if (url.startsWith("https://api.twitter.com/1.1/search/tweets.json")) {
                model.setTweetItems(strJson);
            } else if (url.startsWith("https://api.twitter.com/1.1/statuses/user_timeline.json")) {
                model.setTimeline(strJson);
            } else if (url.startsWith("https://api.twitter.com/1.1/followers/ids.json")) {
                model.setUserIDs(strJson);
            } else if (url.startsWith("https://api.twitter.com/1.1/users/lookup.json")) {
                model.setUsers(strJson);
            } else if(url.startsWith("https://api.twitter.com/1.1/friends/list.json")) {
                model.setUsers(strJson);
            }
        }
    }
}
