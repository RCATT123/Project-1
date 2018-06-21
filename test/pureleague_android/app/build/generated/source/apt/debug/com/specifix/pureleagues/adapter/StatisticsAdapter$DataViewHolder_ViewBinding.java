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

public class StatisticsAdapter$DataViewHolder_ViewBinding implements Unbinder {
  private StatisticsAdapter.DataViewHolder target;

  @UiThread
  public StatisticsAdapter$DataViewHolder_ViewBinding(StatisticsAdapter.DataViewHolder target,
      View source) {
    this.target = target;

    target.mNumber = Utils.findRequiredViewAsType(source, R.id.list_item_statistics_number, "field 'mNumber'", TextView.class);
    target.mPlayer = Utils.findRequiredViewAsType(source, R.id.list_item_statistics_player, "field 'mPlayer'", TextView.class);
    target.mGoals = Utils.findRequiredViewAsType(source, R.id.list_item_statistics_goals, "field 'mGoals'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    StatisticsAdapter.DataViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mNumber = null;
    target.mPlayer = null;
    target.mGoals = null;
  }
}
