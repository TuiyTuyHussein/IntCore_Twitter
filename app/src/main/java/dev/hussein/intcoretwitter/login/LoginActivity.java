package dev.hussein.intcoretwitter.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.hussein.intcoretwitter.R;
import dev.hussein.intcoretwitter.followers.FollowersActivity;

public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.login_button)
    TwitterLoginButton twitterLoginButton;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.welcome_layout)
    LinearLayout welcomeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        TwitterSession session = TwitterCore.getInstance()
                .getSessionManager()
                .getActiveSession();
        if (session == null
                || session.getAuthToken() == null
                || session.getAuthToken().isExpired()) {
            initTwitterLogin();

        } else {
            startHomeActivity();
        }
    }



    private void initTwitterLogin() {
        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                if (result.data.getAuthToken() != null && !result.data.getAuthToken().isExpired()) {
                    showWelcomeText(result.data.getUserName());
                }
            }

            @Override
            public void failure(TwitterException exception) {
                Log.i("TAG_LOGIN", "error -> " + exception.getMessage());
            }
        });
    }


    private void showWelcomeText(String userName) {
        twitterLoginButton.setVisibility(View.GONE);
        welcomeLayout.setVisibility(View.VISIBLE);
        this.userName.setText(getString(R.string.welcome_user, userName));
        new Handler().postDelayed(this::startHomeActivity, 1500);
    }

    private void startHomeActivity() {
        startActivity(new Intent(this, FollowersActivity.class));
        supportFinishAfterTransition();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (twitterLoginButton != null)
            twitterLoginButton.onActivityResult(requestCode, resultCode, data);
    }


}
