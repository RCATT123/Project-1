// Generated code from Butter Knife. Do not modify!
package com.specifix.pureleagues.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.specifix.pureleagues.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class StatisticsFragment_ViewBinding implements Unbinder {
  private StatisticsFragment target;

  private View view2131689822;

  private View view2131689709;

  private View view2131689710;

  private View view2131689711;

  private View view2131689712;

  private View view2131689713;

  @UiThread
  public StatisticsFragment_ViewBinding(final StatisticsFragment target, View source) {
    this.target = target;

    View view;
    target.mListView = Utils.findRequiredViewAsType(source, R.id.statistics_recycler, "field 'mListView'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.statistics_spinner, "field 'mSelectClub' and method 'onTeamSelected'");
    target.mSelectClub = Utils.castView(view, R.id.statistics_spinner, "field 'mSelectClub'", Spinner.class);
    view2131689822 = view;
    ((AdapterView<?>) view).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> p0, View p1, int p2, long p3) {
        target.onTeamSelected(p0, p1, p2, p3);
      }

      @Override
      public void onNothingSelected(AdapterView<?> p0) {
      }
    });
    target.mProgressBar = Utils.findRequiredViewAsType(source, R.id.statistics_progress_bar, "field 'mProgressBar'", ProgressBar.class);
    target.mNoDataTextView = Utils.findRequiredViewAsType(source, R.id.stats_no_data_text_view, "field 'mNoDataTextView'", TextView.class);
    target.mFooter = Utils.findRequiredViewAsType(source, R.id.stats_footer, "field 'mFooter'", LinearLayout.class);
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
    StatisticsFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mListView = null;
    target.mSelectClub = null;
    target.mProgressBar = null;
    target.mNoDataTextView = null;
    target.mFooter = null;

    ((AdapterView<?>) view2131689822).setOnItemSelectedListener(null);
    view2131689822 = null;
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
