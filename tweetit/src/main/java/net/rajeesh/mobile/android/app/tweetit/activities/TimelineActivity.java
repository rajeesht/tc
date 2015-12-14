package net.rajeesh.mobile.android.app.tweetit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

import net.rajeesh.mobile.android.app.tweetit.EndlessScrollListener;
import net.rajeesh.mobile.android.app.tweetit.R;
import net.rajeesh.mobile.android.app.tweetit.TwitterApplication;
import net.rajeesh.mobile.android.app.tweetit.TwitterClient;
import net.rajeesh.mobile.android.app.tweetit.adapters.TweetArrayAdapter;
import net.rajeesh.mobile.android.app.tweetit.models.Tweet;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TimelineActivity extends AppCompatActivity {

    private TwitterClient client;
    private TweetArrayAdapter tweetArrayAdapter;
    private ArrayList<Tweet> tweetArrayList;
    private ListView lvTweets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        lvTweets = (ListView) findViewById(R.id.lvTweets);
        tweetArrayList = new ArrayList<>();
        tweetArrayAdapter = new TweetArrayAdapter(this, tweetArrayList);
        lvTweets.setAdapter(tweetArrayAdapter);
        client = TwitterApplication.getRestClient();
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.timeline_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_new) {
            Intent i = new Intent(this, TweetitActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void populateTimeline() {
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                tweetArrayAdapter.clear();
                tweetArrayList = Tweet.fromJSONArray(response);
                tweetArrayAdapter.addAll(tweetArrayList);
                tweetArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateTimeline();
    }
}
