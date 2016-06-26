package nl.saxion.robbins.twitterclient.entities.media;

import java.util.Observable;

import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.Bitmap;

import nl.saxion.robbins.twitterclient.model.ImageLoadTask;
import nl.saxion.robbins.twitterclient.model.JsonParser;

/** A media object which is part of a tweet. Contains a picture, video or something else
 *
 * @author Niels Jan */
public class Media extends Observable {

    private String url;
    private int[] indices = new int[2];
    private Bitmap image;

    public Media(JSONObject media) {

        JsonParser parser = new JsonParser(media);
        url = parser.getString("url");

        JSONArray newIndices = parser.getArray("indices");
        JsonParser arrayParser = new JsonParser(newIndices);
        indices = arrayParser.getIndices();
        ImageLoadTask downloader = new ImageLoadTask(this);
        downloader.execute(parser.getString("media_url"));
    }

    public String getText() {
        return url;
    }

    public int[] getIndices() {
        return indices;
    }

    public int getIndexStart() {
        return this.indices[0];
    }

    public int getIndexEnd() {
        return this.indices[1];
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap result) {
        this.image = result;
        setChanged();
        notifyObservers();
    }
}
