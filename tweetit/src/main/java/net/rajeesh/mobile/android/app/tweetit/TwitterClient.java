package net.rajeesh.mobile.android.app.tweetit;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/*
 *
 * This is the object responsible for communicating with a REST API.
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes:
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 *
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 *
 */
public class TwitterClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
    public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
    public static final String REST_CONSUMER_KEY = "bozHgDqaNgAr8VrkqRr0SRM6M";       // Change this
    public static final String REST_CONSUMER_SECRET = "UuJKFOhhchsrGP98GLtAJ1gi4h0aODZAql1dxrvjN2ba2S4n8f"; // Change this
    public static final String REST_CALLBACK_URL = "oauth://cptweetit"; // Change this (here and in manifest)

    public TwitterClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }

    // HomeTimeline - Get user home timeline
    // GET /statuses/home_timeline.json
    // count=25
    // since_id=1
    public void getHomeTimeline(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("/statuses/home_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count", 25);
        params.put("since_id", 1);
        getClient().get(apiUrl, params, handler);
    }

    // Publish tweet - Compose new tweet
    // POST /statuses/update.json
    // status="this is a tweet"
    public void postStatus(AsyncHttpResponseHandler handler, String statusUpdate) {
        String apiUrl = getApiUrl("/statuses/update.json");
        RequestParams params = new RequestParams();
        params.put("status", statusUpdate);

        getClient().post(apiUrl, params, handler);
    }

    // Tweeter Mentions - Get user home timeline
    // GET /statuses/mentions_timeline.json
    // count=25
    public void getMentionsTimeline(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("/statuses/mentions_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count", 25);
        getClient().get(apiUrl, params, handler);
    }

    // Tweeter Mentions - Get user home timeline
    // GET /statuses/user_timeline.json
    // count=25
    // screem_name=null|screen_name
    public void getUserTimeline(String screenName, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("/statuses/user_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count", 25);
        params.put("screen_name", screenName);
        getClient().get(apiUrl, params, handler);
    }

    // Tweeter Mentions - Get user home timeline
    // GET /account/verify_credentials.json
    // count=25
    public void getUserDetails(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("/account/verify_credentials.json");
        RequestParams params = new RequestParams();
        getClient().get(apiUrl, params, handler);
    }

    /* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
     * 	  i.e getApiUrl("statuses/home_timeline.json");
     * 2. Define the parameters to pass to the request (query or body)
     *    i.e RequestParams params = new RequestParams("foo", "bar");
     * 3. Define the request method and make a call to the client
     *    i.e client.get(apiUrl, params, handler);
     *    i.e client.post(apiUrl, params, handler);
     */
}
