package com.codepath.apps.twitterville.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.twitterville.R;
import com.codepath.apps.twitterville.fragments.ComposeTweetDialogFragment;
import com.codepath.apps.twitterville.helper.FragmentUtil;
import com.codepath.apps.twitterville.models.Tweet;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TweetDetailActivity extends AppCompatActivity implements ComposeTweetDialogFragment.OnTweetPostedListener{

    private static final String TAG = TweetDetailActivity.class.getSimpleName();
    public static final String ARG_ITEM = "tweet";
    Tweet mTweet;

    @NonNull @BindView(R.id.iv_profile_pic)
    ImageView iv_profile_pic;

    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.tv_handle)
    TextView tv_screenName;

    @BindView(R.id.tv_body)
    TextView tv_body;

    @BindView(R.id.tv_tweet_time)
    TextView tv_tweet_time;

    @BindView(R.id.et_tweet_reply)
    EditText et_tweet_reply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_detail);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.ic_navigation_arrow_back);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra(TweetDetailActivity.ARG_ITEM));

        Glide.with(this)
                .load(mTweet.getUser().getProfileImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .into(iv_profile_pic);

        tv_name.setText(mTweet.getUser().getName());
        tv_screenName.setText(mTweet.getUser().getScreenName());
        tv_body.setText(mTweet.getBody());
        tv_tweet_time.setText(formatTweetTime(mTweet.getTweetTime()));
        Log.d(TAG, formatTweetTime(mTweet.getTweetTime()));


        et_tweet_reply.setHint("Reply to " + mTweet.getUser().getScreenName());
        et_tweet_reply.setOnClickListener(v -> FragmentUtil.showComposeTweetDialog(getSupportFragmentManager(), mTweet.getUser().getScreenName()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                //showEditDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public String formatTweetTime(String tweetTime) {
        String inputPattern = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        String outputPattern = " h:mm a dd MMM yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(tweetTime);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    @Override
    public void onPostTweet(String tweetBody) {
        //TODO: Update the list of the replies
        //TODO: Pass the tweetBody back to TimeLineActivity
    }
}
