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

public class RegisterActivity_ViewBinding implements Unbinder {
  private RegisterActivity target;

  private View view2131689632;

  private View view2131689634;

  private View view2131689635;

  private View view2131689636;

  private View view2131689637;

  @UiThread
  public RegisterActivity_ViewBinding(RegisterActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public RegisterActivity_ViewBinding(final RegisterActivity target, View source) {
    this.target = target;

    View view;
    target.mToolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'mToolbar'", Toolbar.class);
    target.mRegisterTitle = Utils.findRequiredViewAsType(source, R.id.register_screen_title, "field 'mRegisterTitle'", TextView.class);
    target.mPartOneLayout = Utils.findRequiredViewAsType(source, R.id.register_part_one, "field 'mPartOneLayout'", LinearLayout.class);
    target.mPartTwoLayout = Utils.findRequiredViewAsType(source, R.id.register_part_two, "field 'mPartTwoLayout'", LinearLayout.class);
    target.mName = Utils.findRequiredViewAsType(source, R.id.registration_name, "field 'mName'", EditText.class);
    target.mUserName = Utils.findRequiredViewAsType(source, R.id.registration_username, "field 'mUserName'", EditText.class);
    target.mEmail = Utils.findRequiredViewAsType(source, R.id.registration_email, "field 'mEmail'", EditText.class);
    target.mPassword = Utils.findRequiredViewAsType(source, R.id.registration_password, "field 'mPassword'", EditText.class);
    target.mConfirmPassword = Utils.findRequiredViewAsType(source, R.id.registration_confirm_password, "field 'mConfirmPassword'", EditText.class);
    view = Utils.findRequiredView(source, R.id.registration_continue_button, "field 'mContinueBtn' and method 'onContinueClick'");
    target.mContinueBtn = Utils.castView(view, R.id.registration_continue_button, "field 'mContinueBtn'", Button.class);
    view2131689632 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onContinueClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.register_date_picker, "field 'mDateTextView' and method 'onDatePickerClick'");
    target.mDateTextView = Utils.castView(view, R.id.register_date_picker, "field 'mDateTextView'", TextView.class);
    view2131689634 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onDatePickerClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.register_gender_picker, "field 'mGenderTextView' and method 'onGenderPickerClick'");
    target.mGenderTextView = Utils.castView(view, R.id.register_gender_picker, "field 'mGenderTextView'", TextView.class);
    view2131689635 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onGenderPickerClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.register_user_type, "field 'mUserTypeTextView' and method 'onUserTypeClick'");
    target.mUserTypeTextView = Utils.castView(view, R.id.register_user_type, "field 'mUserTypeTextView'", TextView.class);
    view2131689636 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onUserTypeClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.register_submit_button, "field 'mSubmitBtn' and method 'onSubmitClick'");
    target.mSubmitBtn = Utils.castView(view, R.id.register_submit_button, "field 'mSubmitBtn'", Button.class);
    view2131689637 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSubmitClick();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    RegisterActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mToolbar = null;
    target.mRegisterTitle = null;
    target.mPartOneLayout = null;
    target.mPartTwoLayout = null;
    target.mName = null;
    target.mUserName = null;
    target.mEmail = null;
    target.mPassword = null;
    target.mConfirmPassword = null;
    target.mContinueBtn = null;
    target.mDateTextView = null;
    target.mGenderTextView = null;
    target.mUserTypeTextView = null;
    target.mSubmitBtn = null;

    view2131689632.setOnClickListener(null);
    view2131689632 = null;
    view2131689634.setOnClickListener(null);
    view2131689634 = null;
    view2131689635.setOnClickListener(null);
    view2131689635 = null;
    view2131689636.setOnClickListener(null);
    view2131689636 = null;
    view2131689637.setOnClickListener(null);
    view2131689637 = null;
  }
}
