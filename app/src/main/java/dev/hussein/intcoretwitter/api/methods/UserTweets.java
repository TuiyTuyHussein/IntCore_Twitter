package dev.hussein.intcoretwitter.api.methods;

import com.twitter.sdk.android.core.models.Tweet;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Dev. M. Hussein on 3/21/2018.
 */

public interface UserTweets {

    /**
     * Get user tweets
     */

    @GET("/1.1/statuses/user_timeline.json")
    Call<Tweet[]> apply(
            @Query("user_id") long userId,
            @Query("count") int count
    );


}
