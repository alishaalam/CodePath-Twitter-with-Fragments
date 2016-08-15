package com.codepath.apps.twitterville.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.twitterville.R;
import com.codepath.apps.twitterville.adapters.TweetAdapter;
import com.codepath.apps.twitterville.models.Tweet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alishaalam on 8/12/16.
 */
public class TweetListFragment extends Fragment {

    private static final String TAG = TweetListFragment.class.getSimpleName();
    TweetAdapter tweetAdapter;
    ArrayList<Tweet> mTweetsList;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeContainer;
    LinearLayoutManager linearLayoutManager;
    private static long MAX_ID = 0;

    //Creation lifecycle event
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTweetsList = new ArrayList<>();
        tweetAdapter = new TweetAdapter(getActivity(), mTweetsList);
    }

    //Inflation logic
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragments_tweets_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.tweet_list);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupRecyclerView();
    }

    private void setupRecyclerView() {

        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(tweetAdapter);
        recyclerView.setHasFixedSize(true);
    }

    public void addAll(List items){
        mTweetsList.addAll(items);
        tweetAdapter.addAll(items);
    }

    public Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }
        return false;
    }

    public void showConnectivityErrorMessages() {
        Snackbar snackBar;
        if (!isNetworkAvailable()) {
            snackBar = Snackbar.make(recyclerView, R.string.no_connectivity_text, Snackbar.LENGTH_INDEFINITE);
            snackBar.setAction("Dismiss", v -> {
                snackBar.dismiss();
            });
            snackBar.setActionTextColor(Color.WHITE).show();
        } else if (!isOnline()) {
            snackBar = Snackbar.make(recyclerView, R.string.no_internet_text, Snackbar.LENGTH_INDEFINITE);
            snackBar.setAction("Dismiss", v -> {
                snackBar.dismiss();
            });
            snackBar.setActionTextColor(Color.WHITE).show();
        }
    }

    public void displayErrorResponse() {
        Snackbar snackBar = Snackbar.make(recyclerView, R.string.error_in_response, Snackbar.LENGTH_INDEFINITE);
        snackBar.setAction("Dismiss", v -> {
            snackBar.dismiss();
        });
        snackBar.setActionTextColor(Color.WHITE).show();

    }
}
