// Generated code from Butter Knife. Do not modify!
package com.specifix.pureleagues.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.specifix.pureleagues.R;
import java.lang.IllegalStateException;
import java.lang.Override;
import me.grantland.widget.AutofitTextView;

public class ResultsFragment_ViewBinding implements Unbinder {
  private ResultsFragment target;

  private View view2131689816;

  private View view2131689817;

  private View view2131689709;

  private View view2131689710;

  private View view2131689711;

  private View view2131689712;

  private View view2131689713;

  @UiThread
  public ResultsFragment_ViewBinding(final ResultsFragment target, View source) {
    this.target = target;

    View view;
    target.mTitle = Utils.findRequiredViewAsType(source, R.id.results_title_text_view, "field 'mTitle'", TextView.class);
    target.mViewPager = Utils.findRequiredViewAsType(source, R.id.results_view_pager, "field 'mViewPager'", ViewPager.class);
    target.mNoDataTextView = Utils.findRequiredViewAsType(source, R.id.results_no_data_text_view, "field 'mNoDataTextView'", AutofitTextView.class);
    target.mFooter = Utils.findRequiredViewAsType(source, R.id.results_footer, "field 'mFooter'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.results_arrow_left, "method 'onPrevImageClick'");
    view2131689816 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onPrevImageClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.results_arrow_right, "method 'onNextImageClick'");
    view2131689817 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onNextImageClick();
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
    ResultsFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mTitle = null;
    target.mViewPager = null;
    target.mNoDataTextView = null;
    target.mFooter = null;

    view2131689816.setOnClickListener(null);
    view2131689816 = null;
    view2131689817.setOnClickListener(null);
    view2131689817 = null;
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
