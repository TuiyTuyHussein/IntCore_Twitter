package dev.hussein.intcoretwitter.followers;

import com.twitter.sdk.android.core.models.User;

import dev.hussein.intcoretwitter.config.FollowersRequestType;
import dev.hussein.intcoretwitter.pojo.FollowersResponse;

/**
 * Created by Dev M Hussein on 3/21/2018.
 *
 * Using MVC
 */


/**
 * connection interface to send response data from api server model to view
 */
interface View {

    /**
     * uses to send followers data or failure reasons from server to view
     */
    interface OnFollowersResponse {
        void setOnFollowersResponse(FollowersRequestType followersRequestType
                , FollowersResponse response);

        void setOnFollowersFailure(Throwable failure);
    }

    /**
     * uses to send user information or failure reasons from server to view
     */
    interface OnUserInfoResponse {
        void setOnUserInfoResponse(User user);

        void setOnUserInfoFailure(Throwable failure);
    }

}
