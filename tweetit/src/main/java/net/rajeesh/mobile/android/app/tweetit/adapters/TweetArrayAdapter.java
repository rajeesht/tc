package net.rajeesh.mobile.android.app.tweetit.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.rajeesh.mobile.android.app.tweetit.R;
import net.rajeesh.mobile.android.app.tweetit.models.Tweet;
import net.rajeesh.mobile.android.app.tweetit.models.User;

import java.util.ArrayList;

/**
 * Created by rtripathi on 12/13/15.
 */
public class TweetArrayAdapter extends ArrayAdapter<Tweet> {


    public interface ProfileImageViewListener {
        void onImageClick(User user);
    }

    private ProfileImageViewListener profileImageViewListener;

    public void setProfileImageListener(ProfileImageViewListener listener) {
        this.profileImageViewListener = listener;
    }

    public TweetArrayAdapter(Context context, ArrayList<Tweet> todoItems) {
        super(context, android.R.layout.simple_list_item_1, todoItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Tweet tweet = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }

        ImageView ivProfile = (ImageView) convertView.findViewById(R.id.ivProfile);
        TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
        TextView tvRelativeTime = (TextView) convertView.findViewById(R.id.tvRelativeTime);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b>@");
        stringBuilder.append(tweet.getUser().getScreenName());
        stringBuilder.append("</b> (");
        stringBuilder.append(tweet.getUser().getName());
        stringBuilder.append(")");

        tvUsername.setText(Html.fromHtml(stringBuilder.toString()));
        tvRelativeTime.setText(tweet.getRelativeTimeAgo());
        tvBody.setText(tweet.getBody());

        ivProfile.setImageResource(android.R.color.transparent);
        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profileImageViewListener != null) {
                    profileImageViewListener.onImageClick(tweet.getUser());
                }
            }
        });
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfile);

        return convertView;
    }

}
