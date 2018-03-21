package dev.hussein.intcoretwitter.followers;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.models.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import dev.hussein.intcoretwitter.R;
import dev.hussein.intcoretwitter.follower_info.FollowerInformationActivity;

/**
 * Created by Dev M Hussein on 3/21/2018.
 */

class FollowersAdapter extends RecyclerView.Adapter<FollowersAdapter.Holder> {

    private Context context;
    private List<User> users;

    public FollowersAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        android.view.View view = LayoutInflater.from(context).inflate(R.layout.item_follower, parent, false);
        Holder holder = new Holder(view);
        holder.itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        User user = users.get(position);
        holder.name.setText(user.name);
        holder.bio.setText(user.description);
        holder.bio.setVisibility(user.description == null || TextUtils.isEmpty(user.description)
                ? View.GONE : View.VISIBLE);


        String profileLink = user.profileImageUrl.replace("normal", "400x400");


        Picasso.with(context)
                .load(profileLink)
                .placeholder(R.mipmap.default_user)
                .error(R.mipmap.default_user)
                .into(holder.imageView);

        holder.item.setTag(R.id.user_id, user.id);
        holder.item.setOnClickListener(v -> {
            long id = (long) v.getTag(R.id.user_id);
            Intent intent = new Intent(context, FollowerInformationActivity.class);
            intent.putExtra(FollowerInformationActivity.TAG_USER_ID, id);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return users == null ? 0 : users.size();
    }


    class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.profile)
        CircleImageView imageView;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.bio)
        TextView bio;
        @BindView(R.id.item)
        CardView item;

        public Holder(android.view.View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
