package dev.hussein.intcoretwitter.followers;

import dev.hussein.intcoretwitter.config.FollowersRequestType;

/**
 * Created by Dev M Hussein on 3/21/2018.
 */

interface Presenter {


    interface GetFollowers {
        void loadFollowers(FollowersRequestType followersRequestType, long cursor);
    }

    interface GetUserInfo {
        void loadUserInfo(long userId);
    }
}
