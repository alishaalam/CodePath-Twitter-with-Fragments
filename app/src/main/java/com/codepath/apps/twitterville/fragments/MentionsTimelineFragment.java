package com.codepath.apps.twitterville.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.codepath.apps.twitterville.activities.ProfileActivity;
import com.codepath.apps.twitterville.activities.TweetDetailActivity;
import com.codepath.apps.twitterville.activities.TwitterApplication;
import com.codepath.apps.twitterville.activities.TwitterClient;
import com.codepath.apps.twitterville.helper.EndlessRecyclerViewScrollListener;
import com.codepath.apps.twitterville.helper.IViewHolderClickedListener;
import com.codepath.apps.twitterville.models.Tweet;
import com.codepath.apps.twitterville.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

/**
 * Created by alishaalam on 8/12/16.
 */
public class MentionsTimelineFragment extends TweetListFragment {

    private static final String TAG = MentionsTimelineFragment.class.getSimpleName();
    private TwitterClient client;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient(); //get a singleton client
        if(isNetworkAvailable() && isOnline()) {
            populateMentionsTimeLine();
        }else {
            showConnectivityErrorMessages();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupSwipeContainer();

        tweetAdapter.setIViewHolderClickedListener(new IViewHolderClickedListener() {
            @Override
            public void onProfilePicClicked(View screeName, int position) {
                Tweet tweet = mTweetsList.get(position);
                Toast.makeText(getActivity(), "User Pic clicked", Toast.LENGTH_SHORT).show();
                if(isNetworkAvailable() && isOnline()) {
                    getUserForScreenName(tweet.getUser().getScreenName());
                }else {
                    showConnectivityErrorMessages();
                }
            }

            @Override
            public void onTweetClicked(View t, int position) {
                Tweet tweet = mTweetsList.get(position);
                startTweetDetailActivity(tweet);
            }
        });

        //Handling endless scrolling
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                if(isNetworkAvailable() && isOnline()) {
                    populateMentionsTimeLine();
                }else {
                    showConnectivityErrorMessages();
                }
            }
        });
    }


    private void setupSwipeContainer() {

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(() -> {
            // Code to refresh the list here.
            // Call swipeContainer.setRefreshing(false) once the network request has completed successfully.
            swipeContainer.setRefreshing(true);
            if(isNetworkAvailable() && isOnline()) {
                populateMentionsTimeLine();
            }else {
                showConnectivityErrorMessages();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }


    private void populateMentionsTimeLine() {
        client.getTweetsForMentionsLine(new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray jsonResponse) {
                swipeContainer.setRefreshing(false);
                parseJsonResponseToTweets(jsonResponse);
                Log.d(TAG, "getTweetsForHomeTimeLine Response: " + jsonResponse.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                swipeContainer.setRefreshing(false);
                Log.d(TAG, "getTweetsForHomeTimeLine Failure: " + errorResponse.toString());
                displayErrorResponse();
            }
        });
    }


    private void parseJsonResponseToTweets(JSONArray jsonResponse) {
        addAll(Tweet.fromJsonArray(jsonResponse));
        displayTweets();
    }

    private void displayTweets() {
        tweetAdapter.notifyDataSetChanged();
    }



    private void getUserForScreenName(String screenName) {
        client.getUserForScreenName(screenName, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d(TAG, "onProfilePicClicked Response" + response.toString());
                parseJsonResponseToUser(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                displayErrorResponse();
            }
        });
    }

    private void parseJsonResponseToUser(JSONObject response) {
        User user = User.fromUserJSON(response);
        startProfileActivity(user);
    }

    private void startTweetDetailActivity(Tweet tweet) {
        Intent intent = new Intent(getContext(), TweetDetailActivity.class);
        intent.putExtra(TweetDetailActivity.ARG_ITEM, Parcels.wrap(tweet));
        getContext().startActivity(intent);
    }

    private void startProfileActivity(User user) {
        Intent intent = new Intent(getActivity(), ProfileActivity.class);
        intent.putExtra(ProfileActivity.ARG_ITEM, Parcels.wrap(user));
        startActivity(intent);
        Log.d(TAG, "Starting Profile Activity");
    }
}
