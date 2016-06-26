package nl.saxion.robbins.twitterclient.model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import nl.saxion.robbins.twitterclient.R;
import nl.saxion.robbins.twitterclient.activity.ProfileActivity;

public class FriendFollowerAdapter extends ArrayAdapter<User> implements Observer {

    private ViewHolder holder;
    private User user;
    private TwitterModel model;

    public FriendFollowerAdapter(Context context, List<User> objects, TwitterModel model) {
        super(context, 0, objects);
        this.model = model;
    }

    @Override
    public void update(Observable observable, Object data) {
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.friend_follower, parent, false);

            holder = new ViewHolder();
            holder.ivProfileImage = (ImageView) convertView.findViewById(R.id.iv_profile_image);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvScreenName = (TextView) convertView.findViewById(R.id.tv_screen_name);
            holder.tvDescription = (TextView) convertView.findViewById(R.id.tv_description);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        user = getItem(position);

        new ImageLoadTask(user.getProfileImageUrl(), holder.ivProfileImage).execute();
        holder.ivProfileImage.setOnClickListener(new FriendFollowerOnClickListener(position));

        holder.tvName.setText(user.getName());
        holder.tvScreenName.setText(user.getScreenName());
        holder.tvDescription.setText(user.getDescription());

        return convertView;
    }

    static class ViewHolder {
        ImageView ivProfileImage;
        TextView tvName;
        TextView tvScreenName;
        TextView tvDescription;
    }

    private class FriendFollowerOnClickListener implements View.OnClickListener {
        private int position;

        public FriendFollowerOnClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            model.setUser(model.getUsers().get(position));

            Intent intent = new Intent(getContext(), ProfileActivity.class);
            getContext().startActivity(intent);
        }
    }
}
