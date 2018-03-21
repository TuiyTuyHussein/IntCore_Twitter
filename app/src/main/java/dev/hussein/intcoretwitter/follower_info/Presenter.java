package dev.hussein.intcoretwitter.follower_info;

/**
 * Created by Dev M Hussein on 3/21/2018.
 */

interface Presenter {


    interface GetTweets {
        void loadTweets(long userId, int count);
    }

    interface GetUserInfo {
        void loadUserInfo(long userId);
    }
}
