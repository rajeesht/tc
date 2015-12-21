package net.rajeesh.mobile.android.app.tweetit.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import net.rajeesh.mobile.android.app.tweetit.EndlessScrollListener;
import net.rajeesh.mobile.android.app.tweetit.R;
import net.rajeesh.mobile.android.app.tweetit.activities.ProfileActivity;
import net.rajeesh.mobile.android.app.tweetit.adapters.TweetArrayAdapter;
import net.rajeesh.mobile.android.app.tweetit.models.Tweet;
import net.rajeesh.mobile.android.app.tweetit.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rtripathi on 12/17/15.
 */
public class TweetsListFragment extends Fragment {
    private TweetArrayAdapter tweetArrayAdapter;
    private ArrayList<Tweet> tweetArrayList;
    private ListView lvTweets;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tweets_list, container, false);
        lvTweets = (ListView) view.findViewById(R.id.lvTweets);
        lvTweets.setAdapter(tweetArrayAdapter);
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                // populateTimeline();
                // or customLoadMoreDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweetArrayList = new ArrayList<>();
        tweetArrayAdapter = new TweetArrayAdapter(getActivity(), tweetArrayList);
        tweetArrayAdapter.setProfileImageListener(new TweetArrayAdapter.ProfileImageViewListener() {
            @Override
            public void onImageClick(User user) {
                Intent i = new Intent(getContext(), ProfileActivity.class);
                i.putExtra("user", user);
                startActivity(i);
            }
        });
    }

    public void addAll(List<Tweet> tweetList) {
        tweetArrayAdapter.clear();
        tweetArrayAdapter.addAll(tweetList);
        tweetArrayAdapter.notifyDataSetChanged();
    }
}
