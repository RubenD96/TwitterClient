package nl.saxion.robbins.twitterclient.model;

import android.app.Application;

/** Application class to make sure there is one instance of the model accessible throughout the app. */
public class TwitterApplication extends Application {

    private static TwitterModel model = new TwitterModel();

    public static TwitterModel getModel() {
        return model;
    }
}
