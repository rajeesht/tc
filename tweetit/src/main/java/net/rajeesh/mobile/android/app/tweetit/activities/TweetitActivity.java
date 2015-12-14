package net.rajeesh.mobile.android.app.tweetit.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import net.rajeesh.mobile.android.app.tweetit.R;
import net.rajeesh.mobile.android.app.tweetit.TwitterApplication;
import net.rajeesh.mobile.android.app.tweetit.TwitterClient;

import org.apache.http.Header;
import org.json.JSONObject;

public class TweetitActivity extends AppCompatActivity {

    private TextView tvCharsLeft;
    private EditText etNewTweet;
    private TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweetit);
        client = TwitterApplication.getRestClient();
        tvCharsLeft = (TextView) findViewById(R.id.tvCharsLeft);
        etNewTweet = (EditText) findViewById(R.id.etNewTweet);

        etNewTweet.setFilters(new InputFilter[] {new InputFilter.LengthFilter(140)});
        etNewTweet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int charsLeft = 140 - s.length();
                tvCharsLeft.setText(Integer.toString(charsLeft));
            }
        });
    }

    public void doTweeting(View view) {
        client.postStatus(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(getApplicationContext(), "Tweet failed", Toast.LENGTH_LONG);
            }
        }, etNewTweet.getText().toString());
    }

}
