
package org.parceler;

import java.util.HashMap;
import java.util.Map;
import com.codepath.apps.twitterville.models.Messages;
import com.codepath.apps.twitterville.models.Messages$$Parcelable;
import com.codepath.apps.twitterville.models.Tweet;
import com.codepath.apps.twitterville.models.Tweet$$Parcelable;
import com.codepath.apps.twitterville.models.User;
import com.codepath.apps.twitterville.models.User$$Parcelable;

@Generated(value = "org.parceler.ParcelAnnotationProcessor", date = "2016-08-14T18:37-0700")
@SuppressWarnings({
    "unchecked",
    "deprecation"
})
public class Parceler$$Parcels
    implements Repository<org.parceler.Parcels.ParcelableFactory>
{

    private final Map<Class, org.parceler.Parcels.ParcelableFactory> map$$0 = new HashMap<Class, org.parceler.Parcels.ParcelableFactory>();

    public Parceler$$Parcels() {
        map$$0 .put(Tweet.class, new Parceler$$Parcels.Tweet$$Parcelable$$0());
        map$$0 .put(Messages.class, new Parceler$$Parcels.Messages$$Parcelable$$0());
        map$$0 .put(User.class, new Parceler$$Parcels.User$$Parcelable$$0());
    }

    public Map<Class, org.parceler.Parcels.ParcelableFactory> get() {
        return map$$0;
    }

    private final static class Messages$$Parcelable$$0
        implements org.parceler.Parcels.ParcelableFactory<Messages>
    {


        @Override
        public Messages$$Parcelable buildParcelable(Messages input) {
            return new Messages$$Parcelable(input);
        }

    }

    private final static class Tweet$$Parcelable$$0
        implements org.parceler.Parcels.ParcelableFactory<Tweet>
    {


        @Override
        public Tweet$$Parcelable buildParcelable(Tweet input) {
            return new Tweet$$Parcelable(input);
        }

    }

    private final static class User$$Parcelable$$0
        implements org.parceler.Parcels.ParcelableFactory<User>
    {


        @Override
        public User$$Parcelable buildParcelable(User input) {
            return new User$$Parcelable(input);
        }

    }

}
