// Generated code from Butter Knife. Do not modify!
package com.specifix.pureleagues.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.specifix.pureleagues.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MessageImageDialogFragment_ViewBinding implements Unbinder {
  private MessageImageDialogFragment target;

  private View view2131689804;

  @UiThread
  public MessageImageDialogFragment_ViewBinding(final MessageImageDialogFragment target,
      View source) {
    this.target = target;

    View view;
    target.mImage = Utils.findRequiredViewAsType(source, R.id.messages_dialog_image, "field 'mImage'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.messages_dialog_close, "method 'onCloseDialogClick'");
    view2131689804 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onCloseDialogClick();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    MessageImageDialogFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mImage = null;

    view2131689804.setOnClickListener(null);
    view2131689804 = null;
  }
}
