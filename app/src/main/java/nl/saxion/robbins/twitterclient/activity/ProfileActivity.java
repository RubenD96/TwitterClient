package nl.saxion.robbins.twitterclient.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import nl.saxion.robbins.twitterclient.R;
import nl.saxion.robbins.twitterclient.model.RequestHandler;
import nl.saxion.robbins.twitterclient.model.TweetAdapter;
import nl.saxion.robbins.twitterclient.model.TwitterApplication;
import nl.saxion.robbins.twitterclient.model.TwitterModel;

public class ProfileActivity extends AppCompatActivity {

    private TwitterModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        model = TwitterApplication.getModel();

        TweetAdapter tweetAdapter = new TweetAdapter(this, R.id.lv_tweets, model.getTweets());
        ListView lvTweets = (ListView) findViewById(R.id.lv_tweets);
        lvTweets.setAdapter(tweetAdapter);

        Bundle extras = getIntent().getExtras();
        String screenName = extras.getString("screen_name");

        RequestHandler downloader;
        downloader = new RequestHandler(model, "https://api.twitter.com/1.1/users/show.json?screen_name=" + screenName, RequestHandler.GET_REQUEST);
        downloader.execute();

        ImageView ivProfileImage = (ImageView) findViewById(R.id.iv_profile_image);

        TextView tvName = (TextView) findViewById(R.id.tv_name);

        TextView tvScreenName = (TextView) findViewById(R.id.tv_screen_name);

        TextView tvFollowingCount = (TextView) findViewById(R.id.tv_following_count);

        tvFollowingCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: User meegeven?
                Intent intent = new Intent(getBaseContext(), FollowingActivity.class);
                startActivity(intent);
            }
        });

        TextView tvFollowersCount = (TextView) findViewById(R.id.tv_followers_count);

        tvFollowersCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: User meegeven?
                Intent intent = new Intent(getBaseContext(), FollowersActivity.class);
                startActivity(intent);
            }
        });
    }
}
