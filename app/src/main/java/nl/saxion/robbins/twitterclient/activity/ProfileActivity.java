package nl.saxion.robbins.twitterclient.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import nl.saxion.robbins.twitterclient.R;
import nl.saxion.robbins.twitterclient.model.AuthManager;
import nl.saxion.robbins.twitterclient.model.ImageLoadTask;
import nl.saxion.robbins.twitterclient.model.RequestHandler;
import nl.saxion.robbins.twitterclient.model.TweetAdapter;
import nl.saxion.robbins.twitterclient.model.TwitterApplication;
import nl.saxion.robbins.twitterclient.model.TwitterModel;

public class ProfileActivity extends AppCompatActivity {

    private TwitterModel model;
    private Button btnFriend;
    private boolean follow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        model = ((TwitterApplication) getApplication()).getModel();
        model.clearTweets();

        TweetAdapter tweetAdapter = new TweetAdapter(this, model.getTweets(), model);
        ListView lvTweets = (ListView) findViewById(R.id.lv_tweets);
        lvTweets.setAdapter(tweetAdapter);
        model.addObserver(tweetAdapter);

        RequestHandler downloader;
        downloader = new RequestHandler(model, "https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=" + model.getUser().getScreenName() + "&count=10", RequestHandler.GET_REQUEST);
        downloader.execute();

        ImageView ivProfileImage = (ImageView) findViewById(R.id.iv_profile_image);
        ivProfileImage.setImageBitmap(model.getUser().getPicture());

        TextView tvName = (TextView) findViewById(R.id.tv_name);
        tvName.setText(model.getUser().getName());

        TextView tvScreenName = (TextView) findViewById(R.id.tv_screen_name);
        tvScreenName.setText("@" + model.getUser().getScreenName());

        TextView tvFriendsCount = (TextView) findViewById(R.id.tv_friends_count);
        tvFriendsCount.setText(String.valueOf(model.getUser().getFriendsCount()));

        TextView tvFriends = (TextView) findViewById(R.id.tv_friends);

        TextView tvFollowersCount = (TextView) findViewById(R.id.tv_followers_count);
        tvFollowersCount.setText(String.valueOf(model.getUser().getFollowersCount()));

        TextView tvFollowers = (TextView) findViewById(R.id.tv_followers);

        btnFriend = (Button) findViewById(R.id.btn_friend);
        RequestHandler friendGetter = new RequestHandler(model, "https://api.twitter.com/1.1/friends/list.json?screen_name=" + AuthManager.getInstance().getMainUserScreenName(), RequestHandler.GET_REQUEST);
        friendGetter.execute();
        follow = model.getFriendNames().contains(model.getUser());
        setButtonText();

        tvFriendsCount.setOnClickListener(new FriendsOnClickListener());
        tvFriends.setOnClickListener(new FriendsOnClickListener());
        tvFollowersCount.setOnClickListener(new FollowersOnClickListener());
        tvFollowers.setOnClickListener(new FollowersOnClickListener());
        btnFriend.setOnClickListener(new followOnClickListener());
    }

    private class followOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            follow = !follow;
            setButtonText();
        }
    }

    private class FriendsOnClickListener implements View.OnClickListener
    {
        public void onClick(View v)
        {
            Intent intent = new Intent(getBaseContext(), FriendsActivity.class);
            startActivity(intent);
        }
    }

    private class FollowersOnClickListener implements View.OnClickListener
    {
        public void onClick(View v)
        {
            Intent intent = new Intent(getBaseContext(), FollowersActivity.class);
            startActivity(intent);
        }
    }

    private void setButtonText() {
        if (follow) {
            btnFriend.setText("Unfollow :(");
        } else {
            btnFriend.setText("Follow :)");
        }
    }
}
