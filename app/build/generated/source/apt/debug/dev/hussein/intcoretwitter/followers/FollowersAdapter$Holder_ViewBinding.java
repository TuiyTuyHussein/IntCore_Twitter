// Generated code from Butter Knife. Do not modify!
package dev.hussein.intcoretwitter.followers;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import de.hdodenhof.circleimageview.CircleImageView;
import dev.hussein.intcoretwitter.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FollowersAdapter$Holder_ViewBinding implements Unbinder {
  private FollowersAdapter.Holder target;

  @UiThread
  public FollowersAdapter$Holder_ViewBinding(FollowersAdapter.Holder target, View source) {
    this.target = target;

    target.imageView = Utils.findRequiredViewAsType(source, R.id.profile, "field 'imageView'", CircleImageView.class);
    target.name = Utils.findRequiredViewAsType(source, R.id.name, "field 'name'", TextView.class);
    target.bio = Utils.findRequiredViewAsType(source, R.id.bio, "field 'bio'", TextView.class);
    target.item = Utils.findRequiredViewAsType(source, R.id.item, "field 'item'", CardView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FollowersAdapter.Holder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imageView = null;
    target.name = null;
    target.bio = null;
    target.item = null;
  }
}
