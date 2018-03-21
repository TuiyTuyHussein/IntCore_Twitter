package dev.hussein.intcoretwitter.pojo;


import com.google.gson.annotations.SerializedName;
import com.twitter.sdk.android.core.models.Tweet;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Dev M Hussein on 3/21/2018.
 */

public class TweetsResponse implements Serializable {

    @SerializedName("users")
    public List<Tweet> tweets;

}
