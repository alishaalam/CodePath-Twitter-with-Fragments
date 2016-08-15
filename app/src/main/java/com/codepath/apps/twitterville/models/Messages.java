
package com.codepath.apps.twitterville.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;

@Parcel(analyze={Messages.class})
public class Messages extends Model {

    /**
     * "created_at": "Mon Aug 27 17:21:03 +0000 2012",
     "entities": {
     "hashtags": [],
     "urls": [],
     "user_mentions": []
     },
     "id": 240136858829479936,
     "id_str": "240136858829479936",
     * "sender_id": 38895958,
     "sender_screen_name": "theSeanCook",
     "text": "booyakasha"
     */


    @Column(name = "Body")
    public String body;

    @Column(name = "Uid", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public Long uid;

    @Column(name = "user_id")
    public Long user_id;

    public User sender;

    @Column(name = "CreatedAt")
    String createdAt;

    long sender_id;
    String sender_screen_name;

    public Messages() { super(); }

    public static Messages fromJSON(JSONObject jsonObject) {
        Messages tweet = new Messages();

        try {
            //tweet.uid = jsonObject.getLong("id");
            tweet.body = jsonObject.getString("text");
            //tweet.createdAt = jsonObject.getString("created_at");
            //tweet.sender = User.fromJSON( jsonObject.getJSONObject("sender") );
            tweet.sender_id=jsonObject.getLong("sender_id");
            tweet.sender_screen_name=jsonObject.getString("sender_screen_name");
            tweet.save();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tweet;
    }

    public static ArrayList<Messages> fromJsonArray(JSONArray jsonArray) {
        ArrayList<Messages> tweets = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {

            try {
                JSONObject tweetJson = jsonArray.getJSONObject(i);
                Messages messages = Messages.fromJSON(tweetJson);
                if (messages!= null) {
                    tweets.add(messages);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }

        }
        return tweets;
    }

    /*public static List<Tweet> getAllTweetsFromDB() {
        return new Select()
                .from(Tweet.class)
                .orderBy("CreatedAt DESC")
                .limit("25")
                .execute();
    }*/

    public String getTweetTime() {
        return createdAt;
    }

    public long getUid() {
        return uid;
    }

    public String getBody() {
        return body;
    }

    public User getSender() {
        return sender;
    }

    public long getSender_id() {
        return sender_id;
    }

    public String getSender_screen_name() {
        return sender_screen_name;
    }

    public User getUser() {
        if (sender != null) {
            return sender;
        } else {
            return User.fromUserId(user_id);
        }
//        return user != null ? user : User.fromUserId(user_id);
    }
}
