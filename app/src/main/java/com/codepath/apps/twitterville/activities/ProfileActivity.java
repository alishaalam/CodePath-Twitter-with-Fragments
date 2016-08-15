package com.codepath.apps.twitterville.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.twitterville.R;
import com.codepath.apps.twitterville.fragments.UserTimelineFragment;
import com.codepath.apps.twitterville.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;
import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = ProfileActivity.class.getSimpleName();
    public static final String ARG_ITEM = "user";
    private UserTimelineFragment userTimelineFragment;
    String screenName = null;
    User mUser;

    TwitterClient client;

    @BindView(R.id.detail_toolbar)
    Toolbar toolbar;

    @BindView(R.id.user_follow)
    ImageView ivUserFollowing;

    @BindView(R.id.user_fav)
    ImageView ivUserFavorited;

    @BindView(R.id.profile_detail_backdrop)
    ImageView ivProfileBackdrop;

    @BindView(R.id.user_profile_pic)
    ImageView ivProfilePicture;

    @BindView(R.id.user_name)
    TextView tvUserName;

    @BindView(R.id.user_screen_name)
    TextView tvUserScreenName;

    @BindView(R.id.user_description)
    TextView tvDescription;

    @BindView(R.id.friends_count)
    TextView tvFriendsCount;

    @BindView(R.id.followers_count)
    TextView tvFollowersCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.ic_navigation_arrow_back);
            actionBar.setHomeAsUpIndicator(upArrow);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        //Get the bundle from TimeLineActivity
        mUser = Parcels.unwrap(getIntent().getParcelableExtra(ProfileActivity.ARG_ITEM));
        if (mUser != null)
            screenName = mUser.getScreenName();
        else {
            client = TwitterApplication.getRestClient(); //get a singleton client

            //Get the account_info
            client.getUserInfo(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    mUser = User.fromUserJSON(response);
                    Log.d(TAG, "getUserInfo Success" + response.toString());
                    populateProfile(mUser);
                }


                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.d(TAG, "getUserInfo Error" + errorResponse.toString());
                }
            });
        }

        if (savedInstanceState == null) {
            //Create a fragment dynamically
            userTimelineFragment = UserTimelineFragment.newInstance(screenName);

            //Display the fragment in the activity (dynamically) since we dont want the fragment to be created unless we specify the arguments
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fl_fragment_user_timeline, userTimelineFragment);
            ft.commit(); //changes the fragments
        }

        if (mUser != null) {
            populateProfile(mUser);
        }
    }

    private void populateProfile(User user) {
        Glide.with(this)
                .load(user.getProfileBackgroundImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .into(ivProfileBackdrop);
        Glide.with(this)
                .load(user.getProfileImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .into(ivProfilePicture);

        if (user.isFollowing())
            ivUserFollowing.setImageResource(R.drawable.ic_checked_user);
        else
            ivUserFollowing.setImageResource(R.drawable.ic_social_person_add);

        if (user.isFavorite())
            ivUserFavorited.setImageResource(R.drawable.ic_fav_star);
        else
            ivUserFavorited.setImageResource(R.drawable.ic_action_star_grey);


        tvUserName.setText(user.getName());
        tvUserScreenName.setText(user.getFormattedScreenName());
        tvDescription.setText(user.getDescription());
        tvFriendsCount.setText(String.valueOf(user.getFriends_count()));
        tvFollowersCount.setText(String.valueOf(user.getFollowers_count()));
    }
}
