package com.specifix.pureleagues.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
//import com.parse.ParseUser;
import com.specifix.pureleagues.R;
import com.specifix.pureleagues.api.DataManager;
import com.specifix.pureleagues.api.UserManager;
import com.specifix.pureleagues.model.Converter;
import com.specifix.pureleagues.model.Team;
import com.specifix.pureleagues.model.User;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import me.grantland.widget.AutofitTextView;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.EasyImageConfig;
import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;

public class EditProfileFragment extends Fragment implements UserManager.UserListener {
    public static final String TEAM_ID = "team_id";
    private static final int MAX_TEAMS_SIZE = 3;
    private static final int PICK_IMAGE_REQUEST = 212;

    @BindView(R.id.edit_profile_edit_text_view)
    TextView mEditProfile;
    @BindView(R.id.edit_profile_save)
    TextView mSaveProfile;
    @BindView(R.id.edit_profile_name)
    TextView mName;
    @BindView(R.id.edit_profile_name_edit_text)
    EditText mNameEditText;
    @BindView(R.id.edit_profile_age)
    TextView mAge;
    @BindView(R.id.edit_profile_age_edit_text)
    EditText mAgeEditText;
    @BindView(R.id.edit_profile_height)
    TextView mHeight;
    @BindView(R.id.edit_profile_height_edit_text)
    EditText mHeightEditText;
    @BindView(R.id.edit_profile_weight)
    TextView mWeight;
    @BindView(R.id.edit_profile_weight_edit_text)
    EditText mWeightEditText;
    @BindView(R.id.edit_profile_position)
    TextView mPosition;
    @BindView(R.id.edit_profile_position_edit_text)
    EditText mPositionEditText;
    @BindView(R.id.edit_profile_about_me)
    TextView mAboutMe;
    @BindView(R.id.edit_profile_about_me_edit_text)
    EditText mAboutMeEditText;
    @BindView(R.id.edit_profile_teams_list_container)
    LinearLayout mTeamsListContainer;
    @BindView(R.id.edit_profile_add_team_button)
    Button mAddTeamBtn;
    @BindView(R.id.edit_profile_name_label)
    TextView mNameLabel;
    @BindView(R.id.edit_profile_age_label)
    TextView mAgeLabel;
    @BindView(R.id.edit_profile_height_label)
    TextView mHeightLabel;
    @BindView(R.id.edit_profile_weight_label)
    TextView mWeightLabel;
    @BindView(R.id.edit_profile_position_label)
    TextView mPositionLabel;
    @BindView(R.id.edit_profile_profile_label)
    TextView mProfileLabel;
    @BindView(R.id.edit_text_teams_label)
    TextView mTeamsLabel;
    @BindView(R.id.edit_profile_height_layout)
    LinearLayout mHeightLayout;
    @BindView(R.id.edit_profile_weight_layout)
    LinearLayout mWeightLayout;
    @BindView(R.id.edit_profile_position_layout)
    LinearLayout mPositionLayout;
    @BindView(R.id.edit_profile_image)
    CircleImageView mProfilePhoto;
    @BindView(R.id.edit_profile_user_layout)
    RelativeLayout mUserLayout;
    @BindView(R.id.edit_profile_cms_text)
    TextView mCmsText;
    @BindView(R.id.edit_profile_pounds_text)
    TextView mPoundsText;

    private User mUserProfile;
    private EditProfileListener mCallback;
    private Typeface gothamBold;
    private Typeface gothamBook;
    private ProgressDialog mLoadingDialog;

    private boolean mIsEditable = false;
    private String mCurrentImageUri;

    public static EditProfileFragment newInstance() {
        EditProfileFragment fragment = new EditProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context Context) {
        super.onAttach(Context);

        try {
            mCallback = (EditProfileListener) Context;
        } catch (ClassCastException e) {
            throw new ClassCastException(Context.toString()
                    + " must implement EditProfileListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Nammu.init(getContext());
        gothamBold = Typeface.createFromAsset(getContext().getAssets(), "GothamBold.otf");
        gothamBook = Typeface.createFromAsset(getContext().getAssets(), "GothamBook.otf");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*mUserProfile = UserManager.getInstance().getCurrentUser();
        if (mUserProfile.getHeight() == null) {
            mUserProfile.setHeight(String.valueOf(0));
        }
        if (mUserProfile.getWeight() == null) {
            mUserProfile.setWeight(String.valueOf(0));
        }
        mName.setText(mUserProfile.getName());
        mAge.setText(getAge(mUserProfile.getDateOfBirth()));
        mAgeEditText.setKeyListener(null);
        mAgeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        mHeight.setText(String.format("%s", mUserProfile.getHeight()));
        mWeight.setText(String.format("%s", mUserProfile.getWeight()));
        mPosition.setText(mUserProfile.getPosition());
        mAboutMe.setText(mUserProfile.getProfile());

        if (!mUserProfile.getType().equals(getString(R.string.player_item))) {
            mUserLayout.setBackgroundResource(R.drawable.footer_background);
        }

        String imageUrl = mUserProfile.getPhotoUrl();
        if (TextUtils.isEmpty(imageUrl)) {
            mProfilePhoto.setImageResource(R.drawable.user_default_logo);
        } else {
            byte[] data = mUserProfile.getPhotoByteArray();
            if ((data != null) && (data.length > 0)) {
                Bitmap photoImage = decodeSampledBitmapFromByteArray(data, Converter.dpToPx(getActivity(), 200));
                mProfilePhoto.setImageBitmap(photoImage);

            } else {
                /*Picasso.with(getContext())
                        .load(imageUrl)
                        //.resize(Converter.dpToPx(mContext, 180), Converter.dpToPx(mContext, 180))
                        .centerInside()
                        .fit()
                        .into(mProfilePhoto);*//*
                Glide.with(getContext())
                        .load(imageUrl)
                        .centerCrop()
                        .into(mProfilePhoto);
            }
        }

        showUserTeams();

        Typeface gothamBold = Typeface.createFromAsset(getContext().getAssets(), "GothamBold.otf");
        Typeface gothamBook = Typeface.createFromAsset(getContext().getAssets(), "GothamBook.otf");
        mEditProfile.setTypeface(gothamBold);
        mSaveProfile.setTypeface(gothamBold);
        mNameLabel.setTypeface(gothamBold);
        mName.setTypeface(gothamBold);
        mNameEditText.setTypeface(gothamBold);
        mAgeLabel.setTypeface(gothamBold);
        mAge.setTypeface(gothamBold);
        mAgeEditText.setTypeface(gothamBold);
        mHeightLabel.setTypeface(gothamBold);
        mHeight.setTypeface(gothamBold);
        mHeightEditText.setTypeface(gothamBold);
        mWeightLabel.setTypeface(gothamBold);
        mWeight.setTypeface(gothamBold);
        mWeightEditText.setTypeface(gothamBold);
        mPositionLabel.setTypeface(gothamBold);
        mPosition.setTypeface(gothamBold);
        mPositionEditText.setTypeface(gothamBold);

        mProfileLabel.setTypeface(gothamBold);
        mAboutMe.setTypeface(gothamBook);
        mAboutMeEditText.setTypeface(gothamBook);

        mCmsText.setTypeface(gothamBold);
        mPoundsText.setTypeface(gothamBold);

        mTeamsLabel.setTypeface(gothamBold);
        mAddTeamBtn.setTypeface(gothamBold);

        if (!mUserProfile.getType().equals(getString(R.string.player_item))) {
            mHeightLayout.setVisibility(View.GONE);
            mWeightLayout.setVisibility(View.GONE);
            mPositionLayout.setVisibility(View.GONE);
            mProfileLabel.setVisibility(View.GONE);
            mAboutMe.setVisibility(View.GONE);
            mAboutMeEditText.setVisibility(View.GONE);
        }*/
    }

    public static Bitmap decodeSampledBitmapFromByteArray(byte[] data, int reqSize) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, 0, data.length, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqSize, reqSize);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(data, 0, data.length, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    private void showUserTeams() {
        List<Team> teams = mUserProfile.getTeams();
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());

        for (final Team team : teams) {
            final View view = View.inflate(getContext(), R.layout.list_item_team, null);
            ImageView colours = (ImageView) view.findViewById(R.id.team_list_item_colour);
            AutofitTextView clubName = (AutofitTextView) view.findViewById(R.id.team_list_item_club);
            TextView divisionName = (TextView) view.findViewById(R.id.team_list_item_division);
            ImageView clubSettings = (ImageView) view.findViewById(R.id.team_list_item_settings);
            view.setTag(team.getTeamId());

            if (team.getTeamId() == sp.getLong(TEAM_ID, 0)) {
                view.setBackgroundResource(R.color.sub_color_3);
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (sp.getLong(TEAM_ID, 0) == team.getTeamId()) return;

                    mLoadingDialog = new ProgressDialog(getContext());
                    mLoadingDialog.setMessage(getString(R.string.loading_data_message));
                    mLoadingDialog.show();

                    sp.edit().putLong(TEAM_ID, team.getTeamId()).apply();
                    //UserManager.getInstance().setCurrentTeamId(team.getTeamId());
                    DataManager.getInstance().downloadEvents(team, getContext(), new DataManager.DataManagerUpdateEvent() {
                        @Override
                        public void onFixturesReady() {
                            if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
                                mLoadingDialog.dismiss();
                                setTeamSelected(view);
                                ((EditTeamFragment.EditTeamListener) mCallback).onTeamColoursChanged(team.getColor(), true);
                            }
                        }

                        @Override
                        public void onResultsReady() {

                        }
                    });
                }
            });

            clubName.setTypeface(gothamBold);
            divisionName.setTypeface(gothamBook);

            clubName.setText(team.getClub());
            divisionName.setText(team.getDivision());
            /*colours.setImageResource(UserManager.getInstance().getTeamColorRes(team.getColor()));
            clubSettings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.onEditTeamClick(team);
                }
            });*/

            mTeamsListContainer.addView(view);
        }
    }

    private void setTeamSelected(View view) {
        for (int i = 0; i < mTeamsListContainer.getChildCount(); i++) {
            View child = mTeamsListContainer.getChildAt(i);
            if (child.getTag() == view.getTag()) {
                child.setBackgroundResource(R.color.sub_color_3);
            } else {
                child.setBackgroundResource(R.color.sub_color_4);
            }
        }
    }

    private String getAge(String dateOfBirth) {
        DateFormat sourceFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        Date todayDate = new Date();
        long interval = 0;
        try {
            interval = todayDate.getTime() - sourceFormat.parse(dateOfBirth).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long age = interval / DateUtils.YEAR_IN_MILLIS;

        return String.valueOf(age);
    }

    public void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        DateFormat sourceFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        long birthMillis = 0;
        try {
            birthMillis = sourceFormat.parse(mUserProfile.getDateOfBirth()).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTimeInMillis(birthMillis);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(
                getContext(),
                R.style.AppTheme_DatePicker,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String dateOfBirth = String.format(
                                Locale.getDefault(),
                                "%02d.%02d.%s",
                                dayOfMonth,
                                month + 1,
                                year);
                        mAgeEditText.setText(getAge(dateOfBirth));
                        mUserProfile.setDateOfBirth(dateOfBirth);
                    }
                }, year, month, day);
        datePicker.getDatePicker().setMaxDate(new Date().getTime());
        datePicker.show();
    }

    @OnClick(R.id.edit_profile_edit_text_view)
    public void onEditProfileClick() {
        mIsEditable = true;
        mEditProfile.setVisibility(View.INVISIBLE);
        mSaveProfile.setVisibility(View.VISIBLE);

        mName.setVisibility(View.GONE);
        mNameEditText.setVisibility(View.VISIBLE);
        mAge.setVisibility(View.GONE);
        mAgeEditText.setVisibility(View.VISIBLE);
        if (mUserProfile.getType().equals(getString(R.string.player_item))) {
            mHeight.setVisibility(View.GONE);
            mHeightEditText.setVisibility(View.VISIBLE);
            mWeight.setVisibility(View.GONE);
            mWeightEditText.setVisibility(View.VISIBLE);
            mPosition.setVisibility(View.GONE);
            mPositionEditText.setVisibility(View.VISIBLE);
            mAboutMe.setVisibility(View.GONE);
            mAboutMeEditText.setVisibility(View.VISIBLE);
        }

        mNameEditText.setText(mName.getText());
        mAgeEditText.setText(mAge.getText());
        mHeightEditText.setText(String.valueOf(mHeight.getText().toString().trim()));
        mWeightEditText.setText(String.valueOf(mWeight.getText().toString().trim()));
        mPositionEditText.setText(mPosition.getText());
        mAboutMeEditText.setText(mAboutMe.getText());
        mUserLayout.setFocusableInTouchMode(true);
    }

    @OnClick(R.id.edit_profile_save)
    public void onSaveProfileClick() {
        hideKeyboard(getActivity());
        mIsEditable = false;
        mEditProfile.setVisibility(View.VISIBLE);
        mSaveProfile.setVisibility(View.INVISIBLE);

        mName.setVisibility(View.VISIBLE);
        mNameEditText.setVisibility(View.GONE);
        mAge.setVisibility(View.VISIBLE);
        mAgeEditText.setVisibility(View.GONE);
        if (mUserProfile.getType().equals(getString(R.string.player_item))) {
            mHeight.setVisibility(View.VISIBLE);
            mHeightEditText.setVisibility(View.GONE);
            mWeight.setVisibility(View.VISIBLE);
            mWeightEditText.setVisibility(View.GONE);
            mPosition.setVisibility(View.VISIBLE);
            mPositionEditText.setVisibility(View.GONE);
            mAboutMe.setVisibility(View.VISIBLE);
            mAboutMeEditText.setVisibility(View.GONE);
        }

        mName.setText(mNameEditText.getText().toString());
        mAge.setText(mAgeEditText.getText().toString());
        mHeight.setText(String.format("%s", mHeightEditText.getText().toString()));
        mWeight.setText(String.format("%s", mWeightEditText.getText().toString()));
        mPosition.setText(mPositionEditText.getText().toString());
        mAboutMe.setText(mAboutMeEditText.getText().toString());

        mUserProfile.setName(mName.getText().toString());
        mUserProfile.setHeight(mHeightEditText.getText().toString());
        mUserProfile.setWeight(mWeightEditText.getText().toString());
        mUserProfile.setPosition(mPositionEditText.getText().toString());
        mUserProfile.setProfile(mAboutMe.getText().toString());
        //mUserProfile.setPhoto_url(mCurrentImageUri);

        new Thread() {
            UserManager.UserListener listener;

            Thread addListener(UserManager.UserListener listener) {
                this.listener = listener;
                return this;
            }

            @Override
            public void run() {
                if (mCurrentImageUri != null) {
                    Bitmap pickedImage = null;
                    try {
                        int imageSize = (int) getResources().getDimension(R.dimen.size_of_profile);

                        pickedImage = Glide
                                .with(getContext())
                                .load(mCurrentImageUri)
                                .asBitmap()
                                .override(imageSize, imageSize)
                                .atMost()
                                .dontTransform()
                                .into(imageSize, imageSize)
                                .get();

                        /*pickedImage = Picasso.with(getContext())
                                .load(mCurrentImageUri)
                                .resize(imageSize, imageSize)
                                .centerInside()
                                .onlyScaleDown()
                                .get();*/

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Error on photo upload", Toast.LENGTH_SHORT).show();
                    }

                    if (pickedImage != null) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        pickedImage.compress(Bitmap.CompressFormat.JPEG, 85, stream);
                        byte[] byteArray = stream.toByteArray();
                        mUserProfile.setPhotoByteArray(byteArray);
                        mCurrentImageUri = null;
                    }
                }

                UserManager.getInstance().updateUserProfile(mUserProfile, listener);
            }
        }.addListener(this).start();
    }


    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @OnClick(R.id.edit_profile_add_team_button)
    public void onAddTeamClick() {
        if (mUserProfile.getTeams().size() == MAX_TEAMS_SIZE) {
            Toast.makeText(getContext(), R.string.max_teams_size_error_message, Toast.LENGTH_SHORT).show();
            return;
        }
        if (mUserProfile.getTeams().size() >= 1 && mUserProfile.getType().equals(getString(R.string.manager_item))) {
            Toast.makeText(getContext(), R.string.max_manager_teams_size_error_message, Toast.LENGTH_SHORT).show();
            return;
        }
        mCallback.onAddTeamClick();
    }

    @OnClick(R.id.edit_profile_image)
    public void onProfileImageClick() {
        if (!mIsEditable) return;

        int permissionCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Nammu.askForPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionCallback() {
                @Override
                public void permissionGranted() {
                    EasyImage.openGallery(EditProfileFragment.this, EasyImageConfig.REQ_SOURCE_CHOOSER);
                }

                @Override
                public void permissionRefused() {
                }
            });
        } else {
            EasyImage.openGallery(EditProfileFragment.this, EasyImageConfig.REQ_SOURCE_CHOOSER);
        }
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, getString(R.string.pick_image_dialog_text)),
//                PICK_IMAGE_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Nammu.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                e.printStackTrace();
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                mCurrentImageUri = imageFile.getPath();

                Glide.with(getContext())
                        .load(mCurrentImageUri)
                        .centerCrop()
                        .into(mProfilePhoto);
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                if (source == EasyImage.ImageSource.CAMERA) {
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(getContext());
                    if (photoFile != null) photoFile.delete();
                }
            }
        });

//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
//            mCurrentImageUri = data.getData();
//
//            Glide.with(getContext())
//                    .load(mCurrentImageUri)
//                    .centerCrop()
//                    .into(mProfilePhoto);
//            /*Picasso.with(getContext())
//                    .load(mCurrentImageUri)
//                    //.resize(mMessageImage.getWidth(), mMessageImage.getHeight())
//                    //.centerCrop()
//                    .centerInside()
//                    .fit()
//                    .into(mProfilePhoto);*/
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        hideKeyboard(getActivity());
    }

    @OnClick({R.id.footer_facebook_icon,
            R.id.footer_twitter_icon,
            R.id.footer_instagram_icon,
            R.id.footer_youtube_icon,
            R.id.footer_snapchat_icon})
    public void onSocialClick(View view) {
        String url = null;
        switch (view.getId()) {
            case R.id.footer_facebook_icon: {
                url = getString(R.string.facebook_url);
                break;
            }
            case R.id.footer_twitter_icon: {
                url = getString(R.string.twitter_url);
                break;
            }
            case R.id.footer_instagram_icon: {
                url = getString(R.string.instagram_url);
                break;
            }
            case R.id.footer_youtube_icon: {
                url = getString(R.string.youtube_url);
                break;
            }
            case R.id.footer_snapchat_icon: {
                url = getString(R.string.snapchat_url);
                break;
            }
        }

        try {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(i);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    /*public void onUserLogin(ParseUser user) {

    }*/
    public void onUserLogin(String userid) {

    }

    @Override
    public void onSuccess() {
        if (isVisible()) {
            Toast.makeText(getContext(), "Profile updated", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onError(String message) {
        if (isVisible()) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    public interface EditProfileListener {

        void onAddTeamClick();

        void onEditTeamClick(Team team);
    }
}
