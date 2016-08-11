// Generated code from Butter Knife. Do not modify!
package com.codepath.apps.twitterville.activities;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import com.codepath.apps.twitterville.R;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class TweetDetailActivity_ViewBinding<T extends TweetDetailActivity> implements Unbinder {
  protected T target;

  public TweetDetailActivity_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.iv_profile_pic = finder.findRequiredViewAsType(source, R.id.iv_profile_pic, "field 'iv_profile_pic'", ImageView.class);
    target.tv_name = finder.findRequiredViewAsType(source, R.id.tv_name, "field 'tv_name'", TextView.class);
    target.tv_screenName = finder.findRequiredViewAsType(source, R.id.tv_handle, "field 'tv_screenName'", TextView.class);
    target.tv_body = finder.findRequiredViewAsType(source, R.id.tv_body, "field 'tv_body'", TextView.class);
    target.tv_tweet_time = finder.findRequiredViewAsType(source, R.id.tv_tweet_time, "field 'tv_tweet_time'", TextView.class);
    target.et_tweet_reply = finder.findRequiredViewAsType(source, R.id.et_tweet_reply, "field 'et_tweet_reply'", EditText.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.iv_profile_pic = null;
    target.tv_name = null;
    target.tv_screenName = null;
    target.tv_body = null;
    target.tv_tweet_time = null;
    target.et_tweet_reply = null;

    this.target = null;
  }
}
