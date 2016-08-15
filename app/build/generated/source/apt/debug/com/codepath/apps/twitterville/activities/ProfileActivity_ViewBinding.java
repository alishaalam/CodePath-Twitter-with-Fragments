// Generated code from Butter Knife. Do not modify!
package com.codepath.apps.twitterville.activities;

import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import com.codepath.apps.twitterville.R;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class ProfileActivity_ViewBinding<T extends ProfileActivity> implements Unbinder {
  protected T target;

  public ProfileActivity_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.toolbar = finder.findRequiredViewAsType(source, R.id.detail_toolbar, "field 'toolbar'", Toolbar.class);
    target.ivUserFollowing = finder.findRequiredViewAsType(source, R.id.user_follow, "field 'ivUserFollowing'", ImageView.class);
    target.ivUserFavorited = finder.findRequiredViewAsType(source, R.id.user_fav, "field 'ivUserFavorited'", ImageView.class);
    target.ivProfileBackdrop = finder.findRequiredViewAsType(source, R.id.profile_detail_backdrop, "field 'ivProfileBackdrop'", ImageView.class);
    target.ivProfilePicture = finder.findRequiredViewAsType(source, R.id.user_profile_pic, "field 'ivProfilePicture'", ImageView.class);
    target.tvUserName = finder.findRequiredViewAsType(source, R.id.user_name, "field 'tvUserName'", TextView.class);
    target.tvUserScreenName = finder.findRequiredViewAsType(source, R.id.user_screen_name, "field 'tvUserScreenName'", TextView.class);
    target.tvDescription = finder.findRequiredViewAsType(source, R.id.user_description, "field 'tvDescription'", TextView.class);
    target.tvFriendsCount = finder.findRequiredViewAsType(source, R.id.friends_count, "field 'tvFriendsCount'", TextView.class);
    target.tvFollowersCount = finder.findRequiredViewAsType(source, R.id.followers_count, "field 'tvFollowersCount'", TextView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.toolbar = null;
    target.ivUserFollowing = null;
    target.ivUserFavorited = null;
    target.ivProfileBackdrop = null;
    target.ivProfilePicture = null;
    target.tvUserName = null;
    target.tvUserScreenName = null;
    target.tvDescription = null;
    target.tvFriendsCount = null;
    target.tvFollowersCount = null;

    this.target = null;
  }
}
