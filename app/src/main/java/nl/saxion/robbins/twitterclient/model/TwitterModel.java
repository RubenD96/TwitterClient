package nl.saxion.robbins.twitterclient.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;

/**
 * @author Ruben
 * @author Robbin
 *
 *         Twitter model that contains all other model objects
 */
public class TwitterModel extends Observable implements Observer {

    private ArrayList<Tweet> tweets;
    private ArrayList<User> users;
    private ArrayList<String> userIDs;
    private User user;
    private ArrayList<String> followerNames;

    public TwitterModel() {
        tweets = new ArrayList<>();
        users = new ArrayList<>();
        userIDs = new ArrayList<>();
        followerNames = new ArrayList<>();
    }

    /**
     * Get the list with tweets saved in this model
     */
    public ArrayList<Tweet> getTweets() {
        return tweets;
    }

    /**
     * Get the list with users saved in this model
     *
     * @return the list of users
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * Set the list with users to match the JSON result string
     *
     * @param result JSON string with users
     */
    public void setUsers(String result) {
        System.out.println("Start setUsers");

        if (result != null && !result.isEmpty()) {
            users.clear();

            JsonParser parser = new JsonParser(result);
            JSONArray userArray = null;

            try {
                userArray = parser.getArray("users");
            } catch (NullPointerException npe) {
                npe.getMessage();
            }

            if (userArray == null) {
                if (parser.getParentArray() != null) {
                    for (int i = 0; i < parser.getParentArray().length(); i++) {
                        users.add(new User(parser.getObject(i)));
                    }
                }
            } else {
                for (int i = 0; i < userArray.length(); i++) {
                    try {
                        users.add(new User(userArray.getJSONObject(i)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            refresh();
        }
    }

    /**
     * Set the list with userID's to match the JSON result string
     *
     * @param result JSON string with userID's
     */
    public void setUserIDs(String result) {
        userIDs.clear();

        JsonParser parser = new JsonParser(result);

        if (result != null) {
            JSONArray idArray = parser.getArray("ids");

            for (int i = 0; i < idArray.length() && i < 100; i++) {
                try {
                    userIDs.add(idArray.getString(i));
                    System.out.println(userIDs.size());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        RequestHandler downloader;
        String ids = null;
        System.out.println(userIDs.size());

        while (!userIDs.isEmpty()) {
            if (ids == null) {
                ids = String.valueOf(userIDs.get(0));
                userIDs.remove(0);
                System.out.println(userIDs.size());
            } else {
                ids = ids + "," + String.valueOf(userIDs.get(0));
                userIDs.remove(0);
                System.out.println(userIDs.size());
            }
        }
        System.out.println(ids);
        downloader = new RequestHandler(this, "https://api.twitter.com/1.1/users/lookup.json?user_id=" + ids, RequestHandler.GET_REQUEST);
        downloader.execute();
    }

    /**
     * Get the user
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Set the user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Set the tweet items with the JSON string provided
     */
    public void setTweetItems(String jsonLine) {
        if (jsonLine != null && !jsonLine.isEmpty()) {
            tweets.clear();
            JsonParser parser = new JsonParser(jsonLine);
            JSONArray statuses = parser.getArray("statuses");
            parser.setArray(statuses);
            for (int i = 0; i < statuses.length(); i++) {
                try {
                    tweets.add(new Tweet(parser.getObject(i)));
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            refresh();
        }
    }

    /**
     * Set the timeline with the JSON string provided
     */
    public void setTimeline(String timeline) {
        if (timeline != null && !timeline.isEmpty()) {
            tweets.clear();
            JsonParser parser = new JsonParser(timeline);
            if (parser.getParentArray() != null) {
                for (int i = 0; i < parser.getParentArray().length(); i++) {
                    try {
                        tweets.add(new Tweet(parser.getObject(i)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            refresh();
        }
    }

    /**
     * Clear the saved tweets inside this model
     */
    public void clearTweets() {
        tweets.clear();
        refresh();
    }

    /**
     * Notify the observers of this model that something has changed
     */
    public void refresh() {
        setChanged();
        notifyObservers();
    }

    /**
     * Call refresh when something has changed
     */
    @Override
    public void update(Observable observable, Object data) {
        refresh();
    }
}
