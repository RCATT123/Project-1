// Generated code from Butter Knife. Do not modify!
package com.specifix.pureleagues.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.specifix.pureleagues.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RegisterContinueActivity_ViewBinding implements Unbinder {
  private RegisterContinueActivity target;

  private View view2131689641;

  private View view2131689645;

  private View view2131689644;

  private View view2131689646;

  private View view2131689642;

  private View view2131689647;

  @UiThread
  public RegisterContinueActivity_ViewBinding(RegisterContinueActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public RegisterContinueActivity_ViewBinding(final RegisterContinueActivity target, View source) {
    this.target = target;

    View view;
    target.mAssociationLayout = Utils.findRequiredViewAsType(source, R.id.register_continue_association_layout, "field 'mAssociationLayout'", LinearLayout.class);
    target.mSubmitLayout = Utils.findRequiredViewAsType(source, R.id.register_continue_submit_layout, "field 'mSubmitLayout'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.register_continue_association_picker, "field 'mAssociationPicker' and method 'onAssociationClick'");
    target.mAssociationPicker = Utils.castView(view, R.id.register_continue_association_picker, "field 'mAssociationPicker'", TextView.class);
    view2131689641 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onAssociationClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.register_continue_club_textview, "field 'mClubPicker' and method 'onClubClick'");
    target.mClubPicker = Utils.castView(view, R.id.register_continue_club_textview, "field 'mClubPicker'", TextView.class);
    view2131689645 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClubClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.register_continue_division_text_view, "field 'mDivisionPicker' and method 'onDivisionClick'");
    target.mDivisionPicker = Utils.castView(view, R.id.register_continue_division_text_view, "field 'mDivisionPicker'", TextView.class);
    view2131689644 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onDivisionClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.register_continue_team_colors, "field 'mTeamColorPicker' and method 'onTeamColorsClick'");
    target.mTeamColorPicker = Utils.castView(view, R.id.register_continue_team_colors, "field 'mTeamColorPicker'", TextView.class);
    view2131689646 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onTeamColorsClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.register_continue_next_button, "field 'mNextBtn' and method 'onContinueClick'");
    target.mNextBtn = Utils.castView(view, R.id.register_continue_next_button, "field 'mNextBtn'", Button.class);
    view2131689642 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onContinueClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.register_continue_submit_button, "field 'mSubmitBtn' and method 'onSubmitClick'");
    target.mSubmitBtn = Utils.castView(view, R.id.register_continue_submit_button, "field 'mSubmitBtn'", Button.class);
    view2131689647 = view;
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
    RegisterContinueActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mAssociationLayout = null;
    target.mSubmitLayout = null;
    target.mAssociationPicker = null;
    target.mClubPicker = null;
    target.mDivisionPicker = null;
    target.mTeamColorPicker = null;
    target.mNextBtn = null;
    target.mSubmitBtn = null;

    view2131689641.setOnClickListener(null);
    view2131689641 = null;
    view2131689645.setOnClickListener(null);
    view2131689645 = null;
    view2131689644.setOnClickListener(null);
    view2131689644 = null;
    view2131689646.setOnClickListener(null);
    view2131689646 = null;
    view2131689642.setOnClickListener(null);
    view2131689642 = null;
    view2131689647.setOnClickListener(null);
    view2131689647 = null;
  }
}
