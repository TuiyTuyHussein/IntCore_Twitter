package dev.hussein.intcoretwitter.followers;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;

import dev.hussein.intcoretwitter.api.ApiModel;
import dev.hussein.intcoretwitter.config.FollowersRequestType;
import dev.hussein.intcoretwitter.pojo.FollowersResponse;
import retrofit2.Call;

/**
 * Created by Dev M Hussein on 3/21/2018.
 *
 * Using MVC
 */

class Model implements Presenter.GetFollowers, Presenter.GetUserInfo {

    private View.OnFollowersResponse onFollowersResponse;
    private View.OnUserInfoResponse onUserInfoResponse;

    public Model(View.OnFollowersResponse onFollowersResponse) {
        this.onFollowersResponse = onFollowersResponse;
    }

    public Model(View.OnUserInfoResponse onUserInfoResponse) {
        this.onUserInfoResponse = onUserInfoResponse;
    }

    @Override
    public void loadFollowers(FollowersRequestType followersRequestType, long cursor) {
        TwitterSession session = TwitterCore.getInstance()
                .getSessionManager()
                .getActiveSession();
        if (session != null) {
            Call<FollowersResponse> call = ApiModel.instance(session).getFollowers()
                    .apply(session.getUserId(),
                            cursor,
                            null,
                            true,
                            true,
                            10
                    );

            call.enqueue(new Callback<FollowersResponse>() {
                @Override
                public void success(Result<FollowersResponse> result) {
                    if (result != null
                            && onFollowersResponse != null) {
                        onFollowersResponse
                                .setOnFollowersResponse(followersRequestType
                                        , result.data);
                    }
                }

                @Override
                public void failure(TwitterException exception) {
                    if (exception != null && onFollowersResponse != null) {
                        onFollowersResponse.setOnFollowersFailure(exception);
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
                    .apply(session.getUserId(),
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
