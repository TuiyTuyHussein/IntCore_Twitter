package dev.hussein.intcoretwitter.config;

/**
 * Created by Dev M Hussein on 3/21/2018.
 *
 * use this to handle if user want to load more followers
 * or refresh and get new added followers
 * @apiNote NEXT  --> for load more followers
 * @apiNote NEW  --> for get new added followers
 */


public enum FollowersRequestType {
    NEXT, NEW
}
