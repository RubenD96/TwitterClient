package nl.saxion.robbins.twitterclient.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class Tweets {
    private static Tweets instance;
    private ArrayList<Tweet> tweets;

    public static Tweets getInstance() {
        if(instance == null) {
            instance = new Tweets();
        }

        return instance;
    }

    public void clearTweets() {
        tweets.clear();
    }

    /**
     * Constructs a new Tweet
     */
    private Tweets() {
        tweets = new ArrayList<>();
    }

    public ArrayList<Tweet> getTweets() {
        return tweets;
    }

    /**
     * Add a User to the users ArrayList
     * @param jsonObject JSONObject from which the User is created
     */
    public void addTweet(JSONObject jsonObject) throws ParseException, JSONException, IOException {
        tweets.add(new Tweet(jsonObject));
    }
}
