package dev.hussein.intcoretwitter.pojo;


import com.google.gson.annotations.SerializedName;
import com.twitter.sdk.android.core.models.User;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Dev M Hussein on 3/21/2018.
 */

public class FollowersResponse implements Serializable {

    @SerializedName("users")
    public List<User> followers;
    public long next_cursor;
    public String next_cursor_str;
    public long previous_cursor;
    public String previous_cursor_str;
}
