package nl.saxion.robbins.twitterclient.activity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;

import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuthService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;

import nl.saxion.robbins.twitterclient.R;
import nl.saxion.robbins.twitterclient.model.AuthManager;
import nl.saxion.robbins.twitterclient.model.RequestHandler;
import nl.saxion.robbins.twitterclient.model.TweetAdapter;
import nl.saxion.robbins.twitterclient.model.Tweets;

public class MainActivity extends AppCompatActivity {

    private OAuthService authService = AuthManager.getInstance().getService();
    private AutoCompleteTextView actvSearch;
    private Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

        final TweetAdapter tweetAdapter = new TweetAdapter(this, R.id.lv_tweets, Tweets.getInstance().getTweets());
        final ListView lvTweets = (ListView) findViewById(R.id.lv_tweets);
        lvTweets.setAdapter(tweetAdapter);
        Tweets.getInstance().clearTweets();

        actvSearch = (AutoCompleteTextView) findViewById(R.id.actv_search);
        btnSearch = (Button) findViewById(R.id.btn_search);

        btnSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (actvSearch.getText() != null && actvSearch.length() > 2) {
                    startSearch(actvSearch.getText().toString());
                }
            }
        });

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestHandler downloader;
                // downloader = new RequestHandler("https://api.twitter.com/1.1/account/verify_credentials.json", RequestHandler.GET_REQUEST);
                downloader = new RequestHandler("https://api.twitter.com/1.1/statuses/user_timeline.json", RequestHandler.GET_REQUEST);
                downloader.execute();
                tweetAdapter.notifyDataSetChanged();
            }
        });


    }

    private void startSearch(String searchClause) {
        RequestHandler downloader;
        try {
            String searchUrl = "https://api.twitter.com/1.1/search/tweets.json?q=" + URLEncoder.encode(searchClause, "UTF-8");
            downloader = new RequestHandler(searchUrl, RequestHandler.GET_REQUEST);
            downloader.execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads an asset file and returns a string with the full contents.
     *
     * @param filename The filename of the file to read.
     * @return The contents of the file.
     * @throws IOException If file could not be found or not read.
     */
    private String readAssetIntoString(String filename) throws IOException {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            InputStream is = getAssets().open(filename, AssetManager.ACCESS_BUFFER);
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}