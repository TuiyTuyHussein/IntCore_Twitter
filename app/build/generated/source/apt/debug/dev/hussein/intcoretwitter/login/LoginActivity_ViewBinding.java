// Generated code from Butter Knife. Do not modify!
package dev.hussein.intcoretwitter.login;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import dev.hussein.intcoretwitter.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target, View source) {
    this.target = target;

    target.twitterLoginButton = Utils.findRequiredViewAsType(source, R.id.login_button, "field 'twitterLoginButton'", TwitterLoginButton.class);
    target.userName = Utils.findRequiredViewAsType(source, R.id.user_name, "field 'userName'", TextView.class);
    target.welcomeLayout = Utils.findRequiredViewAsType(source, R.id.welcome_layout, "field 'welcomeLayout'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.twitterLoginButton = null;
    target.userName = null;
    target.welcomeLayout = null;
  }
}
