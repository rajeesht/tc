package net.rajeesh.mobile.android.app.tweetit.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import net.rajeesh.mobile.android.app.tweetit.R;
import net.rajeesh.mobile.android.app.tweetit.TwitterApplication;
import net.rajeesh.mobile.android.app.tweetit.TwitterClient;
import net.rajeesh.mobile.android.app.tweetit.models.User;

import org.apache.http.Header;
import org.json.JSONObject;

public class UserProfileFragment extends Fragment {
    private TwitterClient client;

    public static UserProfileFragment newInstance(String screenName) {
        UserProfileFragment fragment = new UserProfileFragment();
        Bundle args = new Bundle();
        args.putString("screen_name", screenName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        client.getUserDetails(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                User user = User.fromJSON(response);
                TextView tvUsername = (TextView) getActivity().findViewById(R.id.tvUsername);
                TextView tvTagline = (TextView) getActivity().findViewById(R.id.tvTagline);
                TextView tvFollowers = (TextView) getActivity().findViewById(R.id.tvFollowers);
                TextView tvfollowing = (TextView) getActivity().findViewById(R.id.tvFollowing);
                ImageView ivProfile = (ImageView) getActivity().findViewById(R.id.ivProfile);

                tvUsername.setText(user.getScreenName());
                tvTagline.setText(user.getTagline());
                tvFollowers.setText(user.getFollowers() + " Followers");
                tvfollowing.setText(user.getFollowing() + " Following");
                Picasso.with(getContext()).load(user.getProfileImageUrl()).into(ivProfile);
            }
        });
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }
}
