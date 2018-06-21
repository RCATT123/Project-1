// Generated code from Butter Knife. Do not modify!
package com.specifix.pureleagues.fragment;

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

public class ContactUsFragment_ViewBinding implements Unbinder {
  private ContactUsFragment target;

  private View view2131689728;

  private View view2131689709;

  private View view2131689710;

  private View view2131689711;

  private View view2131689712;

  private View view2131689713;

  @UiThread
  public ContactUsFragment_ViewBinding(final ContactUsFragment target, View source) {
    this.target = target;

    View view;
    target.mTitle = Utils.findRequiredViewAsType(source, R.id.contact_us_title, "field 'mTitle'", TextView.class);
    target.mName = Utils.findRequiredViewAsType(source, R.id.contact_us_name, "field 'mName'", EditText.class);
    target.mReason = Utils.findRequiredViewAsType(source, R.id.contact_us_reason_edit_text, "field 'mReason'", EditText.class);
    target.mPhone = Utils.findRequiredViewAsType(source, R.id.contact_us_phone, "field 'mPhone'", EditText.class);
    target.mEmail = Utils.findRequiredViewAsType(source, R.id.contact_us_email, "field 'mEmail'", EditText.class);
    target.mMessage = Utils.findRequiredViewAsType(source, R.id.contact_us_message, "field 'mMessage'", EditText.class);
    view = Utils.findRequiredView(source, R.id.contact_us_submit_button, "field 'mSubmitBtn' and method 'onSubmitClick'");
    target.mSubmitBtn = Utils.castView(view, R.id.contact_us_submit_button, "field 'mSubmitBtn'", Button.class);
    view2131689728 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSubmitClick();
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
    ContactUsFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mTitle = null;
    target.mName = null;
    target.mReason = null;
    target.mPhone = null;
    target.mEmail = null;
    target.mMessage = null;
    target.mSubmitBtn = null;

    view2131689728.setOnClickListener(null);
    view2131689728 = null;
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
