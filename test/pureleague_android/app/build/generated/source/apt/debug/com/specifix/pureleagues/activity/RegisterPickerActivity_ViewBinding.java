// Generated code from Butter Knife. Do not modify!
package com.specifix.pureleagues.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.specifix.pureleagues.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RegisterPickerActivity_ViewBinding implements Unbinder {
  private RegisterPickerActivity target;

  private View view2131689709;

  private View view2131689710;

  private View view2131689711;

  private View view2131689712;

  private View view2131689713;

  @UiThread
  public RegisterPickerActivity_ViewBinding(RegisterPickerActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public RegisterPickerActivity_ViewBinding(final RegisterPickerActivity target, View source) {
    this.target = target;

    View view;
    target.mLayout = Utils.findRequiredViewAsType(source, R.id.register_picker_layout, "field 'mLayout'", ViewGroup.class);
    target.mToolbar = Utils.findRequiredViewAsType(source, R.id.register_picker_toolbar, "field 'mToolbar'", Toolbar.class);
    target.mPickerTitle = Utils.findRequiredViewAsType(source, R.id.register_picker_title, "field 'mPickerTitle'", TextView.class);
    target.mSearchLayout = Utils.findRequiredViewAsType(source, R.id.register_picker_search_layout, "field 'mSearchLayout'", LinearLayout.class);
    target.mSearchEditText = Utils.findRequiredViewAsType(source, R.id.register_picker_search_edittext, "field 'mSearchEditText'", EditText.class);
    target.mList = Utils.findRequiredViewAsType(source, R.id.register_picker_list, "field 'mList'", RecyclerView.class);
    target.mLoadingView = Utils.findRequiredViewAsType(source, R.id.register_picker_progress_bar, "field 'mLoadingView'", ProgressBar.class);
    view = Utils.findRequiredView(source, R.id.footer_facebook_icon, "field 'mFacebookBth' and method 'onSocialClick'");
    target.mFacebookBth = Utils.castView(view, R.id.footer_facebook_icon, "field 'mFacebookBth'", ImageView.class);
    view2131689709 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSocialClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.footer_twitter_icon, "field 'mTwitterBtn' and method 'onSocialClick'");
    target.mTwitterBtn = Utils.castView(view, R.id.footer_twitter_icon, "field 'mTwitterBtn'", ImageView.class);
    view2131689710 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSocialClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.footer_instagram_icon, "field 'mInstagramBtn' and method 'onSocialClick'");
    target.mInstagramBtn = Utils.castView(view, R.id.footer_instagram_icon, "field 'mInstagramBtn'", ImageView.class);
    view2131689711 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSocialClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.footer_youtube_icon, "field 'mYoutubeBtn' and method 'onSocialClick'");
    target.mYoutubeBtn = Utils.castView(view, R.id.footer_youtube_icon, "field 'mYoutubeBtn'", ImageView.class);
    view2131689712 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSocialClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.footer_snapchat_icon, "field 'mSnapchatBtn' and method 'onSocialClick'");
    target.mSnapchatBtn = Utils.castView(view, R.id.footer_snapchat_icon, "field 'mSnapchatBtn'", ImageView.class);
    view2131689713 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSocialClick(p0);
      }
    });
    target.mFooter = Utils.findRequiredViewAsType(source, R.id.register_picker_footer, "field 'mFooter'", ViewGroup.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RegisterPickerActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLayout = null;
    target.mToolbar = null;
    target.mPickerTitle = null;
    target.mSearchLayout = null;
    target.mSearchEditText = null;
    target.mList = null;
    target.mLoadingView = null;
    target.mFacebookBth = null;
    target.mTwitterBtn = null;
    target.mInstagramBtn = null;
    target.mYoutubeBtn = null;
    target.mSnapchatBtn = null;
    target.mFooter = null;

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
