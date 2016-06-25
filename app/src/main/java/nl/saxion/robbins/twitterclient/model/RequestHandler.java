package nl.saxion.robbins.twitterclient.model;

import android.os.AsyncTask;

import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;

import nl.saxion.robbins.twitterclient.activity.MainActivity;

/**
 * Created by Ruben on 6/25/2016.
 */
public class RequestHandler extends AsyncTask<Void, Void, String> {

    public static final int GET_REQUEST = 1;
    public static final int POST_REQUEST = 2;

    private AuthManager authManager;
    private String url;
    private int request_type;

    public RequestHandler(String url, int request_type) {
        authManager = AuthManager.getInstance();
        this.url = url;
        this.request_type = request_type;
    }

    @Override
    protected String doInBackground(Void... params) {
        String responseStr = null;
        if (request_type == GET_REQUEST) {
            OAuthRequest request = new OAuthRequest(Verb.GET, url, authManager.getService());

            authManager.getService().signRequest(AuthManager.getInstance().getAccessToken(), request);
            Response response = request.send();

            if(response.isSuccessful()) {
                System.out.println(response.getBody());
                responseStr = response.getBody();
            }
        }
        return responseStr;
    }

    @Override
    protected void onPostExecute(String strJson) {
        if (request_type == GET_REQUEST) {
            try {
                try {
                    JSONArray jsonArray = new JSONArray(strJson);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            Tweets.getInstance().addTweet(jsonArray.getJSONObject(i));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (request_type == POST_REQUEST) {

        }
    }
}
