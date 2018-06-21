// Generated code from Butter Knife. Do not modify!
package com.specifix.pureleagues.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.specifix.pureleagues.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CalendarFragment_ViewBinding implements Unbinder {
  private CalendarFragment target;

  @UiThread
  public CalendarFragment_ViewBinding(CalendarFragment target, View source) {
    this.target = target;

    target.mScrollView = Utils.findRequiredViewAsType(source, R.id.calendar_scroll_view, "field 'mScrollView'", ScrollView.class);
    target.mCalendarLegend = Utils.findRequiredViewAsType(source, R.id.calendar_container_legend, "field 'mCalendarLegend'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CalendarFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mScrollView = null;
    target.mCalendarLegend = null;
  }
}
