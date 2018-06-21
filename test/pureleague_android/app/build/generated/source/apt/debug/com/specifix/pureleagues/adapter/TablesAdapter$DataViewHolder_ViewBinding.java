// Generated code from Butter Knife. Do not modify!
package com.specifix.pureleagues.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.specifix.pureleagues.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TablesAdapter$DataViewHolder_ViewBinding implements Unbinder {
  private TablesAdapter.DataViewHolder target;

  @UiThread
  public TablesAdapter$DataViewHolder_ViewBinding(TablesAdapter.DataViewHolder target,
      View source) {
    this.target = target;

    target.mNumber = Utils.findRequiredViewAsType(source, R.id.list_item_tables_number, "field 'mNumber'", TextView.class);
    target.mClub = Utils.findRequiredViewAsType(source, R.id.list_item_tables_club_name, "field 'mClub'", TextView.class);
    target.mGames = Utils.findRequiredViewAsType(source, R.id.list_item_tables_total_games, "field 'mGames'", TextView.class);
    target.mWon = Utils.findRequiredViewAsType(source, R.id.list_item_tables_won, "field 'mWon'", TextView.class);
    target.mDrawn = Utils.findRequiredViewAsType(source, R.id.list_item_tables_drawn, "field 'mDrawn'", TextView.class);
    target.mLost = Utils.findRequiredViewAsType(source, R.id.list_item_tables_lost, "field 'mLost'", TextView.class);
    target.mGoalsFor = Utils.findRequiredViewAsType(source, R.id.list_item_tables_goals_for, "field 'mGoalsFor'", TextView.class);
    target.mGoalsAgainst = Utils.findRequiredViewAsType(source, R.id.list_item_tables_goals_against, "field 'mGoalsAgainst'", TextView.class);
    target.mDifference = Utils.findRequiredViewAsType(source, R.id.list_item_tables_goals_difference, "field 'mDifference'", TextView.class);
    target.mPoints = Utils.findRequiredViewAsType(source, R.id.list_item_tables_points, "field 'mPoints'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    TablesAdapter.DataViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mNumber = null;
    target.mClub = null;
    target.mGames = null;
    target.mWon = null;
    target.mDrawn = null;
    target.mLost = null;
    target.mGoalsFor = null;
    target.mGoalsAgainst = null;
    target.mDifference = null;
    target.mPoints = null;
  }
}
