package nl.saxion.robbins.twitterclient.model;

import android.graphics.Bitmap;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Observable;

/**
 * @author Ruben
 * @author Robbin
 *
 *         User class that stores information about a user
 */
public class User extends Observable {
    private String id;
    private String name;
    private String screenName;
    private String description;
    private int followersCount;
    private int friendCount;
    private boolean friend;
    private Bitmap picture;

    /**
     * Constructor for the User class. Creates a new User based on a JSON object
     *
     * @param jsonObject JSON object from which the user is created
     */
    public User(JSONObject jsonObject) {
        JsonParser parser = new JsonParser(jsonObject);
        this.id = parser.getString("id_str");
        this.name = parser.getString("name");
        this.screenName = parser.getString("screen_name");
        this.description = parser.getString("description");
        this.followersCount = parser.getInt("followers_count");
        this.friendCount = parser.getInt("friends_count");
        this.friend = parser.getBoolean("following");
        ImageLoadTask downloader = new ImageLoadTask(this);
        String imageURL = parser.getString("profile_image_url");
        downloader.execute(imageURL);
    }

    public boolean isFriend() {
        return friend;
    }

    /**
     * Get the User id
     *
     * @return the id of a User
     */
    public String getId() {
        return id;
    }

    /**
     * Get the User name
     *
     * @return the name of a User
     */
    public String getName() {
        return name;
    }

    /**
     * Get the User screen name
     *
     * @return the screen name of a User
     */
    public String getScreenName() {
        return screenName;
    }

    /**
     * Get the User description
     *
     * @return the description of a User
     */
    public String getDescription() {
        return description;
    }

    public void setImage(Bitmap image) {
        this.picture = image;
        setChanged();
        notifyObservers();
    }

    public Bitmap getPicture() {
        return picture;
    }

    /**
     * Get the User followers
     *
     * @return number of followers
     */
    public int getFollowersCount() {
        return followersCount;
    }

    /**
     * Get the User friends
     *
     * @return number of friends
     */
    public int getFriendsCount() {
        return friendCount;
    }
}
