// Generated code from Butter Knife. Do not modify!
package com.specifix.pureleagues.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.specifix.pureleagues.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MessagesFragment_ViewBinding implements Unbinder {
  private MessagesFragment target;

  private View view2131689810;

  private View view2131689812;

  @UiThread
  public MessagesFragment_ViewBinding(final MessagesFragment target, View source) {
    this.target = target;

    View view;
    target.mFragmentLabel = Utils.findRequiredViewAsType(source, R.id.messages_fragment_label, "field 'mFragmentLabel'", TextView.class);
    target.mRecyclerView = Utils.findRequiredViewAsType(source, R.id.messages_recycler_view, "field 'mRecyclerView'", RecyclerView.class);
    target.mNewMessageEditText = Utils.findRequiredViewAsType(source, R.id.messages_content_edit_text, "field 'mNewMessageEditText'", EditText.class);
    view = Utils.findRequiredView(source, R.id.messages_new_image, "field 'mMessageImage' and method 'selectImage'");
    target.mMessageImage = Utils.castView(view, R.id.messages_new_image, "field 'mMessageImage'", ImageView.class);
    view2131689810 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.selectImage();
      }
    });
    view = Utils.findRequiredView(source, R.id.messages_send_button, "field 'mSendBtn' and method 'onSendButtonClick'");
    target.mSendBtn = view;
    view2131689812 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSendButtonClick();
      }
    });
    target.mProgressBar = Utils.findRequiredViewAsType(source, R.id.messages_picker_progress_bar, "field 'mProgressBar'", ProgressBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MessagesFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mFragmentLabel = null;
    target.mRecyclerView = null;
    target.mNewMessageEditText = null;
    target.mMessageImage = null;
    target.mSendBtn = null;
    target.mProgressBar = null;

    view2131689810.setOnClickListener(null);
    view2131689810 = null;
    view2131689812.setOnClickListener(null);
    view2131689812 = null;
  }
}
