package com.codepath.apps.twitterville.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.codepath.apps.twitterville.R;

/**
 * Created by alishaalam on 8/6/16.
 */
public class ComposeTweetDialogFragment extends DialogFragment implements View.OnClickListener{

    EditText mEtTweetBody;
    Button btnPostTweet;


    public ComposeTweetDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static ComposeTweetDialogFragment newInstance(String screenName) {
        ComposeTweetDialogFragment frag = new ComposeTweetDialogFragment();
        Bundle args = new Bundle();
        args.putString("screenName", screenName);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog_compose_tweet, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String btnText = null;
        // Get field from view
        mEtTweetBody = (EditText) view.findViewById(R.id.et_tweet_body);

        // Fetch arguments from bundle and set title
        String screenName = getArguments().getString("screenName", "");
        if(screenName.isEmpty()) {
            btnText = "TWEET";
        }else {
            mEtTweetBody.setText(screenName + " ");
            btnText = "REPLY";
        }
        // Show soft keyboard automatically and request focus to field
        mEtTweetBody.requestFocus();
        getDialog().setTitle("Tweet");
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        btnPostTweet = (Button) view.findViewById(R.id.btn_send_tweet);
        btnPostTweet.setText(btnText);
        btnPostTweet.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String tweetBody = mEtTweetBody.getText().toString();
        OnTweetPostedListener listener = (OnTweetPostedListener) getActivity();
        listener.onPostTweet(tweetBody);
        dismiss();
    }

    // Defines the listener interface with a method passing back filters as result to activity.
    public interface OnTweetPostedListener {
        void onPostTweet(String tweetBody);
    }


}
