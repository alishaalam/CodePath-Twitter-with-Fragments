package com.codepath.apps.twitterville.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.codepath.apps.twitterville.R;
import com.codepath.oauth.OAuthLoginActionBarActivity;
import com.skyfishjy.library.RippleBackground;

import butterknife.BindView;
import butterknife.ButterKnife;
import tyrantgit.explosionfield.ExplosionField;

public class LoginActivity extends OAuthLoginActionBarActivity<TwitterClient> {
	RippleBackground rippleBackground;
	Handler mHandler;
	private ExplosionField mExplosionField;
	private static final String TAG = LoginActivity.class.getSimpleName();
	private static int COUNTER = 0;

	@BindView(R.id.centerImage)
	ImageView loginImage;

	@BindView(R.id.btn_login)
	Button loginButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		ButterKnife.bind(this);
		mExplosionField = ExplosionField.attach2Window(this);
		mHandler = new Handler();

		rippleBackground = (RippleBackground) findViewById(R.id.content);
	}

	// OAuth authenticated successfully, launch primary authenticated activity
	// i.e Display application "homepage"
	@Override
	public void onLoginSuccess() {
		mHandler.postDelayed(() -> {
            Intent i = new Intent(LoginActivity.this, TimeLineActivity.class);
            startActivity(i);
        }, 3000);
	}

	// OAuth authentication flow failed, handle the error
	// i.e Display an error dialog or toast
	@Override
	public void onLoginFailure(Exception e) {
		e.printStackTrace();
	}

	// Click handler method for the button used to start OAuth flow
	// Uses the client to initiate OAuth authorization
	// This should be tied to a button used to login
	public void loginToRest(View view) {
		getClient().connect();
	}

	public void onExplodeElements(View v) {

	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.v(TAG, "In onResume");
		if(COUNTER == 1){
			onLoginImageClick(findViewById(R.id.centerImage));
		}
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.v(TAG, "In onRestart");
	}

	@Override
	protected void onPause() {
		super.onPause();
		rippleBackground.stopRippleAnimation();
	}

	public void onLoginImageClick(final View v) {
		Log.v(TAG, "Counter: "+ COUNTER);
		if(COUNTER == 0)
			rippleBackground.startRippleAnimation();
		else {
			mHandler.postDelayed(() -> {
                mExplosionField.explode(v);
                mExplosionField.explode(findViewById(R.id.btn_login));
                Log.v(TAG, "Explosion!!!!!!!!!!!");
            }, 2000);
		}
		COUNTER++;
	}
}
