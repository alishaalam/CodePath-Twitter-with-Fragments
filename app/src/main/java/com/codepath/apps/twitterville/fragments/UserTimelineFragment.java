package com.codepath.apps.twitterville.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

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

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by alishaalam on 8/12/16.
 */
public class UserTimelineFragment extends TweetListFragment implements ComposeTweetDialogFragment.OnTweetPostedListener  {

    private static final String TAG = UserTimelineFragment.class.getSimpleName();
    private TwitterClient client;
    public String mScreenName;

    public String getScreenName() {
        return mScreenName;
    }

    public void setScreenName(String mScreenName) {
        this.mScreenName = mScreenName;
    }


    public static UserTimelineFragment newInstance(String screenName) {
        UserTimelineFragment userTimelineFragment = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putString("screenName", screenName);
        userTimelineFragment.setArguments(args);
        return userTimelineFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient(); //get a singleton client

        setScreenName(getArguments().getString("screenName"));

        if(isNetworkAvailable() && isOnline()) {
            populateUserTimeLine(mScreenName);
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
                if(isNetworkAvailable() && isOnline()) {
                    getUserForScreenName(tweet.getUser().getScreenName());
                }else {
                    showConnectivityErrorMessages();
                }

            }

            @Override
            public void onTweetClicked(View t, int position) {
                Tweet tweet = mTweetsList.get(position);
                Intent intent = new Intent(getContext(), TweetDetailActivity.class);
                intent.putExtra(TweetDetailActivity.ARG_ITEM, Parcels.wrap(tweet));
                getContext().startActivity(intent);
            }
        });

        //Handling endless scrolling
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                if(isNetworkAvailable() && isOnline()) {
                    populateUserTimeLine(mScreenName);
                }else {
                    showConnectivityErrorMessages();
                }
            }
        });
    }



    private void setupSwipeContainer() {

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(() -> {
            // Your code to refresh the list here.
            // Make sure you call swipeContainer.setRefreshing(false)
            // once the network request has completed successfully.
            swipeContainer.setRefreshing(true);
            if(isNetworkAvailable() && isOnline()) {
                populateUserTimeLine(mScreenName);
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

    private void populateUserTimeLine(String screenName) {
        client.getTweetsForUser(screenName, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray jsonResponse) {
                swipeContainer.setRefreshing(false);
                parseJsonResponseToTweets(jsonResponse);
                Log.d(TAG, "getTweetsForUser Response: " + jsonResponse.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                swipeContainer.setRefreshing(false);
                Log.d(TAG, "getTweetsForUser Failure: " + errorResponse);
                displayErrorResponse();
            }
        });
    }

    private void parseJsonResponseToTweets(JSONArray jsonResponse) {
        List<Tweet> newList = new ArrayList<>();
        newList.addAll(Tweet.fromJsonArray(jsonResponse));
        addAll(Tweet.fromJsonArray(jsonResponse));
        displayTweets();
    }

    private void parseJsonResponseToTweets(JSONObject jsonResponse) {
        addAll((List<Tweet>) Tweet.fromJSON(jsonResponse));
        displayTweets();
    }

    private void displayTweets() {
        tweetAdapter.notifyDataSetChanged();
    }

    public void onPostTweet(String tweetBody) {
        client.postTweet(tweetBody, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonResponse) {
                Log.d(TAG, "onPostTweet Response" + jsonResponse.toString());
                parseJsonResponseToTweets(jsonResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d(TAG, "onPostTweet Failure" + errorResponse.toString());
                displayErrorResponse();
            }
        });
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
        startDetailActivity(user);
    }

    private void startDetailActivity(User user) {
        Intent intent = new Intent(getActivity(), ProfileActivity.class);
        intent.putExtra(ProfileActivity.ARG_ITEM, Parcels.wrap(user));
        startActivity(intent);
        Log.d(TAG, "Starting Profile Activity");
    }


}
