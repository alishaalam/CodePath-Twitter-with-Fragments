// Generated code from Butter Knife. Do not modify!
package com.codepath.apps.twitterville.activities;

import android.widget.Button;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import com.codepath.apps.twitterville.R;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class LoginActivity_ViewBinding<T extends LoginActivity> implements Unbinder {
  protected T target;

  public LoginActivity_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.loginImage = finder.findRequiredViewAsType(source, R.id.centerImage, "field 'loginImage'", ImageView.class);
    target.loginButton = finder.findRequiredViewAsType(source, R.id.btn_login, "field 'loginButton'", Button.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.loginImage = null;
    target.loginButton = null;

    this.target = null;
  }
}
