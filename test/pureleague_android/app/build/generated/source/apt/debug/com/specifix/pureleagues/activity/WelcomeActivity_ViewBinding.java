// Generated code from Butter Knife. Do not modify!
package com.specifix.pureleagues.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.specifix.pureleagues.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class WelcomeActivity_ViewBinding implements Unbinder {
  private WelcomeActivity target;

  private View view2131689659;

  private View view2131689660;

  @UiThread
  public WelcomeActivity_ViewBinding(WelcomeActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public WelcomeActivity_ViewBinding(final WelcomeActivity target, View source) {
    this.target = target;

    View view;
    target.mTitle = Utils.findRequiredViewAsType(source, R.id.welcome_title, "field 'mTitle'", TextView.class);
    target.mSubtitle = Utils.findRequiredViewAsType(source, R.id.welcome_subtitle_textview, "field 'mSubtitle'", TextView.class);
    view = Utils.findRequiredView(source, R.id.welcome_login_button, "field 'mLoginBtn' and method 'onLoginClick'");
    target.mLoginBtn = Utils.castView(view, R.id.welcome_login_button, "field 'mLoginBtn'", Button.class);
    view2131689659 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onLoginClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.welcome_add_team_button, "field 'mAddTeamBtn' and method 'onAddTeamClick'");
    target.mAddTeamBtn = Utils.castView(view, R.id.welcome_add_team_button, "field 'mAddTeamBtn'", Button.class);
    view2131689660 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onAddTeamClick();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    WelcomeActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mTitle = null;
    target.mSubtitle = null;
    target.mLoginBtn = null;
    target.mAddTeamBtn = null;

    view2131689659.setOnClickListener(null);
    view2131689659 = null;
    view2131689660.setOnClickListener(null);
    view2131689660 = null;
  }
}
