package com.codepath.apps.twitterville.helper;

import android.view.View;

/**
 * Created by alishaalam on 8/7/16.
 */
public interface ClickListener {
    void onClick(View view, int position);
    void onLongClick(View view, int position);
}
