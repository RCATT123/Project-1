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

public class GalleryAdapter$DataViewHolder_ViewBinding implements Unbinder {
  private GalleryAdapter.DataViewHolder target;

  @UiThread
  public GalleryAdapter$DataViewHolder_ViewBinding(GalleryAdapter.DataViewHolder target,
      View source) {
    this.target = target;

    target.mMessageImage = Utils.findRequiredViewAsType(source, R.id.list_item_gallery_image, "field 'mMessageImage'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    GalleryAdapter.DataViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mMessageImage = null;
  }
}
