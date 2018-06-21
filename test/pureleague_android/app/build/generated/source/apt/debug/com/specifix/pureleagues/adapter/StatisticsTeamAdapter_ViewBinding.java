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

public class StatisticsTeamAdapter_ViewBinding implements Unbinder {
  private StatisticsTeamAdapter target;

  @UiThread
  public StatisticsTeamAdapter_ViewBinding(StatisticsTeamAdapter target, View source) {
    this.target = target;

    target.listItem = Utils.findRequiredViewAsType(source, R.id.simple_list_item_textview, "field 'listItem'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    StatisticsTeamAdapter target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.listItem = null;
  }
}
