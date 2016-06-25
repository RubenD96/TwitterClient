package nl.saxion.robbins.twitterclient.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;

import com.github.scribejava.core.oauth.OAuthService;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import nl.saxion.robbins.twitterclient.R;
import nl.saxion.robbins.twitterclient.model.AuthManager;
import nl.saxion.robbins.twitterclient.model.RequestHandler;
import nl.saxion.robbins.twitterclient.model.TweetAdapter;
import nl.saxion.robbins.twitterclient.model.TwitterApplication;
import nl.saxion.robbins.twitterclient.model.TwitterModel;

public class MainActivity extends AppCompatActivity {

    private AutoCompleteTextView actvSearch;
    private Button btnSearch;
    private TweetAdapter tweetAdapter;
    private ListView lvTweets;
    private TwitterModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model = TwitterApplication.getModel();

        RequestHandler downloader;
        // downloader = new RequestHandler("https://api.twitter.com/1.1/account/verify_credentials.json", RequestHandler.GET_REQUEST);
        downloader = new RequestHandler(model, "https://api.twitter.com/1.1/statuses/home_timeline.json", RequestHandler.GET_REQUEST);
        downloader.execute();

        tweetAdapter = new TweetAdapter(this, R.id.lv_tweets, model.getTweets());
        lvTweets = (ListView) findViewById(R.id.lv_tweets);
        lvTweets.setAdapter(tweetAdapter);

        actvSearch = (AutoCompleteTextView) findViewById(R.id.actv_search);

        btnSearch = (Button) findViewById(R.id.btn_search);

        btnSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (actvSearch.getText() != null && actvSearch.length() > 2) {
                    model.clearTweets();
                    startSearch(actvSearch.getText().toString());

                    tweetAdapter.notifyDataSetChanged();

                    InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void startSearch(String searchClause) {
        RequestHandler downloader;
        try {
            String searchUrl = "https://api.twitter.com/1.1/search/tweets.json?q=" + URLEncoder.encode(searchClause, "UTF-8");
            downloader = new RequestHandler(model, searchUrl, RequestHandler.GET_REQUEST);
            downloader.execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}