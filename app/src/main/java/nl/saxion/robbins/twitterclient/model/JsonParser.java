package nl.saxion.robbins.twitterclient.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/** A parser that can be constructed with either JSONObjects, JSONArrays or Strings. Returns underlying JSONObjects,
 * JSONArrays and Strings.
 *
 * Handles exceptions and provides syntax sugar for model classes.
 *
 * @author Niels Jan */
public class JsonParser {

    /* Instance variables */
	/* Parent Object and parent Array */
    private JSONObject jsonObject;
    private JSONArray jsonArray;

	/* Constructors */
    /** Construct JsonParser from a JSONObject */
    public JsonParser(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    /** Construct JsonParser with a JSONArray */
    public JsonParser(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    /** Construct JsonParser with a String containing JSON */
    public JsonParser(String jsonLine) {
        if (jsonLine != null) {
            try {
                if (jsonLine.startsWith("[")) {
                    this.jsonArray = new JSONArray(jsonLine);
                } else {
                    this.jsonObject = new JSONObject(jsonLine);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

	/* Public methods */
    /** Get the array with a given name from saved JSONObject */
    public JSONArray getArray(String name) {

        JSONArray array = new JSONArray();
        try {
            array = jsonObject.getJSONArray(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array;
    }

    /** Get the object with a given name from saved JSONObject */
    public JSONObject getObject(String name) {

        JSONObject object = new JSONObject();
        try {
            object = jsonObject.getJSONObject(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;
    }

    /** Get the object from the parent JSONArray @ a given position */
    public JSONObject getObject(int position) {

        JSONObject object = new JSONObject();
        try {
            object = jsonArray.getJSONObject(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;
    }

    /** Get a String from the saved JSONObject with a given name */
    public String getString(String name) {
        assert jsonObject != null : "Object is null";
        String result = "";
        try {
            if (jsonObject.has(name)) {
                result = jsonObject.getString(name);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean getBoolean(String name) {
        boolean result = false;
        try {
            if (jsonObject.has(name)) {
                result = jsonObject.getBoolean(name);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    /** Get a int from the saved JSONObject with a given name */
    public int getInt(String name) {
        int result = 0;
        try {
            if (jsonObject.has(name)) {
                result = jsonObject.getInt(name);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    /** Get the indices from the saved JSONArray */
    public int[] getIndices() {
        int[] indices = new int[2];
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                indices[i] = jsonArray.getInt(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return indices;
    }

    /** Set the parent object of this parser */
    public void setObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    /** Set the parent array of this parser */
    public void setArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    /** Get the parent object of this parser */
    public JSONObject getParentObject() {
        return jsonObject;
    }

    /** Get the parent array of this parser */
    public JSONArray getParentArray() {
        return jsonArray;
    }
}
