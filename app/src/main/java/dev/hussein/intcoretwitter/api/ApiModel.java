package dev.hussein.intcoretwitter.api;

import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterSession;

import dev.hussein.intcoretwitter.api.methods.UserFollowers;
import dev.hussein.intcoretwitter.api.methods.UserInfo;
import dev.hussein.intcoretwitter.api.methods.UserTweets;

/**
 * Created by Dev M Hussein on 3/21/2018.
 */

public class ApiModel extends TwitterApiClient {

    private ApiModel(TwitterSession session) {
        super(session);
    }

    public static ApiModel instance(TwitterSession session) {
        return new ApiModel(session);
    }

    public UserFollowers getFollowers() {
        return getService(UserFollowers.class);
    }
    public UserInfo getUserInfo() {
        return getService(UserInfo.class);
    }
    public UserTweets getUserTweets() {
        return getService(UserTweets.class);
    }

}
