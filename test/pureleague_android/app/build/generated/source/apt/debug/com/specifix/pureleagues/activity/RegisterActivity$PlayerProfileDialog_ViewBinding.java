// Generated code from Butter Knife. Do not modify!
package com.specifix.pureleagues.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.specifix.pureleagues.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RegisterActivity$PlayerProfileDialog_ViewBinding implements Unbinder {
  private RegisterActivity.PlayerProfileDialog target;

  private View view2131689707;

  @UiThread
  public RegisterActivity$PlayerProfileDialog_ViewBinding(RegisterActivity.PlayerProfileDialog target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public RegisterActivity$PlayerProfileDialog_ViewBinding(final RegisterActivity.PlayerProfileDialog target,
      View source) {
    this.target = target;

    View view;
    target.mHeightLabel = Utils.findRequiredViewAsType(source, R.id.profile_height_label, "field 'mHeightLabel'", TextView.class);
    target.mWeightLabel = Utils.findRequiredViewAsType(source, R.id.profile_weight_label, "field 'mWeightLabel'", TextView.class);
    target.mPositionLabel = Utils.findRequiredViewAsType(source, R.id.profile_position_label, "field 'mPositionLabel'", TextView.class);
    target.mProfileLabel = Utils.findRequiredViewAsType(source, R.id.profile_label, "field 'mProfileLabel'", TextView.class);
    target.mHeightEditText = Utils.findRequiredViewAsType(source, R.id.profile_height_edit_text, "field 'mHeightEditText'", EditText.class);
    target.mWeightEditText = Utils.findRequiredViewAsType(source, R.id.profile_weight_edit_text, "field 'mWeightEditText'", EditText.class);
    target.mPositionEditText = Utils.findRequiredViewAsType(source, R.id.profile_position_edit_text, "field 'mPositionEditText'", EditText.class);
    target.mProfileEditText = Utils.findRequiredViewAsType(source, R.id.profile_edit_text, "field 'mProfileEditText'", EditText.class);
    view = Utils.findRequiredView(source, R.id.profile_submit_button, "field 'mSubmitBtn' and method 'onSubmitClick'");
    target.mSubmitBtn = Utils.castView(view, R.id.profile_submit_button, "field 'mSubmitBtn'", Button.class);
    view2131689707 = view;
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
    RegisterActivity.PlayerProfileDialog target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mHeightLabel = null;
    target.mWeightLabel = null;
    target.mPositionLabel = null;
    target.mProfileLabel = null;
    target.mHeightEditText = null;
    target.mWeightEditText = null;
    target.mPositionEditText = null;
    target.mProfileEditText = null;
    target.mSubmitBtn = null;

    view2131689707.setOnClickListener(null);
    view2131689707 = null;
  }
}
