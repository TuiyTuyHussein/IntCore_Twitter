package dev.hussein.intcoretwitter.follower_info;

/**
 * Created by Dev M Hussein on 3/21/2018.
 *
 * Using MVC
 */

interface Presenter {


    interface GetTweets {

        /**
         * to get tweets
         *
         * @param userId
         * @param count
         */
        void loadTweets(long userId, int count);
    }

    interface GetUserInfo {
        /**
         * to get tweets
         *
         * @param userId
         */
        void loadUserInfo(long userId);
    }
}
