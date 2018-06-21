// Generated code from Butter Knife. Do not modify!
package com.specifix.pureleagues.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.specifix.pureleagues.R;
import java.lang.IllegalStateException;
import java.lang.Override;
import me.grantland.widget.AutofitTextView;

public class FixturesAdapter$DataViewHolder_ViewBinding implements Unbinder {
  private FixturesAdapter.DataViewHolder target;

  @UiThread
  public FixturesAdapter$DataViewHolder_ViewBinding(FixturesAdapter.DataViewHolder target,
      View source) {
    this.target = target;

    target.mFirstTeam = Utils.findRequiredViewAsType(source, R.id.fixture_list_item_team_one_title, "field 'mFirstTeam'", TextView.class);
    target.mSecondTeam = Utils.findRequiredViewAsType(source, R.id.fixture_list_item_team_two_title, "field 'mSecondTeam'", TextView.class);
    target.mTime = Utils.findRequiredViewAsType(source, R.id.fixture_list_item_time, "field 'mTime'", TextView.class);
    target.mDate = Utils.findRequiredViewAsType(source, R.id.fixture_list_item_date, "field 'mDate'", AutofitTextView.class);
    target.mMessages = Utils.findRequiredViewAsType(source, R.id.fixture_list_item_messages, "field 'mMessages'", ImageView.class);
    target.mRoute = Utils.findRequiredViewAsType(source, R.id.fixture_list_item_route, "field 'mRoute'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FixturesAdapter.DataViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mFirstTeam = null;
    target.mSecondTeam = null;
    target.mTime = null;
    target.mDate = null;
    target.mMessages = null;
    target.mRoute = null;
  }
}
