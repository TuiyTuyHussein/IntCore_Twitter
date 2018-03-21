package dev.hussein.intcoretwitter.api.methods;

import dev.hussein.intcoretwitter.pojo.FollowersResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Dev. M. Hussein on 12/20/2017.
 */

public interface UserFollowers {


    @GET("/1.1/followers/list.json")
    Call<FollowersResponse> apply(
            @Query("user_id") long userId,
            @Query("cursor") long cursor,
            @Query("screen_name") String screenName,
            @Query("skip_status") Boolean skipStatus,
            @Query("include_user_entities") Boolean includeUserEntities,
            @Query("count") int count
    );


}
