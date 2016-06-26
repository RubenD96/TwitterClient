package nl.saxion.robbins.twitterclient.entities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Ruben
 * @author Robbin
 *
 *         Information about the Hashtag entity that a Tweet might contain
 */
public class Hashtag {

    private String text;
    private int[] indices = new int[2];

    public Hashtag(JSONObject hashtag) {
        try {
            text = hashtag.getString("text");
            JSONArray jsonIndices = hashtag.getJSONArray("indices");
            indices[0] = jsonIndices.getInt(0);
            indices[1] = jsonIndices.getInt(1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getText() {
        return text;
    }

    public int[] getIndices() {
        return indices;
    }
}
