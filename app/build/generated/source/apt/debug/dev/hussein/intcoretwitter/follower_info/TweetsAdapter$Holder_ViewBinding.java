// Generated code from Butter Knife. Do not modify!
package dev.hussein.intcoretwitter.follower_info;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.FrameLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import dev.hussein.intcoretwitter.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TweetsAdapter$Holder_ViewBinding implements Unbinder {
  private TweetsAdapter.Holder target;

  @UiThread
  public TweetsAdapter$Holder_ViewBinding(TweetsAdapter.Holder target, View source) {
    this.target = target;

    target.container = Utils.findRequiredViewAsType(source, R.id.container, "field 'container'", FrameLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    TweetsAdapter.Holder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.container = null;
  }
}
