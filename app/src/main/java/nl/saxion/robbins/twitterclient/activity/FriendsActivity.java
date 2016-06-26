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
 *         FriendsActivity shows a list of users that the clicked user is following.
 *         Uses an observer to keep the data up to date.
 */
public class FriendsActivity extends AppCompatActivity {

    private TwitterModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        model = ((TwitterApplication) getApplication()).getModel();
        FriendFollowerAdapter adapter = new FriendFollowerAdapter(this, model.getUsers(), model);
        ListView lvFriends = (ListView) findViewById(R.id.lv_friends);
        lvFriends.setAdapter(adapter);
        model.addObserver(adapter);

        RequestHandler downloader;
        downloader = new RequestHandler(model, "https://api.twitter.com/1.1/friends/list.json?screen_name=" + model.getUser().getScreenName(), RequestHandler.GET_REQUEST);
        downloader.execute();
    }
}
