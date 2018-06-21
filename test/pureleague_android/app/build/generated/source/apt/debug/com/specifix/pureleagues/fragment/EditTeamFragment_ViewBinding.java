// Generated code from Butter Knife. Do not modify!
package com.specifix.pureleagues.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.specifix.pureleagues.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class EditTeamFragment_ViewBinding implements Unbinder {
  private EditTeamFragment target;

  private View view2131689787;

  private View view2131689780;

  private View view2131689709;

  private View view2131689710;

  private View view2131689711;

  private View view2131689712;

  private View view2131689713;

  @UiThread
  public EditTeamFragment_ViewBinding(final EditTeamFragment target, View source) {
    this.target = target;

    View view;
    target.mTitle = Utils.findRequiredViewAsType(source, R.id.edit_team_title, "field 'mTitle'", TextView.class);
    target.mName = Utils.findRequiredViewAsType(source, R.id.edit_team_club_name, "field 'mName'", TextView.class);
    target.mDivision = Utils.findRequiredViewAsType(source, R.id.edit_team_division, "field 'mDivision'", TextView.class);
    target.mShirt = Utils.findRequiredViewAsType(source, R.id.edit_team_shirt_image, "field 'mShirt'", ImageView.class);
    target.mShirtLabel = Utils.findRequiredViewAsType(source, R.id.edit_team_shirt_label, "field 'mShirtLabel'", TextView.class);
    target.mNotifSwitch = Utils.findRequiredViewAsType(source, R.id.edit_team_notifications_switch, "field 'mNotifSwitch'", SwitchCompat.class);
    target.mNotifLabel = Utils.findRequiredViewAsType(source, R.id.edit_team_notifications_label, "field 'mNotifLabel'", TextView.class);
    target.mAllowNotifLabel = Utils.findRequiredViewAsType(source, R.id.edit_team_allow_notif_label, "field 'mAllowNotifLabel'", TextView.class);
    view = Utils.findRequiredView(source, R.id.edit_team_delete_button, "field 'mDeleteBtn' and method 'onDeleteClick'");
    target.mDeleteBtn = Utils.castView(view, R.id.edit_team_delete_button, "field 'mDeleteBtn'", Button.class);
    view2131689787 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onDeleteClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.edit_team_shirt_picker_layout, "method 'onShirtClick'");
    view2131689780 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onShirtClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.footer_facebook_icon, "method 'onSocialClick'");
    view2131689709 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSocialClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.footer_twitter_icon, "method 'onSocialClick'");
    view2131689710 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSocialClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.footer_instagram_icon, "method 'onSocialClick'");
    view2131689711 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSocialClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.footer_youtube_icon, "method 'onSocialClick'");
    view2131689712 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSocialClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.footer_snapchat_icon, "method 'onSocialClick'");
    view2131689713 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSocialClick(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    EditTeamFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mTitle = null;
    target.mName = null;
    target.mDivision = null;
    target.mShirt = null;
    target.mShirtLabel = null;
    target.mNotifSwitch = null;
    target.mNotifLabel = null;
    target.mAllowNotifLabel = null;
    target.mDeleteBtn = null;

    view2131689787.setOnClickListener(null);
    view2131689787 = null;
    view2131689780.setOnClickListener(null);
    view2131689780 = null;
    view2131689709.setOnClickListener(null);
    view2131689709 = null;
    view2131689710.setOnClickListener(null);
    view2131689710 = null;
    view2131689711.setOnClickListener(null);
    view2131689711 = null;
    view2131689712.setOnClickListener(null);
    view2131689712 = null;
    view2131689713.setOnClickListener(null);
    view2131689713 = null;
  }
}
