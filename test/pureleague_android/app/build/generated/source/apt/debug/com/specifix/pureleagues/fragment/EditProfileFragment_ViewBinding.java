// Generated code from Butter Knife. Do not modify!
package com.specifix.pureleagues.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.specifix.pureleagues.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class EditProfileFragment_ViewBinding implements Unbinder {
  private EditProfileFragment target;

  private View view2131689698;

  private View view2131689749;

  private View view2131689776;

  private View view2131689748;

  private View view2131689709;

  private View view2131689710;

  private View view2131689711;

  private View view2131689712;

  private View view2131689713;

  @UiThread
  public EditProfileFragment_ViewBinding(final EditProfileFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.edit_profile_edit_text_view, "field 'mEditProfile' and method 'onEditProfileClick'");
    target.mEditProfile = Utils.castView(view, R.id.edit_profile_edit_text_view, "field 'mEditProfile'", TextView.class);
    view2131689698 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onEditProfileClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.edit_profile_save, "field 'mSaveProfile' and method 'onSaveProfileClick'");
    target.mSaveProfile = Utils.castView(view, R.id.edit_profile_save, "field 'mSaveProfile'", TextView.class);
    view2131689749 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSaveProfileClick();
      }
    });
    target.mName = Utils.findRequiredViewAsType(source, R.id.edit_profile_name, "field 'mName'", TextView.class);
    target.mNameEditText = Utils.findRequiredViewAsType(source, R.id.edit_profile_name_edit_text, "field 'mNameEditText'", EditText.class);
    target.mAge = Utils.findRequiredViewAsType(source, R.id.edit_profile_age, "field 'mAge'", TextView.class);
    target.mAgeEditText = Utils.findRequiredViewAsType(source, R.id.edit_profile_age_edit_text, "field 'mAgeEditText'", EditText.class);
    target.mHeight = Utils.findRequiredViewAsType(source, R.id.edit_profile_height, "field 'mHeight'", TextView.class);
    target.mHeightEditText = Utils.findRequiredViewAsType(source, R.id.edit_profile_height_edit_text, "field 'mHeightEditText'", EditText.class);
    target.mWeight = Utils.findRequiredViewAsType(source, R.id.edit_profile_weight, "field 'mWeight'", TextView.class);
    target.mWeightEditText = Utils.findRequiredViewAsType(source, R.id.edit_profile_weight_edit_text, "field 'mWeightEditText'", EditText.class);
    target.mPosition = Utils.findRequiredViewAsType(source, R.id.edit_profile_position, "field 'mPosition'", TextView.class);
    target.mPositionEditText = Utils.findRequiredViewAsType(source, R.id.edit_profile_position_edit_text, "field 'mPositionEditText'", EditText.class);
    target.mAboutMe = Utils.findRequiredViewAsType(source, R.id.edit_profile_about_me, "field 'mAboutMe'", TextView.class);
    target.mAboutMeEditText = Utils.findRequiredViewAsType(source, R.id.edit_profile_about_me_edit_text, "field 'mAboutMeEditText'", EditText.class);
    target.mTeamsListContainer = Utils.findRequiredViewAsType(source, R.id.edit_profile_teams_list_container, "field 'mTeamsListContainer'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.edit_profile_add_team_button, "field 'mAddTeamBtn' and method 'onAddTeamClick'");
    target.mAddTeamBtn = Utils.castView(view, R.id.edit_profile_add_team_button, "field 'mAddTeamBtn'", Button.class);
    view2131689776 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onAddTeamClick();
      }
    });
    target.mNameLabel = Utils.findRequiredViewAsType(source, R.id.edit_profile_name_label, "field 'mNameLabel'", TextView.class);
    target.mAgeLabel = Utils.findRequiredViewAsType(source, R.id.edit_profile_age_label, "field 'mAgeLabel'", TextView.class);
    target.mHeightLabel = Utils.findRequiredViewAsType(source, R.id.edit_profile_height_label, "field 'mHeightLabel'", TextView.class);
    target.mWeightLabel = Utils.findRequiredViewAsType(source, R.id.edit_profile_weight_label, "field 'mWeightLabel'", TextView.class);
    target.mPositionLabel = Utils.findRequiredViewAsType(source, R.id.edit_profile_position_label, "field 'mPositionLabel'", TextView.class);
    target.mProfileLabel = Utils.findRequiredViewAsType(source, R.id.edit_profile_profile_label, "field 'mProfileLabel'", TextView.class);
    target.mTeamsLabel = Utils.findRequiredViewAsType(source, R.id.edit_text_teams_label, "field 'mTeamsLabel'", TextView.class);
    target.mHeightLayout = Utils.findRequiredViewAsType(source, R.id.edit_profile_height_layout, "field 'mHeightLayout'", LinearLayout.class);
    target.mWeightLayout = Utils.findRequiredViewAsType(source, R.id.edit_profile_weight_layout, "field 'mWeightLayout'", LinearLayout.class);
    target.mPositionLayout = Utils.findRequiredViewAsType(source, R.id.edit_profile_position_layout, "field 'mPositionLayout'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.edit_profile_image, "field 'mProfilePhoto' and method 'onProfileImageClick'");
    target.mProfilePhoto = Utils.castView(view, R.id.edit_profile_image, "field 'mProfilePhoto'", CircleImageView.class);
    view2131689748 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onProfileImageClick();
      }
    });
    target.mUserLayout = Utils.findRequiredViewAsType(source, R.id.edit_profile_user_layout, "field 'mUserLayout'", RelativeLayout.class);
    target.mCmsText = Utils.findRequiredViewAsType(source, R.id.edit_profile_cms_text, "field 'mCmsText'", TextView.class);
    target.mPoundsText = Utils.findRequiredViewAsType(source, R.id.edit_profile_pounds_text, "field 'mPoundsText'", TextView.class);
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
    EditProfileFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mEditProfile = null;
    target.mSaveProfile = null;
    target.mName = null;
    target.mNameEditText = null;
    target.mAge = null;
    target.mAgeEditText = null;
    target.mHeight = null;
    target.mHeightEditText = null;
    target.mWeight = null;
    target.mWeightEditText = null;
    target.mPosition = null;
    target.mPositionEditText = null;
    target.mAboutMe = null;
    target.mAboutMeEditText = null;
    target.mTeamsListContainer = null;
    target.mAddTeamBtn = null;
    target.mNameLabel = null;
    target.mAgeLabel = null;
    target.mHeightLabel = null;
    target.mWeightLabel = null;
    target.mPositionLabel = null;
    target.mProfileLabel = null;
    target.mTeamsLabel = null;
    target.mHeightLayout = null;
    target.mWeightLayout = null;
    target.mPositionLayout = null;
    target.mProfilePhoto = null;
    target.mUserLayout = null;
    target.mCmsText = null;
    target.mPoundsText = null;

    view2131689698.setOnClickListener(null);
    view2131689698 = null;
    view2131689749.setOnClickListener(null);
    view2131689749 = null;
    view2131689776.setOnClickListener(null);
    view2131689776 = null;
    view2131689748.setOnClickListener(null);
    view2131689748 = null;
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
