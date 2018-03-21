package dev.hussein.intcoretwitter.follower_info;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.stfalcon.frescoimageviewer.ImageViewer;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import dev.hussein.intcoretwitter.R;
import dev.hussein.intcoretwitter.config.Utility;

public class FollowerInformationActivity extends AppCompatActivity
        implements View.OnFollowerTweetsResponse, View.OnUserInfoResponse
        , SwipeRefreshLayout.OnRefreshListener {


    public static final String TAG_USER_ID = "TAG_USER_ID";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appBar)
    AppBarLayout appBarLayout;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.subtitle)
    TextView subtitle;
    @BindView(R.id.profile)
    CircleImageView profile;
    @BindView(R.id.cover)
    ImageView cover;
    @BindView(R.id.swipeView)
    SwipeRefreshLayout refreshLayout;
    private String userName;
    private List<Tweet> tweets = new LinkedList<>();
    private TweetsAdapter tweetsAdapter;
    private Presenter.GetTweets tweetsPresenter;
    private Presenter.GetUserInfo userInfoPresenter;
    private User activeUser;
    private boolean isLandScape;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        tweetsPresenter = new Model((View.OnFollowerTweetsResponse) this);
        userInfoPresenter = new Model((View.OnUserInfoResponse) this);
        isLandScape = getResources().getBoolean(R.bool.landScape);
        setupViews();
        loadFollowers();
    }

    private void setupViews() {
        getUserInfo();
        appBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (Math.abs(verticalOffset) - appBarLayout.getTotalScrollRange() == 0) {
                //  Collapsed
                toolbar.setTitle(userName);
                toolbar.setSubtitle(R.string.tweets);
            } else {
                //Expanded
                toolbar.setTitle("");
                toolbar.setSubtitle("");
            }
        });
        toolbar.setNavigationOnClickListener(v -> {
            supportFinishAfterTransition();
        });
        subtitle.setText(R.string.tweets);
        name.setText(userName);

        refreshLayout.setOnRefreshListener(this);

        if (isLandScape)
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2
                    , StaggeredGridLayoutManager.VERTICAL));
        else
            recyclerView.setLayoutManager(new LinearLayoutManager(this
                    , LinearLayoutManager.VERTICAL, false));

        tweetsAdapter = new TweetsAdapter(this, tweets);
        recyclerView.setAdapter(tweetsAdapter);

    }

    private void inflateUserData() {
        userName = activeUser.name;
        this.name.setText(userName);
        if (activeUser.profileImageUrl != null && !TextUtils.isEmpty(activeUser.profileImageUrl)) {
            String profileLink = activeUser.profileImageUrl.replace("normal", "400x400");
            Picasso.with(this).load(profileLink)
                    .placeholder(R.mipmap.default_user)
                    .error(R.mipmap.default_user)
                    .into(profile);
            profile.setOnClickListener(v -> playZoomingView(profileLink));
            Log.i("IMAGE_TAG", "profile http -> " + activeUser.profileImageUrlHttps);
            Log.i("IMAGE_TAG", "profile url -> " + activeUser.profileImageUrl);
        }
        Log.i("IMAGE_TAG", "banner url -> " + activeUser.profileBannerUrl);

        if (activeUser.profileBannerUrl != null && !TextUtils.isEmpty(activeUser.profileBannerUrl)) {
            Picasso.with(this).load(activeUser.profileBannerUrl)
                    .into(cover);

            cover.setOnClickListener(v -> playZoomingView(activeUser.profileBannerUrl));
            Log.i("IMAGE_TAG", "background http -> " + activeUser.profileBackgroundImageUrlHttps);
            Log.i("IMAGE_TAG", "background url -> " + activeUser.profileBackgroundImageUrl);
        }
    }

    private void playZoomingView(String imageUrl) {
        List<String> images = new LinkedList<>();
        images.add(imageUrl);
        new ImageViewer.Builder<>(this, images)
                .setFormatter(url -> url)
                .hideStatusBar(true)
                .allowZooming(true)
                .allowSwipeToDismiss(true)
                .setBackgroundColorRes(R.color.bg_dark)
                .show();
    }

    private void getUserInfo() {
        userId = getIntent().getLongExtra(TAG_USER_ID, -1);
        userInfoPresenter.loadUserInfo(userId);
    }


    private void playProgress(boolean play) {
        if (!play) refreshLayout.setRefreshing(false);
        progressBar.setIndeterminate(play);
        progressBar.setVisibility(play ? android.view.View.VISIBLE : android.view.View.GONE);
    }

    private void loadFollowers() {
        playProgress(true);
        if (Utility.isNetworkAvailable(this))
            tweetsPresenter.loadTweets(userId, 10);
        else if (getCurrentFocus() != null)
            Snackbar.make(getCurrentFocus(), R.string.no_internet_connection, Snackbar.LENGTH_LONG).show();

    }


    @Override
    public void setOnFollowerTweetsResponse(Tweet[] response) {
        playProgress(false);
        if (response != null && response.length > 0) {

            int last = tweets.size();
            for (int i = 0; i < response.length; i++) {
                Log.i("TWEETS_TAG", "id -> " + response[i].id);
                this.tweets.add(response[i]);
            }

            tweetsAdapter.notifyItemRangeInserted(last, response.length);

        } else {
            Log.i("TWEETS_TAG", "NULL Response");
        }
    }


    @Override
    public void setOnFollowerTweetsFailure(Throwable failure) {
        playProgress(false);
        if (getCurrentFocus() != null)
            Snackbar.make(getCurrentFocus(), "error -> " + failure.getMessage(), Snackbar.LENGTH_LONG).show();
        Log.i("TWEETS_TAG", "error -> " + failure.getMessage());
    }

    @Override
    public void setOnUserInfoResponse(User user) {
        if (user != null) {
            this.activeUser = user;
            inflateUserData();
        } else {
            Log.i("USER_INFO_TAG", "NULL Response");
        }
    }


    @Override
    public void setOnUserInfoFailure(Throwable failure) {
        if (getCurrentFocus() != null)
            Snackbar.make(getCurrentFocus(), "error -> " + failure.getMessage(), Snackbar.LENGTH_LONG).show();
        Log.i("USER_INFO_TAG", "error -> " + failure.getMessage());
    }

    @Override
    public void onRefresh() {
        if (tweets.size() == 0)
            loadFollowers();
        else
            playProgress(false);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
