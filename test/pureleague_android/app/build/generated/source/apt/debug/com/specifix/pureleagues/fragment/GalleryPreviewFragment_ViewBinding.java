// Generated code from Butter Knife. Do not modify!
package com.specifix.pureleagues.fragment;

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

public class GalleryPreviewFragment_ViewBinding implements Unbinder {
  private GalleryPreviewFragment target;

  @UiThread
  public GalleryPreviewFragment_ViewBinding(GalleryPreviewFragment target, View source) {
    this.target = target;

    target.mImageView = Utils.findRequiredViewAsType(source, R.id.gallery_preview_image, "field 'mImageView'", ImageView.class);
    target.mMessageContent = Utils.findRequiredViewAsType(source, R.id.gallery_preview_message, "field 'mMessageContent'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    GalleryPreviewFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mImageView = null;
    target.mMessageContent = null;
  }
}
