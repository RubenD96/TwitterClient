package nl.saxion.robbins.twitterclient.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import nl.saxion.robbins.twitterclient.R;
import nl.saxion.robbins.twitterclient.model.RequestHandler;
import nl.saxion.robbins.twitterclient.model.TweetAdapter;
import nl.saxion.robbins.twitterclient.model.TwitterApplication;
import nl.saxion.robbins.twitterclient.model.TwitterModel;

/**
 * @author Ruben
 * @author Robbin
 *
 *         MainActivty contains; Timeline, Search function and Tweet function
 *         Uses an observer to keep the data up to date.
 */
public class MainActivity extends AppCompatActivity {

    private Button btnSearch;
    private ListView lvTweets;
    private TwitterModel model;
    private Button btnTweet;
    private EditText etTweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model = ((TwitterApplication) getApplication()).getModel();
        TweetAdapter adapter = new TweetAdapter(this, model.getTweets(), model);
        lvTweets = (ListView) findViewById(R.id.lv_tweets);
        lvTweets.setAdapter(adapter);
        model.addObserver(adapter);

        //update home timeline
        updateHomeTimeline();

        btnSearch = (Button) findViewById(R.id.btn_search);

        /**
         * Goes to a new activity if Search! is clicked
         */
        btnSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(searchIntent);
            }
        });

        etTweet = (EditText) findViewById(R.id.et_tweet);
        btnTweet = (Button) findViewById(R.id.btn_tweet);

        /**
         * Post a tweet!
         */
        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etTweet.getText().toString().isEmpty()) {
                    RequestHandler poster = new RequestHandler(model, "https://api.twitter.com/1.1/statuses/update.json", RequestHandler.POST_REQUEST, etTweet.getText().toString());
                    poster.execute();
                    updateHomeTimeline();
                    etTweet.getText().clear();
                    InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                } else {
                    System.out.println("No input, nothing to send!");
                }
            }
        });

    }

    /**
     * If an user gets back to MainActivity after leaveing it, update the timeline
     */
    @Override
    protected void onResume() {
        super.onResume();
        updateHomeTimeline();
    }

    /**
     * Updates the home timeline with a get request from twitter
     */
    private void updateHomeTimeline() {
        RequestHandler downloader;
        downloader = new RequestHandler(model, "https://api.twitter.com/1.1/statuses/home_timeline.json", RequestHandler.GET_REQUEST);
        downloader.execute();
    }
}