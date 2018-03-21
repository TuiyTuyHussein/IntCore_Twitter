package dev.hussein.intcoretwitter.api.methods;

import com.twitter.sdk.android.core.models.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Dev. M. Hussein on 12/20/2017.
 */

public interface UserInfo {


    @GET("/1.1/users/show.json")
    Call<User> apply(
            @Query("user_id") long userId,
            @Query("screen_name") String screenName,
            @Query("include_entities") Boolean includeEntities
    );


}
