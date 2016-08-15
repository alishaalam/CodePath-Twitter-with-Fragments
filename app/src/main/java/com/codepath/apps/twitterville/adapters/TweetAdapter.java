package com.codepath.apps.twitterville.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.twitterville.R;
import com.codepath.apps.twitterville.helper.IViewHolderClickedListener;
import com.codepath.apps.twitterville.models.Tweet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alishaalam on 8/5/16.
 */
public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.TweetViewHolder>{

    Context mContext;
    ArrayList<Tweet> mTweetsList = new ArrayList<Tweet>();
    Tweet mTweet;
    private static final String TAG = TweetAdapter.class.getSimpleName();
    private static IViewHolderClickedListener mListener;

    public void setIViewHolderClickedListener(IViewHolderClickedListener listener){
        this.mListener = listener;

    }

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
    public void onBindViewHolder(TweetViewHolder tweetViewHolder, final int position) {
        mTweet = mTweetsList.get(position);

        if(mTweet != null) {

            Glide.with(mContext)
                    .load(mTweet.getUser().getProfileImageUrl())
                    .placeholder(R.mipmap.ic_launcher)
                    .into(tweetViewHolder.vTweetProfilePic);


            tweetViewHolder.vTweetScreenName.setText(mTweet.getUser().getFormattedScreenName());
            tweetViewHolder.vTweetScreenName.setMovementMethod(LinkMovementMethod.getInstance());

            tweetViewHolder.vTweetName.setText(mTweet.getUser().getName());

            tweetViewHolder.vTweetBody.setText(mTweet.getBody());
            tweetViewHolder.vTweetBody.setMovementMethod(LinkMovementMethod.getInstance());

            String tweetAge = getTweetAge(mTweet.getTweetTime());
            tweetViewHolder.vTweetAge.setText(tweetAge);

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
        tweetAge = tweetAge.replace(" second ago","s");
        tweetAge = tweetAge.replace(" seconds ago","s");
        tweetAge = tweetAge.replace(" minute ago","m");
        tweetAge = tweetAge.replace(" minutes ago","m");
        tweetAge = tweetAge.replace(" hour ago","h");
        tweetAge = tweetAge.replace(" hours ago","h");
        tweetAge = tweetAge.replace(" day ago","d");
        tweetAge = tweetAge.replace(" days ago","d");
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

    public static class TweetViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.iv_profile_pic)
        public ImageView vTweetProfilePic;

        @BindView(R.id.tv_screen_name)
        public TextView vTweetScreenName;

        @BindView(R.id.tv_name)
        public TextView vTweetName;

        @BindView(R.id.tv_body)
        public TextView vTweetBody;

        @Nullable @BindView(R.id.tv_age)
        public TextView vTweetAge;

        @Nullable @BindView(R.id.iv_tweet_pic)
        public ImageView vTweetPic;


        public TweetViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            vTweetProfilePic.setOnClickListener(v -> {
                if(mListener != null)
                    mListener.onProfilePicClicked(v, getAdapterPosition());

            });

            vTweetBody.setOnClickListener(v -> {
                if(mListener != null){
                    mListener.onTweetClicked(v, getAdapterPosition());
                }
            });
        }

    }
}
