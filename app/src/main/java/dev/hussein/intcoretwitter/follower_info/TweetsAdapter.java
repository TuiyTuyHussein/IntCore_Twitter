package dev.hussein.intcoretwitter.follower_info;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TweetView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.hussein.intcoretwitter.R;

/**
 * Created by Dev M Hussein on 3/21/2018.
 */

class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.Holder> {

    private Context context;
    private List<Tweet> tweets;

    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        Holder holder = new Holder(view);
        holder.itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Tweet tweet = tweets.get(position);
        if (holder.container.getChildCount() > 0)
            holder.container.removeAllViews();
        TweetView tweetView = new TweetView(context, tweet, R.style.tw__TweetDarkWithActionsStyle);
        tweetView.setBackgroundResource(R.color.bg_card);
        holder.container.addView(tweetView);
    }

    @Override
    public int getItemCount() {
        return tweets == null ? 0 : tweets.size();
    }


    class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.container)
        FrameLayout container;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
