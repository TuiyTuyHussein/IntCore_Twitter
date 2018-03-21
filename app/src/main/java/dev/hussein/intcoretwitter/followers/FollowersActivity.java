package dev.hussein.intcoretwitter.followers;

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
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import dev.hussein.intcoretwitter.R;
import dev.hussein.intcoretwitter.config.CacheUtility;
import dev.hussein.intcoretwitter.config.EndlessRecyclerOnScrollListener;
import dev.hussein.intcoretwitter.config.FollowersRequestType;
import dev.hussein.intcoretwitter.config.Utility;
import dev.hussein.intcoretwitter.pojo.FollowersResponse;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FollowersActivity extends AppCompatActivity
        implements View.OnFollowersResponse, View.OnUserInfoResponse
        , SwipeRefreshLayout.OnRefreshListener {

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
    @BindView(R.id.profile)
    CircleImageView profile;
    @BindView(R.id.cover)
    ImageView cover;
    @BindView(R.id.swipeView)
    SwipeRefreshLayout refreshLayout;
    private Presenter.GetFollowers followerPresenter;
    private Presenter.GetUserInfo userInfoPresenter;
    private String userName;
    private long nextCursor = -1;
    private long firstCursor = -1;
    private List<User> followers = new LinkedList<>();
    private FollowersAdapter followerAdapter;
    private EndlessRecyclerOnScrollListener endlessScroller;
    private User activeUser;
    private boolean isLandScape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        followerPresenter = new Model((View.OnFollowersResponse) this);
        userInfoPresenter = new Model((View.OnUserInfoResponse) this);
        isLandScape = getResources().getBoolean(R.bool.landScape);
        setupViews();
        loadFollowers(FollowersRequestType.NEXT, nextCursor);
    }

    private void setupViews() {
        getUserInfo();
        appBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (Math.abs(verticalOffset) - appBarLayout.getTotalScrollRange() == 0) {
                //  Collapsed
                toolbar.setTitle(userName);
                toolbar.setSubtitle(R.string.followers);
            } else {
                //Expanded
                toolbar.setTitle("");
                toolbar.setSubtitle("");
            }
        });
        toolbar.setNavigationOnClickListener(v -> {
            supportFinishAfterTransition();
        });
        name.setText(userName);

        refreshLayout.setOnRefreshListener(this);

        if (isLandScape)
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2
                    , StaggeredGridLayoutManager.VERTICAL));
        else
            recyclerView.setLayoutManager(new LinearLayoutManager(this
                    , LinearLayoutManager.VERTICAL, false));

        followerAdapter = new FollowersAdapter(this, followers);
        recyclerView.setAdapter(followerAdapter);
        endlessScroller = new EndlessRecyclerOnScrollListener(recyclerView) {
            @Override
            public void onLoadMore(int current_page) {
                loadFollowers(FollowersRequestType.NEXT, nextCursor);
            }
        };
    }

    private void inflateUserData() {
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
        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        if (session != null) {
            userName = session.getUserName();
            userInfoPresenter.loadUserInfo(session.getUserId());
        }
    }


    private void playProgress(boolean play) {
        if (!play) refreshLayout.setRefreshing(false);
        progressBar.setIndeterminate(play);
        progressBar.setVisibility(play ? android.view.View.VISIBLE : android.view.View.GONE);
    }

    private void loadFollowers(FollowersRequestType followersRequestType, long cursor) {
        playProgress(true);
        endlessScroller.stop();
        if (Utility.isNetworkAvailable(this))
            followerPresenter.loadFollowers(followersRequestType, cursor);
        else
            getCashedData();
    }

    private void getCashedData() {
        playProgress(false);

        Observable<FollowersResponse> observable =
                CacheUtility.instance(this).getCachedFollowersResponse();
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(followersResponse -> {
                    if (followersResponse != null) {
                        nextCursor = followersResponse.next_cursor;
                        firstCursor = followersResponse.previous_cursor;
                        followers.addAll(followersResponse.followers);
                        followerAdapter.notifyDataSetChanged();
                    }
                }, error -> {

                });

    }


    @Override
    public void setOnFollowersResponse(FollowersRequestType followersRequestType
            , FollowersResponse response) {
        playProgress(false);
        if (response != null && response.followers != null && response.followers.size() > 0) {
            nextCursor = response.next_cursor;
            if (this.followers.size() == 0
                    || followersRequestType.equals(FollowersRequestType.NEW)) {
                firstCursor = response.previous_cursor;
            }
            if (followersRequestType.equals(FollowersRequestType.NEXT)) {
                int last = followers.size();
                this.followers.addAll(response.followers);
                followerAdapter.notifyItemRangeInserted(last, response.followers.size());
            } else {
                this.followers.addAll(0, response.followers);
                followerAdapter.notifyItemRangeInserted(0, response.followers.size());
            }
            endlessScroller.play();
            cacheFollowers();
        } else {
            String message = "";
            if (!followersRequestType.equals(FollowersRequestType.NEW)) {
                message = getString(R.string.no_more_followers);
                endlessScroller.stop();
            } else {
                message = getString(R.string.followers_up_tp_date);
                endlessScroller.play();
            }

            if (getCurrentFocus() != null)
                Snackbar.make(getCurrentFocus(), message, Snackbar.LENGTH_LONG).show();
            Log.i("FOLLOWER_TAG", "NULL Response");
        }
    }

    private void cacheFollowers() {
        FollowersResponse followersResponse = new FollowersResponse();
        followersResponse.followers = followers;
        followersResponse.previous_cursor = firstCursor;
        followersResponse.next_cursor = nextCursor;
        CacheUtility.instance(this).cacheFollowersResponse(followersResponse);
    }

    @Override
    public void setOnFollowersFailure(Throwable failure) {
        playProgress(false);
        endlessScroller.stop();
        if (getCurrentFocus() != null)
            Snackbar.make(getCurrentFocus(), "error -> " + failure.getMessage(), Snackbar.LENGTH_LONG).show();
        Log.i("FOLLOWER_TAG", "error -> " + failure.getMessage());
    }

    @Override
    public void setOnUserInfoResponse(User user) {
        if (user != null) {
            this.activeUser = user;
            inflateUserData();
        } else {
            endlessScroller.stop();
            Log.i("FOLLOWER_TAG", "NULL Response");
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
        loadFollowers(FollowersRequestType.NEW, firstCursor);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
