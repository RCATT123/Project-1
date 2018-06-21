// Generated code from Butter Knife. Do not modify!
package com.specifix.pureleagues.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.specifix.pureleagues.R;
import java.lang.IllegalStateException;
import java.lang.Override;
import me.grantland.widget.AutofitTextView;

public class DashboardFixtureFragment_ViewBinding implements Unbinder {
  private DashboardFixtureFragment target;

  @UiThread
  public DashboardFixtureFragment_ViewBinding(DashboardFixtureFragment target, View source) {
    this.target = target;

    target.mFixtureClubOne = Utils.findRequiredViewAsType(source, R.id.dashboard_fixture_club_1, "field 'mFixtureClubOne'", AutofitTextView.class);
    target.mFixtureClubTwo = Utils.findRequiredViewAsType(source, R.id.dashboard_fixture_club_2, "field 'mFixtureClubTwo'", AutofitTextView.class);
    target.mVsLabel = Utils.findRequiredViewAsType(source, R.id.dashboard_fixture_versus_label, "field 'mVsLabel'", TextView.class);
    target.mFixtureAddress = Utils.findRequiredViewAsType(source, R.id.dashboard_fixture_address, "field 'mFixtureAddress'", AutofitTextView.class);
    target.mFixtureDate = Utils.findRequiredViewAsType(source, R.id.dashboard_fixture_date, "field 'mFixtureDate'", TextView.class);
    target.mFixtureTime = Utils.findRequiredViewAsType(source, R.id.dashboard_fixture_time, "field 'mFixtureTime'", TextView.class);
    target.score = Utils.findRequiredViewAsType(source, R.id.score, "field 'score'", TextView.class);
    target.mAddressPoint = Utils.findRequiredViewAsType(source, R.id.dashboard_fixture_address_point, "field 'mAddressPoint'", ImageView.class);
    target.mDateTime = Utils.findRequiredViewAsType(source, R.id.dashboard_fixture_datetime_layout, "field 'mDateTime'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DashboardFixtureFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mFixtureClubOne = null;
    target.mFixtureClubTwo = null;
    target.mVsLabel = null;
    target.mFixtureAddress = null;
    target.mFixtureDate = null;
    target.mFixtureTime = null;
    target.score = null;
    target.mAddressPoint = null;
    target.mDateTime = null;
  }
}
