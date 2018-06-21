// Generated code from Butter Knife. Do not modify!
package com.specifix.pureleagues.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.specifix.pureleagues.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;

  private View view2131689917;

  private View view2131689916;

  private View view2131689617;

  private View view2131689618;

  private View view2131689619;

  private View view2131689620;

  private View view2131689621;

  private View view2131689622;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(final MainActivity target, View source) {
    this.target = target;

    View view;
    target.mToolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'mToolbar'", Toolbar.class);
    view = Utils.findRequiredView(source, R.id.toolbar_team_color, "field 'mTeamColor' and method 'onTeamColoursClick'");
    target.mTeamColor = Utils.castView(view, R.id.toolbar_team_color, "field 'mTeamColor'", ImageView.class);
    view2131689917 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onTeamColoursClick();
      }
    });
    target.mTabsLayout = Utils.findRequiredViewAsType(source, R.id.main_tabs_layout, "field 'mTabsLayout'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.logo_image_view, "field 'mTabDashboard' and method 'onDashboardClick'");
    target.mTabDashboard = Utils.castView(view, R.id.logo_image_view, "field 'mTabDashboard'", ImageView.class);
    view2131689916 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onDashboardClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.tab_fixtures, "field 'mTabFixtures' and method 'onFixturesTabClick'");
    target.mTabFixtures = Utils.castView(view, R.id.tab_fixtures, "field 'mTabFixtures'", TextView.class);
    view2131689617 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onFixturesTabClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.tab_results, "field 'mTabResults' and method 'onResultsTabClick'");
    target.mTabResults = Utils.castView(view, R.id.tab_results, "field 'mTabResults'", TextView.class);
    view2131689618 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onResultsTabClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.tab_tables, "field 'mTabTables' and method 'onTablesTabClick'");
    target.mTabTables = Utils.castView(view, R.id.tab_tables, "field 'mTabTables'", TextView.class);
    view2131689619 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onTablesTabClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.tab_stats, "field 'mTabStats' and method 'onStatsTabClick'");
    target.mTabStats = Utils.castView(view, R.id.tab_stats, "field 'mTabStats'", TextView.class);
    view2131689620 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onStatsTabClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.tab_gallery, "field 'mTabGallery' and method 'onGalleryTabClick'");
    target.mTabGallery = Utils.castView(view, R.id.tab_gallery, "field 'mTabGallery'", TextView.class);
    view2131689621 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onGalleryTabClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.tab_calendar, "field 'mTabCalendar' and method 'onCalendarTabClick'");
    target.mTabCalendar = Utils.castView(view, R.id.tab_calendar, "field 'mTabCalendar'", TextView.class);
    view2131689622 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onCalendarTabClick();
      }
    });
    target.mMainContainer = Utils.findRequiredViewAsType(source, R.id.main_container, "field 'mMainContainer'", FrameLayout.class);
    target.mNavigationView = Utils.findRequiredViewAsType(source, R.id.navigation_view, "field 'mNavigationView'", NavigationView.class);
    target.mDrawerLayout = Utils.findRequiredViewAsType(source, R.id.drawer, "field 'mDrawerLayout'", DrawerLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mToolbar = null;
    target.mTeamColor = null;
    target.mTabsLayout = null;
    target.mTabDashboard = null;
    target.mTabFixtures = null;
    target.mTabResults = null;
    target.mTabTables = null;
    target.mTabStats = null;
    target.mTabGallery = null;
    target.mTabCalendar = null;
    target.mMainContainer = null;
    target.mNavigationView = null;
    target.mDrawerLayout = null;

    view2131689917.setOnClickListener(null);
    view2131689917 = null;
    view2131689916.setOnClickListener(null);
    view2131689916 = null;
    view2131689617.setOnClickListener(null);
    view2131689617 = null;
    view2131689618.setOnClickListener(null);
    view2131689618 = null;
    view2131689619.setOnClickListener(null);
    view2131689619 = null;
    view2131689620.setOnClickListener(null);
    view2131689620 = null;
    view2131689621.setOnClickListener(null);
    view2131689621 = null;
    view2131689622.setOnClickListener(null);
    view2131689622 = null;
  }
}
