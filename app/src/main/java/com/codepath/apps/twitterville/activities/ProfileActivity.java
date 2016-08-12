package com.codepath.apps.twitterville.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.twitterville.R;
import com.codepath.apps.twitterville.models.User;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = ProfileActivity.class.getSimpleName();
    public static final String ARG_ITEM = "user";
    User mUser;

    @BindView(R.id.detail_toolbar)
    Toolbar toolbar;

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

        mUser = Parcels.unwrap(getIntent().getParcelableExtra(ProfileActivity.ARG_ITEM));

        Glide.with(this)
                .load(mUser.getProfileBackgroundImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .into(ivProfileBackdrop);
        Glide.with(this)
                        .load(mUser.getProfileImageUrl())
                        .placeholder(R.mipmap.ic_launcher)
                        .into(ivProfilePicture);

        tvUserName.setText(mUser.getName());
        tvUserScreenName.setText(mUser.getFormattedScreenName());
        tvDescription.setText(mUser.getDescription());

    }

}
