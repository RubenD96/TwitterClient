package nl.saxion.robbins.twitterclient.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import nl.saxion.robbins.twitterclient.R;
import nl.saxion.robbins.twitterclient.model.FriendFollowerAdapter;
import nl.saxion.robbins.twitterclient.model.RequestHandler;
import nl.saxion.robbins.twitterclient.model.TwitterApplication;
import nl.saxion.robbins.twitterclient.model.TwitterModel;

/**
 * @author Ruben
 * @author Robbin
 *
 *         FollowersActivity shows a list of users that the clicked user is being followed by.
 *         Uses an observer to keep the data up to date.
 */
public class FollowersActivity extends AppCompatActivity {

    private TwitterModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);

        model = ((TwitterApplication) getApplication()).getModel();
        FriendFollowerAdapter adapter = new FriendFollowerAdapter(this, model.getUsers(), model);
        ListView lvFollowers = (ListView) findViewById(R.id.lv_followers);
        lvFollowers.setAdapter(adapter);
        model.addObserver(adapter);

        RequestHandler downloader;
        downloader = new RequestHandler(model, "https://api.twitter.com/1.1/followers/ids.json?cursor=-1&screen_name=" + model.getUser().getScreenName() + "&count=5000", RequestHandler.GET_REQUEST);
        downloader.execute();
    }
}