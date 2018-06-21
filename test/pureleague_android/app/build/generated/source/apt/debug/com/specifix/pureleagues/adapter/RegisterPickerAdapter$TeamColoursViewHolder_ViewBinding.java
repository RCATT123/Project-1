// Generated code from Butter Knife. Do not modify!
package com.specifix.pureleagues.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.specifix.pureleagues.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RegisterPickerAdapter$TeamColoursViewHolder_ViewBinding implements Unbinder {
  private RegisterPickerAdapter.TeamColoursViewHolder target;

  @UiThread
  public RegisterPickerAdapter$TeamColoursViewHolder_ViewBinding(RegisterPickerAdapter.TeamColoursViewHolder target,
      View source) {
    this.target = target;

    target.mImage = Utils.findRequiredViewAsType(source, R.id.team_color_image, "field 'mImage'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RegisterPickerAdapter.TeamColoursViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mImage = null;
  }
}
