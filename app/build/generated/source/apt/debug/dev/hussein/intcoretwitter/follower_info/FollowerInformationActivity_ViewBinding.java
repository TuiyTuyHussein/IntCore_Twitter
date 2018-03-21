// Generated code from Butter Knife. Do not modify!
package dev.hussein.intcoretwitter.follower_info;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import de.hdodenhof.circleimageview.CircleImageView;
import dev.hussein.intcoretwitter.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FollowerInformationActivity_ViewBinding implements Unbinder {
  private FollowerInformationActivity target;

  @UiThread
  public FollowerInformationActivity_ViewBinding(FollowerInformationActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public FollowerInformationActivity_ViewBinding(FollowerInformationActivity target, View source) {
    this.target = target;

    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recyclerView'", RecyclerView.class);
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.progress, "field 'progressBar'", ProgressBar.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.appBarLayout = Utils.findRequiredViewAsType(source, R.id.appBar, "field 'appBarLayout'", AppBarLayout.class);
    target.name = Utils.findRequiredViewAsType(source, R.id.name, "field 'name'", TextView.class);
    target.subtitle = Utils.findRequiredViewAsType(source, R.id.subtitle, "field 'subtitle'", TextView.class);
    target.profile = Utils.findRequiredViewAsType(source, R.id.profile, "field 'profile'", CircleImageView.class);
    target.cover = Utils.findRequiredViewAsType(source, R.id.cover, "field 'cover'", ImageView.class);
    target.refreshLayout = Utils.findRequiredViewAsType(source, R.id.swipeView, "field 'refreshLayout'", SwipeRefreshLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FollowerInformationActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerView = null;
    target.progressBar = null;
    target.toolbar = null;
    target.appBarLayout = null;
    target.name = null;
    target.subtitle = null;
    target.profile = null;
    target.cover = null;
    target.refreshLayout = null;
  }
}
