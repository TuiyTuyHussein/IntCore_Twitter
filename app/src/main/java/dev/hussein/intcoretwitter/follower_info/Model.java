package dev.hussein.intcoretwitter.follower_info;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;

import dev.hussein.intcoretwitter.api.ApiModel;
import retrofit2.Call;

/**
 * Created by Dev M Hussein on 3/21/2018.
 *
 * Using MVC
 */

class Model implements Presenter.GetTweets, Presenter.GetUserInfo {

    private View.OnFollowerTweetsResponse onFollowerTweetsResponse;
    private View.OnUserInfoResponse onUserInfoResponse;

    public Model(View.OnFollowerTweetsResponse onFollowerTweetsResponse) {
        this.onFollowerTweetsResponse = onFollowerTweetsResponse;
    }

    public Model(View.OnUserInfoResponse onUserInfoResponse) {
        this.onUserInfoResponse = onUserInfoResponse;
    }

    @Override
    public void loadTweets(long userId, int count) {
        TwitterSession session = TwitterCore.getInstance()
                .getSessionManager()
                .getActiveSession();
        if (session != null) {

            Call<Tweet[]> call = ApiModel.instance(session).getUserTweets()
                    .apply(userId,
                            count
                    );

            call.enqueue(new Callback<Tweet[]>() {
                @Override
                public void success(Result<Tweet[]> result) {
                    if (result != null
                            && onFollowerTweetsResponse != null) {
                        onFollowerTweetsResponse
                                .setOnFollowerTweetsResponse(result.data);
                    }
                }

                @Override
                public void failure(TwitterException exception) {
                    if (exception != null && onFollowerTweetsResponse != null) {
                        onFollowerTweetsResponse.setOnFollowerTweetsFailure(exception);
                    }
                }
            });


        }
    }

    @Override
    public void loadUserInfo(long userId) {
        TwitterSession session = TwitterCore.getInstance()
                .getSessionManager()
                .getActiveSession();
        if (session != null) {
            Call<User> call = ApiModel.instance(session).getUserInfo()
                    .apply(userId,
                            null,
                            true
                    );

            call.enqueue(new Callback<User>() {
                @Override
                public void success(Result<User> result) {
                    if (result != null
                            && onUserInfoResponse != null) {
                        onUserInfoResponse
                                .setOnUserInfoResponse(result.data);
                    }
                }

                @Override
                public void failure(TwitterException exception) {
                    if (exception != null && onUserInfoResponse != null) {
                        onUserInfoResponse.setOnUserInfoFailure(exception);
                    }
                }
            });


        }
    }
}
