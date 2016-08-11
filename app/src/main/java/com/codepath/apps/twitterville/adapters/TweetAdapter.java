package com.codepath.apps.twitterville.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.codepath.apps.twitterville.R;
import com.codepath.apps.twitterville.models.Tweet;
import com.codepath.apps.twitterville.viewholder.TweetViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by alishaalam on 8/5/16.
 */
public class TweetAdapter extends RecyclerView.Adapter<TweetViewHolder> {

    Context mContext;
    ArrayList<Tweet> mTweetsList = new ArrayList<Tweet>();
    private static final String TAG = TweetAdapter.class.getSimpleName();

    public TweetAdapter(Context mContext, ArrayList<Tweet> mTweetsList) {
        this.mContext = mContext;
        this.mTweetsList = mTweetsList;
    }

    @Override
    public TweetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view =inflater.inflate(R.layout.content_tweet_item, parent, false);
        TweetViewHolder tweetViewHolder = new TweetViewHolder(view);
        return  tweetViewHolder;
    }

    @Override
    public void onBindViewHolder(TweetViewHolder tweetViewHolder, int position) {
        Tweet tweet = mTweetsList.get(position);
        if(tweet != null) {

            Glide.with(mContext)
                    .load(tweet.getUser().getProfileImageUrl())
                    .placeholder(R.mipmap.ic_launcher)
                    .into(tweetViewHolder.vTweetProfilePic);

            tweetViewHolder.vTweetScreenName.setText(tweet.getUser().getScreenName());
            tweetViewHolder.vTweetScreenName.setMovementMethod(LinkMovementMethod.getInstance());

            tweetViewHolder.vTweetName.setText(tweet.getUser().getName());

            tweetViewHolder.vTweetBody.setText(tweet.getBody());
            tweetViewHolder.vTweetBody.setMovementMethod(LinkMovementMethod.getInstance());

            String tweetAge = getTweetAge(tweet.getTweetTime());
            tweetViewHolder.vTweetAge.setText(tweetAge);

           /* Glide.with(mContext)
                    .load(tweet)
                    .into(tweetViewHolder.vTweetPic);*/
        }
    }

    @Override
    public int getItemCount() {
        return mTweetsList.size();
    }

    public static String getTweetAge(String tweetTime) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy"; // "created_at": "Tue Aug 28 21:16:23 +0000 2012",
        SimpleDateFormat sdf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sdf.setLenient(true);
        long formattedTimeInSecs = 0;

        try {
            formattedTimeInSecs = sdf.parse(tweetTime).getTime();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        String tweetAge = DateUtils.getRelativeTimeSpanString(formattedTimeInSecs, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        Log.v(TAG, "tweetAge:" + tweetAge);
        tweetAge = tweetAge.replace(" second ago","s");
        tweetAge = tweetAge.replace(" seconds ago","s");
        tweetAge = tweetAge.replace(" minute ago","m");
        tweetAge = tweetAge.replace(" minutes ago","m");
        tweetAge = tweetAge.replace(" hour ago","h");
        tweetAge = tweetAge.replace(" hours ago","h");
        tweetAge = tweetAge.replace(" day ago","d");
        tweetAge = tweetAge.replace(" days ago","d");
        Log.v(TAG, "tweetAge:" + tweetAge);
        return tweetAge;
    }


    /**Methods supporting SwipeRefreshLayout*/

    public void clear() {
        mTweetsList.clear();
        notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(List<Tweet> list) {
        mTweetsList.addAll(list);
        notifyDataSetChanged();
    }
}
