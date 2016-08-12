package com.codepath.apps.twitterville.viewholder;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitterville.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alishaalam on 8/5/16.
 */
public class TestTweetViewHolder extends RecyclerView.ViewHolder{

    private Context mContext;
    private static final String TAG = TestTweetViewHolder.class.getSimpleName();


    @BindView(R.id.iv_profile_pic)
    public ImageView vTweetProfilePic;

    @BindView(R.id.tv_screen_name)
    public TextView vTweetScreenName;

    @Nullable @BindView(R.id.tv_name)
    public TextView vTweetName;

    @Nullable @BindView(R.id.tv_body)
    public TextView vTweetBody;

    @Nullable @BindView(R.id.tv_age)
    public TextView vTweetAge;

    @Nullable @BindView(R.id.iv_tweet_pic)
    public ImageView vTweetPic;


    public TestTweetViewHolder(Context ctx, View itemView) {
        super(itemView);
        this.mContext = ctx;
        ButterKnife.bind(this,itemView);
        vTweetProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
