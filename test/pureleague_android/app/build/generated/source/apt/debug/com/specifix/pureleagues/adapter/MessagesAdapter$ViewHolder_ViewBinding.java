// Generated code from Butter Knife. Do not modify!
package com.specifix.pureleagues.adapter;

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

public class MessagesAdapter$ViewHolder_ViewBinding implements Unbinder {
  private MessagesAdapter.ViewHolder target;

  @UiThread
  public MessagesAdapter$ViewHolder_ViewBinding(MessagesAdapter.ViewHolder target, View source) {
    this.target = target;

    target.mName = Utils.findRequiredViewAsType(source, R.id.list_item_message_name, "field 'mName'", TextView.class);
    target.mDay = Utils.findRequiredViewAsType(source, R.id.list_item_message_day, "field 'mDay'", TextView.class);
    target.mMessage = Utils.findRequiredViewAsType(source, R.id.list_item_message_message, "field 'mMessage'", TextView.class);
    target.mTime = Utils.findRequiredViewAsType(source, R.id.list_item_message_time, "field 'mTime'", TextView.class);
    target.mAlert = Utils.findRequiredViewAsType(source, R.id.list_item_message_alert, "field 'mAlert'", ImageView.class);
    target.mImage = Utils.findRequiredViewAsType(source, R.id.list_item_message_image, "field 'mImage'", ImageView.class);
    target.mLayout = Utils.findRequiredViewAsType(source, R.id.list_item_message_layout, "field 'mLayout'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MessagesAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mName = null;
    target.mDay = null;
    target.mMessage = null;
    target.mTime = null;
    target.mAlert = null;
    target.mImage = null;
    target.mLayout = null;
  }
}
