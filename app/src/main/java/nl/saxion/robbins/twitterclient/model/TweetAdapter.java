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

/**
 * @author Ruben
 * @author Robbin
 *
 *         TweetAdapter for a listview
 */
public class TweetAdapter extends ArrayAdapter<Tweet> implements Observer {

    private ViewHolder holder;
    private Tweet tweet;
    private TwitterModel model;

    public TweetAdapter(Context context, ArrayList<Tweet> objects, TwitterModel model) {
        super(context, 0, objects);
        this.model = model;
    }

    /**
     * Update method gets executed as soon as an Observer sees change or when NotifyAllAdapters
     * gets called.
     * @param observable
     * @param data
     */
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

        holder.ivProfileImage.setImageBitmap(tweet.getUser().getPicture());
        holder.ivProfileImage.setOnClickListener(new TweetOnClickListener(position));

        holder.tvName.setText(tweet.getUser().getName());
        holder.tvScreenName.setText("@" + tweet.getUser().getScreenName());
        holder.tvText.setText(tweet.getText());

        return convertView;
    }

    /**
     * Viewholder for this adapter
     */
    static class ViewHolder {
        ImageView ivProfileImage;
        TextView tvName;
        TextView tvScreenName;
        TextView tvText;
    }

    private class TweetOnClickListener implements View.OnClickListener {
        private int position;

        public TweetOnClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            model.setUser(model.getTweets().get(position).getUser());
            // model.setUser(tweet.getUser());
            System.out.println(tweet.getText());

            Intent intent = new Intent(getContext(), ProfileActivity.class);
            getContext().startActivity(intent);
        }
    }
}
