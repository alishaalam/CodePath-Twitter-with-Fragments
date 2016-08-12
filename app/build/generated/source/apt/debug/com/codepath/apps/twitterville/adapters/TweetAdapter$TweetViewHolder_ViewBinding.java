// Generated code from Butter Knife. Do not modify!
package com.codepath.apps.twitterville.adapters;

import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import com.codepath.apps.twitterville.R;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class TweetAdapter$TweetViewHolder_ViewBinding<T extends TweetAdapter.TweetViewHolder> implements Unbinder {
  protected T target;

  public TweetAdapter$TweetViewHolder_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.vTweetProfilePic = finder.findRequiredViewAsType(source, R.id.iv_profile_pic, "field 'vTweetProfilePic'", ImageView.class);
    target.vTweetScreenName = finder.findRequiredViewAsType(source, R.id.tv_screen_name, "field 'vTweetScreenName'", TextView.class);
    target.vTweetName = finder.findRequiredViewAsType(source, R.id.tv_name, "field 'vTweetName'", TextView.class);
    target.vTweetBody = finder.findRequiredViewAsType(source, R.id.tv_body, "field 'vTweetBody'", TextView.class);
    target.vTweetAge = finder.findOptionalViewAsType(source, R.id.tv_age, "field 'vTweetAge'", TextView.class);
    target.vTweetPic = finder.findOptionalViewAsType(source, R.id.iv_tweet_pic, "field 'vTweetPic'", ImageView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.vTweetProfilePic = null;
    target.vTweetScreenName = null;
    target.vTweetName = null;
    target.vTweetBody = null;
    target.vTweetAge = null;
    target.vTweetPic = null;

    this.target = null;
  }
}
