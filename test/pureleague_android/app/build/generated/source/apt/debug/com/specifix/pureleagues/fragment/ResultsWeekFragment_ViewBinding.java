// Generated code from Butter Knife. Do not modify!
package com.specifix.pureleagues.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.specifix.pureleagues.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ResultsWeekFragment_ViewBinding implements Unbinder {
  private ResultsWeekFragment target;

  private View view2131689709;

  private View view2131689710;

  private View view2131689711;

  private View view2131689712;

  private View view2131689713;

  @UiThread
  public ResultsWeekFragment_ViewBinding(final ResultsWeekFragment target, View source) {
    this.target = target;

    View view;
    target.mListView = Utils.findRequiredViewAsType(source, R.id.results_recycler_view, "field 'mListView'", RecyclerView.class);
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
    ResultsWeekFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mListView = null;

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
