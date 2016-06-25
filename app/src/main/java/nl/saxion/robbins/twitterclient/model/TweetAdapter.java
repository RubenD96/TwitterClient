package nl.saxion.robbins.twitterclient.model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import nl.saxion.robbins.twitterclient.R;
import nl.saxion.robbins.twitterclient.activity.ProfileActivity;

public class TweetAdapter extends ArrayAdapter<Tweet> implements Observer {

    private ViewHolder holder;
    private Tweet tweet;
    private AuthManager authManager;

    public TweetAdapter(Context context, int resource, ArrayList<Tweet> objects) {
        super(context, resource, objects);
    }

    @Override
    public void update(Observable observable, Object data) {
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.tweet, parent, false);

            holder = new ViewHolder();
            holder.ivProfileImage = (ImageView) convertView.findViewById(R.id.iv_profile_image);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvScreenName = (TextView) convertView.findViewById(R.id.tv_screen_name);
            holder.tvText = (TextView) convertView.findViewById(R.id.tv_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        tweet = getItem(position);

        new ImageLoadTask(tweet.getUser().getProfileImageUrl(), holder.ivProfileImage).execute();
        holder.ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProfileActivity.class);
                String screenName = tweet.getUser().getScreenName();
                intent.putExtra("screen_name", screenName);
                getContext().startActivity(intent);
            }
        });

        holder.tvName.setText(tweet.getUser().getName());
        holder.tvScreenName.setText(tweet.getUser().getScreenName());
        holder.tvText.setText(tweet.getText());

        return convertView;
    }

    static class ViewHolder {
        ImageView ivProfileImage;
        TextView tvName;
        TextView tvScreenName;
        TextView tvText;
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
