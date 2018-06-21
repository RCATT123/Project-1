// Generated code from Butter Knife. Do not modify!
package com.specifix.pureleagues.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.specifix.pureleagues.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class GalleryFragment_ViewBinding implements Unbinder {
  private GalleryFragment target;

  private View view2131689799;

  private View view2131689800;

  private View view2131689801;

  private View view2131689709;

  private View view2131689710;

  private View view2131689711;

  private View view2131689712;

  private View view2131689713;

  @UiThread
  public GalleryFragment_ViewBinding(final GalleryFragment target, View source) {
    this.target = target;

    View view;
    target.mGridView = Utils.findRequiredViewAsType(source, R.id.gallery_recycler_view, "field 'mGridView'", RecyclerView.class);
    target.mViewPager = Utils.findRequiredViewAsType(source, R.id.gallery_view_pager, "field 'mViewPager'", ViewPager.class);
    target.mViewGroup = Utils.findRequiredViewAsType(source, R.id.gallery_display_preview_layout, "field 'mViewGroup'", ViewGroup.class);
    target.mImagesLayout = Utils.findRequiredViewAsType(source, R.id.gallery_images_layout, "field 'mImagesLayout'", FrameLayout.class);
    target.mNoDataTextView = Utils.findRequiredViewAsType(source, R.id.gallery_no_data_text_view, "field 'mNoDataTextView'", TextView.class);
    view = Utils.findRequiredView(source, R.id.gallery_preview_arrow_left, "field 'mArrowLeft' and method 'onPrevImageClick'");
    target.mArrowLeft = view;
    view2131689799 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onPrevImageClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.gallery_preview_arrow_right, "field 'mArrowRight' and method 'onNextImageClick'");
    target.mArrowRight = view;
    view2131689800 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onNextImageClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.gallery_preview_close, "method 'onCloseGalleryClick'");
    view2131689801 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onCloseGalleryClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.footer_facebook_icon, "method 'onSocialClick'");
    view2131689709 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSocialClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.footer_twitter_icon, "method 'onSocialClick'");
    view2131689710 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSocialClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.footer_instagram_icon, "method 'onSocialClick'");
    view2131689711 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSocialClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.footer_youtube_icon, "method 'onSocialClick'");
    view2131689712 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSocialClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.footer_snapchat_icon, "method 'onSocialClick'");
    view2131689713 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSocialClick(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    GalleryFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mGridView = null;
    target.mViewPager = null;
    target.mViewGroup = null;
    target.mImagesLayout = null;
    target.mNoDataTextView = null;
    target.mArrowLeft = null;
    target.mArrowRight = null;

    view2131689799.setOnClickListener(null);
    view2131689799 = null;
    view2131689800.setOnClickListener(null);
    view2131689800 = null;
    view2131689801.setOnClickListener(null);
    view2131689801 = null;
    view2131689709.setOnClickListener(null);
    view2131689709 = null;
    view2131689710.setOnClickListener(null);
    view2131689710 = null;
    view2131689711.setOnClickListener(null);
    view2131689711 = null;
    view2131689712.setOnClickListener(null);
    view2131689712 = null;
    view2131689713.setOnClickListener(null);
    view2131689713 = null;
  }
}
