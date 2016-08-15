
package com.codepath.apps.twitterville.models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import org.parceler.Generated;
import org.parceler.ParcelWrapper;
import org.parceler.ParcelerRuntimeException;

@Generated(value = "org.parceler.ParcelAnnotationProcessor", date = "2016-08-14T18:37-0700")
@SuppressWarnings({
    "unchecked",
    "deprecation"
})
public class Messages$$Parcelable
    implements Parcelable, ParcelWrapper<com.codepath.apps.twitterville.models.Messages>
{

    private com.codepath.apps.twitterville.models.Messages messages$$0;
    @SuppressWarnings("UnusedDeclaration")
    public final static Messages$$Parcelable.Creator$$0 CREATOR = new Messages$$Parcelable.Creator$$0();

    public Messages$$Parcelable(com.codepath.apps.twitterville.models.Messages messages$$2) {
        messages$$0 = messages$$2;
    }

    @Override
    public void writeToParcel(android.os.Parcel parcel$$0, int flags) {
        write(messages$$0, parcel$$0, flags, new HashSet<Integer>());
    }

    public static void write(com.codepath.apps.twitterville.models.Messages messages$$1, android.os.Parcel parcel$$1, int flags$$0, Set<Integer> identitySet$$0) {
        int identity$$0 = System.identityHashCode(messages$$1);
        parcel$$1 .writeInt(identity$$0);
        if (!identitySet$$0 .contains(identity$$0)) {
            identitySet$$0 .add(identity$$0);
            if (messages$$1 == null) {
                parcel$$1 .writeInt(-1);
            } else {
                parcel$$1 .writeInt(1);
                if (messages$$1 .uid == null) {
                    parcel$$1 .writeInt(-1);
                } else {
                    parcel$$1 .writeInt(1);
                    parcel$$1 .writeLong(messages$$1 .uid);
                }
                parcel$$1 .writeString(messages$$1 .createdAt);
                if (messages$$1 .user_id == null) {
                    parcel$$1 .writeInt(-1);
                } else {
                    parcel$$1 .writeInt(1);
                    parcel$$1 .writeLong(messages$$1 .user_id);
                }
                com.codepath.apps.twitterville.models.User$$Parcelable.write(messages$$1 .sender, parcel$$1, flags$$0, identitySet$$0);
                parcel$$1 .writeString(messages$$1 .sender_screen_name);
                parcel$$1 .writeString(messages$$1 .body);
                parcel$$1 .writeLong(messages$$1 .sender_id);
            }
        }
    }

    @Override
    public int describeContents() {
        return  0;
    }

    @Override
    public com.codepath.apps.twitterville.models.Messages getParcel() {
        return messages$$0;
    }

    public static com.codepath.apps.twitterville.models.Messages read(android.os.Parcel parcel$$3, Map<Integer, Object> identityMap$$0) {
        com.codepath.apps.twitterville.models.Messages messages$$3;
        int identity$$1 = parcel$$3 .readInt();
        if (identityMap$$0 .containsKey(identity$$1)) {
            com.codepath.apps.twitterville.models.Messages messages$$4 = ((com.codepath.apps.twitterville.models.Messages) identityMap$$0 .get(identity$$1));
            if ((messages$$4 == null)&&(identity$$1 != 0)) {
                throw new ParcelerRuntimeException("An instance loop was detected whild building Parcelable and deseralization cannot continue.  This error is most likely due to using @ParcelConstructor or @ParcelFactory.");
            }
            return messages$$4;
        }
        if (parcel$$3 .readInt() == -1) {
            messages$$3 = null;
            identityMap$$0 .put(identity$$1, null);
        } else {
            com.codepath.apps.twitterville.models.Messages messages$$5;
            identityMap$$0 .put(identity$$1, null);
            messages$$5 = new com.codepath.apps.twitterville.models.Messages();
            identityMap$$0 .put(identity$$1, messages$$5);
            int int$$0 = parcel$$3 .readInt();
            java.lang.Long long$$0;
            if (int$$0 < 0) {
                long$$0 = null;
            } else {
                long$$0 = parcel$$3 .readLong();
            }
            messages$$5 .uid = long$$0;
            messages$$5 .createdAt = parcel$$3 .readString();
            int int$$1 = parcel$$3 .readInt();
            java.lang.Long long$$1;
            if (int$$1 < 0) {
                long$$1 = null;
            } else {
                long$$1 = parcel$$3 .readLong();
            }
            messages$$5 .user_id = long$$1;
            User user$$0 = com.codepath.apps.twitterville.models.User$$Parcelable.read(parcel$$3, identityMap$$0);
            messages$$5 .sender = user$$0;
            messages$$5 .sender_screen_name = parcel$$3 .readString();
            messages$$5 .body = parcel$$3 .readString();
            messages$$5 .sender_id = parcel$$3 .readLong();
            messages$$3 = messages$$5;
        }
        return messages$$3;
    }

    public final static class Creator$$0
        implements Creator<Messages$$Parcelable>
    {


        @Override
        public Messages$$Parcelable createFromParcel(android.os.Parcel parcel$$2) {
            return new Messages$$Parcelable(read(parcel$$2, new HashMap<Integer, Object>()));
        }

        @Override
        public Messages$$Parcelable[] newArray(int size) {
            return new Messages$$Parcelable[size] ;
        }

    }

}
