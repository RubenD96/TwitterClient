package nl.saxion.robbins.twitterclient.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import nl.saxion.robbins.twitterclient.R;
import nl.saxion.robbins.twitterclient.model.RequestHandler;
import nl.saxion.robbins.twitterclient.model.TweetAdapter;
import nl.saxion.robbins.twitterclient.model.TwitterApplication;
import nl.saxion.robbins.twitterclient.model.TwitterModel;

public class SearchActivity extends AppCompatActivity {

    private AutoCompleteTextView actvSearch;
    private ListView lvSearch;
    private Button btnSearch;
    private TwitterModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        actvSearch = (AutoCompleteTextView) findViewById(R.id.actv_search);
        btnSearch = (Button) findViewById(R.id.btn_search);

        model = ((TwitterApplication) getApplication()).getModel();
        model.clearTweets();
        lvSearch = (ListView) findViewById(R.id.lv_search);
        TweetAdapter adapter = new TweetAdapter(this, R.id.lv_tweets, model.getTweets());
        lvSearch.setAdapter(adapter);
        model.addObserver(adapter);

        String searchURL = getIntent().getStringExtra("search");
        if (searchURL != null) {
            hashtagSearch(searchURL);
        }

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actvSearch.getText() != null && actvSearch.length() > 2) {
                    startSearch(actvSearch.getText().toString());

                    InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });

    }

    public void hashtagSearch(String hashtag) {
        startSearch(hashtag);
    }

    /** On back pressed (go back to home screen), clear the tweets */
    @Override
    public void onBackPressed() {
        model.clearTweets();
        super.onBackPressed();
    }

    /** Start a search for the given search clause */
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
