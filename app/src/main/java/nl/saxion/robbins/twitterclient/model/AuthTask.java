package nl.saxion.robbins.twitterclient.model;

import android.os.AsyncTask;
import android.util.Base64;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Ruben on 6/17/2016.
 */
public class AuthTask extends AsyncTask<String, Void, Void> {
// Service.requesttoken
    @Override
    protected Void doInBackground(String... params) {
        // Prepare request
        try {
            URL url = new URL("https://api.twitter.com/oauth2/token");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            // Encode API key and secret
            String API_KEY = "jlKDxCHl89Qjz1rJMEVGeDqP8";
            String API_SECRET = "roMZVAfGnAov9m4vdsHrq7gzPuW8dW1knHMOlfdABkNOFbBkLw";
            String CHARSET_UTF_8 = "UTF-8";
            String authString = URLEncoder.encode(API_KEY, CHARSET_UTF_8) + ":" + URLEncoder.encode(API_SECRET, CHARSET_UTF_8);

            // Apply Base64 encoding on the encoded string
            String authStringBase64 = Base64.encodeToString(authString.getBytes(CHARSET_UTF_8), Base64.NO_WRAP);

            // Set headers
            conn.setRequestProperty("Authorization", "Basic " + authStringBase64);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

            // Set body
            conn.setDoOutput(true);
            byte[] body = "grant_type=client_credentials".getBytes("UTF-8");
            conn.setFixedLengthStreamingMode(body.length);
            BufferedOutputStream os = new BufferedOutputStream(conn.getOutputStream());
            os.write(body);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
