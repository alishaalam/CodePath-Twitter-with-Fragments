package com.codepath.apps.twitterville.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.twitterville.R;
import com.codepath.apps.twitterville.fragments.HomeTimelineFragment;
import com.codepath.apps.twitterville.fragments.MentionsTimelineFragment;
import com.codepath.apps.twitterville.helper.FragmentUtil;

public class TimeLineActivity extends AppCompatActivity {

    private static final String TAG = TimeLineActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));

        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Set the compose tweet button
        FloatingActionButton btn_compose_tweet = (FloatingActionButton) findViewById(R.id.btn_compose_tweet);
        if (btn_compose_tweet != null) {
            btn_compose_tweet.setOnClickListener(view -> FragmentUtil.showComposeTweetDialog(getSupportFragmentManager()));
        }
    }

    public class TweetsPagerAdapter extends FragmentPagerAdapter{

        private String[] tabTitles = {"Home", "Mentions", "Messages"};

        //Adapter gets the manager to add or remove fragments from the activity
        public TweetsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        //TODO: Swap 1st and 3rd fragments
        @Override
        public Fragment getItem(int position) {
            if(position == 0)
                return new HomeTimelineFragment();
            else if(position == 1)
                return new MentionsTimelineFragment();
            else if(position == 2)
                //return new DirectMessagesFragment();
                return new HomeTimelineFragment();
            else
                return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    public void onProfileClick(MenuItem mi) {
        Intent intent = new Intent(TimeLineActivity.this, ProfileActivity.class);
        startActivity(intent);
        Log.d(TAG, "Starting Profile Activity");
    }
}
