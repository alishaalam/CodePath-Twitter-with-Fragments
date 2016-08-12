package com.codepath.apps.twitterville.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.twitterville.R;
import com.codepath.apps.twitterville.adapters.TweetAdapter;
import com.codepath.apps.twitterville.fragments.ComposeTweetDialogFragment;
import com.codepath.apps.twitterville.helper.EndlessRecyclerViewScrollListener;
import com.codepath.apps.twitterville.helper.FragmentUtil;
import com.codepath.apps.twitterville.helper.IViewHolderClickedListener;
import com.codepath.apps.twitterville.models.Tweet;
import com.codepath.apps.twitterville.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class TimeLineActivity extends AppCompatActivity implements ComposeTweetDialogFragment.OnTweetPostedListener {

    private static final String TAG = TimeLineActivity.class.getSimpleName();
    private TwitterClient client;
    TweetAdapter tweetAdapter;
    ArrayList<Tweet> tweetsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeContainer;
    private static long MAX_ID = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Set the title
        TextView tv = (TextView) findViewById(R.id.tv_title_custom_font);
        tv.setText(R.string.title_activity_time_line);
        int titleColor = ContextCompat.getColor(this, R.color.colorAccent);
        tv.setTextColor(titleColor);

        //Set the compose tweet button
        FloatingActionButton btn_compose_tweet = (FloatingActionButton) findViewById(R.id.btn_compose_tweet);
        if (btn_compose_tweet != null) {
            btn_compose_tweet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentUtil.showComposeTweetDialog(getSupportFragmentManager());

                }
            });
        }

        client = TwitterApplication.getRestClient(); //get a singleton client
        setupRecyclerView();
        setupSwipeContainer();
        populateTimeLine(MAX_ID); //since_id & max_id are set to 0 for the first call to populate tweets
    }

    private void setupSwipeContainer() {
        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                swipeContainer.setRefreshing(true);
                populateTimeLine(MAX_ID);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }

    private void setupRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.tweet_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        tweetAdapter = new TweetAdapter(this, tweetsList);
        recyclerView.setAdapter(tweetAdapter);
        recyclerView.setHasFixedSize(true);

        tweetAdapter.setIViewHolderClickedListener(new IViewHolderClickedListener() {
            @Override
            public void onProfilePicClicked(View screeName, int position) {
                Tweet tweet = tweetsList.get(position);
                getUserForScreenName(tweet.getUser().getScreenName());
            }

            @Override
            public void onTweetClicked(View t, int position) {
                Tweet tweet = tweetsList.get(position);
                Intent intent = new Intent(TimeLineActivity.this, TweetDetailActivity.class);
                intent.putExtra(TweetDetailActivity.ARG_ITEM, Parcels.wrap(tweet));
                startActivity(intent);

            }
        });

        //Handling endless scrolling
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                populateTimeLine(MAX_ID);
            }
        });

        //Adding on item click handling
        /*recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                    Tweet tweet = tweetsList.get(position);
                    Intent intent = new Intent(TimeLineActivity.this, TweetDetailActivity.class);
                    intent.putExtra(TweetDetailActivity.ARG_ITEM, Parcels.wrap(tweet));
                    startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));*/
    }

    private void populateTimeLine(long max_id) {

        client.getTweetsForHomeTimeLine(max_id, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray jsonResponse) {
                super.onSuccess(statusCode, headers, jsonResponse);
                swipeContainer.setRefreshing(false);
                parseJsonResponseToTweets(jsonResponse);
                Log.d(TAG, "getTweetsForHomeTimeLine Response: " + jsonResponse.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                swipeContainer.setRefreshing(false);
                Log.d(TAG, "getTweetsForHomeTimeLine Failure: " + errorResponse.toString());
                displayErrorResponse();
            }
        });
    }

    private void displayTweets() {
        tweetAdapter.notifyDataSetChanged();
    }

    public void onPostTweet(String tweetBody) {
        client.postTweet(tweetBody, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonResponse) {
                super.onSuccess(statusCode, headers, jsonResponse);
                Log.d(TAG, "onPostTweet Response" + jsonResponse.toString());
                parseJsonResponseToTweets(jsonResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d(TAG, "onPostTweet Failure" + errorResponse.toString());
                displayErrorResponse();
            }
        });
    }


    private void getUserForScreenName(String screenName) {
        client.getUserForScreenName(screenName, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d(TAG, "onProfilePicClicked Response" + response.toString());
                parseJsonResponseToUser(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                displayErrorResponse();
            }
        });
    }

    private void parseJsonResponseToUser(JSONObject response) {
        User user = User.fromUserJSON(response);
        startDetailActivity(user);
    }

    private void startDetailActivity(User user) {
        Intent intent = new Intent(TimeLineActivity.this, ProfileActivity.class);
        intent.putExtra(ProfileActivity.ARG_ITEM, Parcels.wrap(user));
        startActivity(intent);
        Log.d(TAG, "Starting Profile Activity");
    }

    private void parseJsonResponseToTweets(JSONArray jsonResponse) {
        List<Tweet> newList = new ArrayList<>();
        newList.addAll(Tweet.fromJsonArray(jsonResponse));
        MAX_ID = getLowestId(newList);
        tweetsList.addAll(Tweet.fromJsonArray(jsonResponse));
        displayTweets();
    }

    private void parseJsonResponseToTweets(JSONObject jsonResponse) {
        tweetsList.add(0, Tweet.fromJSON(jsonResponse));
        displayTweets();
    }

    private void displayErrorResponse() {
        Toast.makeText(TimeLineActivity.this, "There is a problem in getting the response.", Toast.LENGTH_SHORT).show();
    }

    public static long getLowestId(List<Tweet> list) {
        long min = Long.MAX_VALUE;
        for (Tweet t : list) {
            if (t.getUid() < min)
                min = t.getUid();
        }
        return min;
    }
}
