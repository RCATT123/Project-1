// Generated code from Butter Knife. Do not modify!
package com.specifix.pureleagues.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.specifix.pureleagues.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  private View view2131689608;

  private View view2131689609;

  private View view2131689610;

  private View view2131689614;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(final LoginActivity target, View source) {
    this.target = target;

    View view;
    target.mToolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'mToolbar'", Toolbar.class);
    target.mTitle = Utils.findRequiredViewAsType(source, R.id.login_screen_title, "field 'mTitle'", TextView.class);
    target.mLoginLayout = Utils.findRequiredViewAsType(source, R.id.login_layout, "field 'mLoginLayout'", LinearLayout.class);
    target.mUsernameEditText = Utils.findRequiredViewAsType(source, R.id.login_username_edittext, "field 'mUsernameEditText'", EditText.class);
    target.mPasswordEditText = Utils.findRequiredViewAsType(source, R.id.login_password_edittext, "field 'mPasswordEditText'", EditText.class);
    view = Utils.findRequiredView(source, R.id.login_forgot_password_textview, "field 'mForgotPassTextView' and method 'onForgotPasswordClick'");
    target.mForgotPassTextView = Utils.castView(view, R.id.login_forgot_password_textview, "field 'mForgotPassTextView'", TextView.class);
    view2131689608 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onForgotPasswordClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.login_register_text_view, "field 'mRegisterTextView' and method 'onRegisterClick'");
    target.mRegisterTextView = Utils.castView(view, R.id.login_register_text_view, "field 'mRegisterTextView'", TextView.class);
    view2131689609 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onRegisterClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.login_submit_button, "field 'mSubmitBtn' and method 'onSubmitClick'");
    target.mSubmitBtn = Utils.castView(view, R.id.login_submit_button, "field 'mSubmitBtn'", Button.class);
    view2131689610 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSubmitClick();
      }
    });
    target.mEmailLayout = Utils.findRequiredViewAsType(source, R.id.login_email_layout, "field 'mEmailLayout'", LinearLayout.class);
    target.mEmailEditText = Utils.findRequiredViewAsType(source, R.id.login_recovery_email_edittext, "field 'mEmailEditText'", EditText.class);
    view = Utils.findRequiredView(source, R.id.login_email_submit_button, "field 'mEmailSubmitBtn' and method 'onEmailResetClick'");
    target.mEmailSubmitBtn = Utils.castView(view, R.id.login_email_submit_button, "field 'mEmailSubmitBtn'", Button.class);
    view2131689614 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onEmailResetClick();
      }
    });
    target.mPassRecoveryTextView = Utils.findRequiredViewAsType(source, R.id.login_password_recovery_text, "field 'mPassRecoveryTextView'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mToolbar = null;
    target.mTitle = null;
    target.mLoginLayout = null;
    target.mUsernameEditText = null;
    target.mPasswordEditText = null;
    target.mForgotPassTextView = null;
    target.mRegisterTextView = null;
    target.mSubmitBtn = null;
    target.mEmailLayout = null;
    target.mEmailEditText = null;
    target.mEmailSubmitBtn = null;
    target.mPassRecoveryTextView = null;

    view2131689608.setOnClickListener(null);
    view2131689608 = null;
    view2131689609.setOnClickListener(null);
    view2131689609 = null;
    view2131689610.setOnClickListener(null);
    view2131689610 = null;
    view2131689614.setOnClickListener(null);
    view2131689614 = null;
  }
}
