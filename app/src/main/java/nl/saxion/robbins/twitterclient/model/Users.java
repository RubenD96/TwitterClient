package nl.saxion.robbins.twitterclient.model;

import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * @author Ruben
 * @author Robbin
 *
 *         Class for storing users
 */
public class Users {
    private static Users instance;
    private ArrayList<User> users;

    public static Users getInstance() {
        if(instance == null) {
            instance = new Users();
        }

        return instance;
    }

    /**
     * Constructor for the Users class. Instantiates the users ArrayList
     */
    private Users() {
        users = new ArrayList<>();
    }

    /**
     * Get all users from ArrayList
     * @return the ArrayList users
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * Get a single User based on an id
     * @param id the id of the User
     * @return the User with a given id
     */
    public User getUser(String id) {
        User user = null;

        for (User u : users) {
            if (id.equals(u.getId())) {
                user = u;
            }
        }

        return user;
    }

    /**
     * Check to see if a User with a given id exists and ArrayList users is not 0
     * @param id the id of the User
     * @return whether a User with a given id exists and the ArrayList users is not 0
     */
    public boolean isExistingUser(String id) {
        return users.size() != 0 && getUser(id) != null;
    }

    /**
     * Add a User to the users ArrayList
     * @param jsonObject JSONObject from which the User is created
     */
    public void addUser(JSONObject jsonObject) throws IOException {
        users.add(new User(jsonObject));
    }
}
