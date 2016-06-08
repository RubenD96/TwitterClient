package nl.saxion.robbins.twitterclient.entities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by robbinschonewille on 25-05-16.
 */
public class UserMention {

    private long id;
    private String id_str;
    private int[] indices = new int[2];
    private String name;
    private String screen_name;

    public UserMention(JSONObject userMention) {
        try {
            id = userMention.getLong("id");
            id_str = userMention.getString("id_str");
            JSONArray jsonIndices = userMention.getJSONArray("indices");
            indices[0] = jsonIndices.getInt(0);
            indices[1] = jsonIndices.getInt(1);
            name = userMention.getString("name");
            screen_name = userMention.getString("screen_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public long getId() {
        return id;
    }

    public String getId_str() {
        return id_str;
    }

    public int[] getIndices() {
        return indices;
    }

    public String getName() {
        return name;
    }

    public String getScreen_name() {
        return screen_name;
    }
}
