package nl.saxion.robbins.twitterclient.model;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/** Major twitter model that contains all other model objects.
 *
 * @author Niels Jan */
public class TwitterModel extends Observable implements Observer {

    /**********************************************************************************/
    /*********************** ENCAPSULATED INSTANCE VARIABLES **************************/
    /**********************************************************************************/

    private ArrayList<Tweet> tweets;
    private ArrayList<User> users;
    private ArrayList<Integer> userIDs;
    //private Profile profile;
    private User user;
    private HashSet<String> friendIds;
    private ArrayList<String> friendNames;
    private ArrayList<String> followerNames;

    /**********************************************************************************/
    /********************************** CONSTRUCTOR ***********************************/
    /**********************************************************************************/

    public TwitterModel() {
        // Initiate all lists / sets
        tweets = new ArrayList<>();
        users = new ArrayList<>();
        userIDs = new ArrayList<>();
        followerNames = new ArrayList<>();
        friendNames = new ArrayList<>();
        friendIds = new HashSet<>();
    }

    /**********************************************************************************/
    /************************************ GETTERS *************************************/
    /**********************************************************************************/

    /** Get the profile saved in this model */
    /*public Profile getProfile() {
        return profile;
    }*/

    /** Get the set with friend ids */
    public HashSet<String> getFriends() {
        return friendIds;
    }

    /** Get the list with tweets saved in this model */
    public ArrayList<Tweet> getTweets() {
        return tweets;
    }

    /**
     * Get the list with users saved in this model
     * @return the list of users
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * Get the list with userID's saved in this model
     * @return list of userID's
     */
    public ArrayList<Integer> getUserIDs() {
        return userIDs;
    }

    /** Get the list with follower names */
    public ArrayList<String> getfollowerNames() {
        return followerNames;
    }

    /** Get the list with friend names */
    public ArrayList<String> getFriendNames() {
        return friendNames;
    }

    /**
     * Get the user
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**********************************************************************************/
    /************************************ SETTERS *************************************/
    /**********************************************************************************/

    /** Add a friend to the friend ids list */
    public void addFriend(String friend) {
        friendIds.add(friend);
    }

    /** remove a friend from the friend ids list */
    public void removeFriend(String friend) {
        friendIds.remove(friend);
    }

    /** Set the tweet items with the JSON string provided */
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

    /** Set the timeline with the JSON string provided */
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
            /*JsonParser profileParser;
            try {
                profileParser = new JsonParser((JSONObject) parser.getParentArray().get(0));
                profile = new Profile(profileParser.getObject("user"), this);
                profile.addObserver(this);
            } catch (JSONException e) {
                e.printStackTrace();
            }*/
            refresh();
        }
    }

    /**
     * Set the user
     */
    public void setUser(User user) {
        this.user = user;
        /*JsonParser parser = new JsonParser(user);
        try {
            this.user = new User(parser.getParentObject());
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }

    /**
     * Set the list with users to match the JSON result string
     * @param result JSON string with users
     */
    public void setUsers(String result) {
        System.out.println("Start setUsers " + result);

        if (result != null && !result.isEmpty()) {
            users.clear();
            System.out.println("Users cleared");

            JsonParser parser = new JsonParser(result);
            System.out.println("New parser");

            if (parser.getParentArray() != null) {
                System.out.println("Entered if...");

                for (int i = 0; i < parser.getParentArray().length(); i++) {
                    System.out.println("Entered for...");
                    try {
                        users.add(new User(parser.getObject(i)));
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            refresh();
        }
    }

    /**
     * Set the list with userID's to match the JSON result string
     * @param result JSON string with userID's
     */
    public void setUserIDs(String result) {
        userIDs.clear();

        JsonParser parser = new JsonParser(result);

        if (result != null) {
            JSONArray idArray = parser.getArray("ids");

            for (int i = 0; i < idArray.length(); i++) {
                try {
                    userIDs.add(idArray.getInt(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        RequestHandler downloader;
        String ids = null;
        boolean startRequest = false;
        System.out.println(userIDs.size());

        while(!userIDs.isEmpty()) {
            if(ids == null) {
                ids = String.valueOf(userIDs.get(0));
                userIDs.remove(0);
            } else {
                ids = ids + "," + String.valueOf(userIDs.get(0));
                userIDs.remove(0);
            }
        }

        downloader = new RequestHandler(this, "https://api.twitter.com/1.1/users/lookup.json?user_id=" + ids, RequestHandler.GET_REQUEST);
        downloader.execute();
    }

    /** Set the list with user id's to match the JSON result String */
    public void setFriends(String result) {
        JsonParser parser = new JsonParser(result);
        if (result != null) {
            JSONArray idArray = parser.getArray("ids");
            for (int i = 0; i < idArray.length(); i++) {
                try {
                    if (!friendIds.contains(idArray.get(i))) {
                        friendIds.add(idArray.getString(i));
                        Log.w("testing", "Added to friends id: " + idArray.getString(i));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /** Set the list of friend names with the given json String */
    public void setFriendNames(String result) {
        friendNames.clear();
        JsonParser parser = new JsonParser(result);
        if (result != null) {
            JSONArray userArray = parser.getArray("users");
            for (int i = 0; i < userArray.length(); i++) {
                try {
                    JSONObject user = (JSONObject) userArray.get(i);
                    if (!friendNames.contains(user.getString("screen_name"))) {
                        friendNames.add(user.getString("screen_name"));
                        Log.w("testing", "Added to friend names: " + user.getString("screen_name"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        setChanged();
        notifyObservers();
    }

    /** Set the list of follower names with the given json String */
    public void setFollowerNames(String result) {
        followerNames.clear();
        JsonParser parser = new JsonParser(result);
        if (result != null) {
            JSONArray userArray = parser.getArray("users");
            for (int i = 0; i < userArray.length(); i++) {
                try {
                    JSONObject user = (JSONObject) userArray.get(i);
                    if (!followerNames.contains(user.getString("screen_name"))) {
                        followerNames.add(user.getString("screen_name"));
                        Log.w("testing", "Added to follower names: " + user.getString("screen_name"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        setChanged();
        notifyObservers();
    }

    /**********************************************************************************/
    /**************************** MISCELLANEOUS METHODS *******************************/
    /**********************************************************************************/

    /** Checks if this model already contains a instance of the user with the given id_str */
    public User userExists(String id_str) {
        for (User u : users) {
            if (u.getId().equals(id_str)) {
                return u;
            }
        }
        return null;
    }

    /** Clear the saved tweets inside this model */
    public void clearTweets() {
        tweets.clear();
        refresh();
    }

    /** Notify the observers of this model that something has changed */
    public void refresh() {
        setChanged();
        notifyObservers();
    }

    /** Call refresh when something has changed */
    @Override
    public void update(Observable observable, Object data) {
        refresh();
    }
}
