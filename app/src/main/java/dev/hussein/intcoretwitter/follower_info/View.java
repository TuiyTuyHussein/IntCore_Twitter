package dev.hussein.intcoretwitter.follower_info;

import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;

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
     * uses to send follower tweets or failure reasons from server to view
     */
    interface OnFollowerTweetsResponse {
        void setOnFollowerTweetsResponse(Tweet[] response);

        void setOnFollowerTweetsFailure(Throwable failure);
    }

    /**
     * uses to send user information or failure reasons from server to view
     */
    interface OnUserInfoResponse {
        void setOnUserInfoResponse(User user);

        void setOnUserInfoFailure(Throwable failure);
    }

}
