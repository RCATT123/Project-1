// Generated code from Butter Knife. Do not modify!
package com.specifix.pureleagues.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.view.ViewPager;
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
import me.grantland.widget.AutofitTextView;

public class DashboardFragment_ViewBinding implements Unbinder {
  private DashboardFragment target;

  private View view2131689731;

  private View view2131689733;

  private View view2131689736;

  private View view2131689745;

  private View view2131689709;

  private View view2131689710;

  private View view2131689711;

  private View view2131689712;

  private View view2131689713;

  @UiThread
  public DashboardFragment_ViewBinding(final DashboardFragment target, View source) {
    this.target = target;

    View view;
    target.mViewPager = Utils.findRequiredViewAsType(source, R.id.dashboard_view_pager, "field 'mViewPager'", ViewPager.class);
    view = Utils.findRequiredView(source, R.id.dashboard_route_button, "field 'mRouteBtn' and method 'onRouteClick'");
    target.mRouteBtn = Utils.castView(view, R.id.dashboard_route_button, "field 'mRouteBtn'", LinearLayout.class);
    view2131689731 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onRouteClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.dashboard_weather_button, "field 'mWeatherBtn' and method 'onWeatherClick'");
    target.mWeatherBtn = Utils.castView(view, R.id.dashboard_weather_button, "field 'mWeatherBtn'", LinearLayout.class);
    view2131689733 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onWeatherClick();
      }
    });
    target.mLocationTemperature = Utils.findRequiredViewAsType(source, R.id.dashboard_weather_temperature, "field 'mLocationTemperature'", AutofitTextView.class);
    view = Utils.findRequiredView(source, R.id.dashboard_messages_button, "field 'mMessagesBtn' and method 'onAllMessagesClick'");
    target.mMessagesBtn = Utils.castView(view, R.id.dashboard_messages_button, "field 'mMessagesBtn'", LinearLayout.class);
    view2131689736 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onAllMessagesClick();
      }
    });
    target.mLocationMessages = Utils.findRequiredViewAsType(source, R.id.dashboard_messages, "field 'mLocationMessages'", AutofitTextView.class);
    target.mUnreadMessages = Utils.findRequiredViewAsType(source, R.id.dashboard_messages_badge, "field 'mUnreadMessages'", TextView.class);
    target.mRouteLabel = Utils.findRequiredViewAsType(source, R.id.dashboard_fixture_route_label, "field 'mRouteLabel'", TextView.class);
    target.mWeatherLabel = Utils.findRequiredViewAsType(source, R.id.dashboard_fixture_weather_label, "field 'mWeatherLabel'", TextView.class);
    target.mMessagesLabel = Utils.findRequiredViewAsType(source, R.id.dashboard_messages_label, "field 'mMessagesLabel'", TextView.class);
    target.mLatestMessagesLabel = Utils.findRequiredViewAsType(source, R.id.dashboard_latest_messages_label, "field 'mLatestMessagesLabel'", TextView.class);
    target.mUnreadMessagesLabel = Utils.findRequiredViewAsType(source, R.id.dashboard_unread_messages, "field 'mUnreadMessagesLabel'", TextView.class);
    view = Utils.findRequiredView(source, R.id.dashboard_all_messages_button, "field 'mAllMessagesButton' and method 'onShareClick'");
    target.mAllMessagesButton = Utils.castView(view, R.id.dashboard_all_messages_button, "field 'mAllMessagesButton'", Button.class);
    view2131689745 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onShareClick();
      }
    });
    target.mLatestMessagesContainer = Utils.findRequiredViewAsType(source, R.id.dashboard_latest_messages_container, "field 'mLatestMessagesContainer'", LinearLayout.class);
    target.mNoDataTextView = Utils.findRequiredViewAsType(source, R.id.dashboard_no_data_text_view, "field 'mNoDataTextView'", TextView.class);
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
    DashboardFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mViewPager = null;
    target.mRouteBtn = null;
    target.mWeatherBtn = null;
    target.mLocationTemperature = null;
    target.mMessagesBtn = null;
    target.mLocationMessages = null;
    target.mUnreadMessages = null;
    target.mRouteLabel = null;
    target.mWeatherLabel = null;
    target.mMessagesLabel = null;
    target.mLatestMessagesLabel = null;
    target.mUnreadMessagesLabel = null;
    target.mAllMessagesButton = null;
    target.mLatestMessagesContainer = null;
    target.mNoDataTextView = null;

    view2131689731.setOnClickListener(null);
    view2131689731 = null;
    view2131689733.setOnClickListener(null);
    view2131689733 = null;
    view2131689736.setOnClickListener(null);
    view2131689736 = null;
    view2131689745.setOnClickListener(null);
    view2131689745 = null;
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
