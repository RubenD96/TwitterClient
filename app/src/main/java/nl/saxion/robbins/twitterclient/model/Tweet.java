package nl.saxion.robbins.twitterclient.model;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import nl.saxion.robbins.twitterclient.entities.Hashtag;
import nl.saxion.robbins.twitterclient.entities.URL;
import nl.saxion.robbins.twitterclient.entities.UserMention;

/**
 * @author Ruben
 * @author Robbin
 *
 *         Stores information about a Tweet
 */
public class Tweet {

    private String userId;
    private String text;
    private SpannableString test;
    private ArrayList<Hashtag> hashtagObjects = new ArrayList<>();
    private ArrayList<URL> URLObjects = new ArrayList<>();
    private ArrayList<UserMention> userMentionObjects = new ArrayList<>();

    public Tweet(JSONObject jsonObject) throws ParseException, JSONException, IOException {

        JsonParser parser = new JsonParser(jsonObject);

        String tempId = parser.getObject("user").getString("id_str");

        if(!Users.getInstance().isExistingUser(tempId)) {
            Users.getInstance().addUser(parser.getObject("user"));
            userId = tempId;
        } else {
            this.userId = Users.getInstance().getUser(tempId).getId();
        }

        this.text = parser.getString("text");
        JSONObject entities = parser.getObject("entities");

        //hashtags
        JSONArray hashtags = entities.getJSONArray("hashtags");
        for (int i = 0; i < hashtags.length(); i++) {
            hashtagObjects.add(new Hashtag(hashtags.getJSONObject(i)));
        }

        //URLs
        JSONArray URLs = entities.getJSONArray("urls");
        for (int i = 0; i < URLs.length(); i++) {
            URLObjects.add(new URL(URLs.getJSONObject(i)));
        }

        //user mentions
        JSONArray userMentions = entities.getJSONArray("user_mentions");
        for (int i = 0; i < userMentions.length(); i++) {
            userMentionObjects.add(new UserMention(userMentions.getJSONObject(i)));
        }

        SpannableString spanStr = new SpannableString(this.text);

        for (Hashtag hashtag : hashtagObjects) {
            int start = hashtag.getIndices()[0];
            int end = hashtag.getIndices()[1];

            spanStr.setSpan(new ForegroundColorSpan(Color.BLUE), start, end, 0);
        }

        for (URL url : URLObjects) {
            int start = url.getIndices()[0];
            int end = url.getIndices()[1] - 1;

            spanStr.setSpan(new URLSpan(url.getUrl()), start, end, 0);
        }

        for (UserMention userMention : userMentionObjects) {
            int start = userMention.getIndices()[0];
            int end = userMention.getIndices()[1];

            spanStr.setSpan(new ForegroundColorSpan(Color.BLUE), start, end, 0);
        }

        this.test = spanStr;
    }

    public User getUser() {
        return Users.getInstance().getUser(userId);
    }

    public SpannableString getText() {
        return test;
    }
}
