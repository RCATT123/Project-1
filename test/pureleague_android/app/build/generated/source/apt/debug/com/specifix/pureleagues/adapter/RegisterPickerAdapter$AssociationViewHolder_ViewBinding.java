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

public class RegisterPickerAdapter$AssociationViewHolder_ViewBinding implements Unbinder {
  private RegisterPickerAdapter.AssociationViewHolder target;

  @UiThread
  public RegisterPickerAdapter$AssociationViewHolder_ViewBinding(RegisterPickerAdapter.AssociationViewHolder target,
      View source) {
    this.target = target;

    target.mLogo = Utils.findRequiredViewAsType(source, R.id.association_image, "field 'mLogo'", ImageView.class);
    target.mName = Utils.findRequiredViewAsType(source, R.id.association_name, "field 'mName'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RegisterPickerAdapter.AssociationViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLogo = null;
    target.mName = null;
  }
}
