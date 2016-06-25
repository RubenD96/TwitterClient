package nl.saxion.robbins.twitterclient.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 *
 */
public class User {
    private String id;
    private String name;
    private String screenName;
    private String description;
    private String profileImageUrl;
    private int followersCount;
    private int friendCount;

    /**
     * Constructor for the User class. Creates a new User based on a JSON object
     * @param jsonObject JSON object from which the user is created
     */
    public User(JSONObject jsonObject) throws IOException {
        try {
            this.id = jsonObject.getString("id_str");
            this.name = jsonObject.getString("name");
            this.screenName = jsonObject.getString("screen_name");
            this.description = jsonObject.getString("description");
            this.profileImageUrl = jsonObject.getString("profile_image_url");
            this.followersCount = jsonObject.getInt("followers_count");
            this.friendCount = jsonObject.getInt("friends_count");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the User id
     * @return the id of a User
     */
    public String getId() {
        return id;
    }

    /**
     * Get the User name
     * @return the name of a User
     */
    public String getName() {
        return name;
    }

    /**
     * Get the User screen name
     * @return the screen name of a User
     */
    public String getScreenName() {
        return screenName;
    }

    /**
     * Get the User description
     * @return the description of a User
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the User profile image url
     * @return the url of the profile image of the User
     */
    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    /**
     * Get the User followers
     * @return number of followers
     */
    public int getFollowersCount() {
        return followersCount;
    }

    /**
     * Get the User friends
     * @return number of friends
     */
    public int getFriendsCount() {
        return friendCount;
    }
}
