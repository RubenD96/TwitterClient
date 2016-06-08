package nl.saxion.robbins.twitterclient.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import nl.saxion.robbins.twitterclient.R;

public class TweetAdapter extends ArrayAdapter<Tweet> {
    public TweetAdapter(Context context, int resource, ArrayList<Tweet> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.tweet, parent, false);
        }

        Tweet tweet = Tweets.getInstance().getTweets().get(position);

        ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.iv_profile_image);
        new ImageLoadTask(tweet.getUser().getProfileImageUrl(), ivProfileImage).execute();

        TextView tvName = (TextView) convertView.findViewById(R.id.tv_name);
        tvName.setText(tweet.getUser().getName());

        TextView tvScreenName = (TextView) convertView.findViewById(R.id.tv_screen_name);
        tvScreenName.setText(tweet.getUser().getScreenName());

        TextView tvText = (TextView) convertView.findViewById(R.id.tv_text);
        tvText.setText(tweet.getText());

        return convertView;
    }

    /*public static Bitmap getBitmapFromUrl(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }*/
}
