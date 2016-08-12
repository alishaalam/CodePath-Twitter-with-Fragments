package com.codepath.apps.twitterville.helper;

import android.view.View;

/**
 * Created by alishaalam on 8/11/16.
 */
public interface IViewHolderClickedListener {

    void onProfilePicClicked(View v, int pos);
    void onTweetClicked(View v, int pos);
}
