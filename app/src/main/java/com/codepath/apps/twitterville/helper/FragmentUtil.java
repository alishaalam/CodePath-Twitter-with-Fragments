package com.codepath.apps.twitterville.helper;

import android.support.v4.app.FragmentManager;

import com.codepath.apps.twitterville.fragments.ComposeTweetDialogFragment;

/**
 * Created by alishaalam on 8/7/16.
 */
public class FragmentUtil{


    public static void showComposeTweetDialog(FragmentManager supportFragmentManager) {
        ComposeTweetDialogFragment editNameDialogFragment = ComposeTweetDialogFragment.newInstance("");
        editNameDialogFragment.show(supportFragmentManager, "fragment_edit_name");

    }

    public static void showComposeTweetDialog(FragmentManager supportFragmentManager, String screenName) {
        ComposeTweetDialogFragment editNameDialogFragment = ComposeTweetDialogFragment.newInstance(screenName);
        editNameDialogFragment.show(supportFragmentManager, "fragment_edit_name");
    }
}
