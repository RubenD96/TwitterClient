package nl.saxion.robbins.twitterclient.entities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by robbinschonewille on 25-05-16.
 */
public class URL {

    private String display_url;
    private String expanded_url;
    private int[] indices = new int[2];
    private String url;

    public URL(JSONObject URL) {
        try {
            display_url = URL.getString("display_url");
            expanded_url = URL.getString("expanded_url");
            JSONArray jsonIndices = URL.getJSONArray("indices");
            indices[0] = jsonIndices.getInt(0);
            indices[1] = jsonIndices.getInt(1);
            url = URL.getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getDisplay_url() {
        return display_url;
    }

    public String getExpanded_url() {
        return expanded_url;
    }

    public int[] getIndices() {
        return indices;
    }

    public String getUrl() {
        return url;
    }
}
