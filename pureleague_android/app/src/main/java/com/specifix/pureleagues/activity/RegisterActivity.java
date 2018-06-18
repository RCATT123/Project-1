package com.specifix.pureleagues.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;
import com.specifix.pureleagues.R;
import com.specifix.pureleagues.api.UserManager;
import com.specifix.pureleagues.model.User;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.GONE;
import static com.specifix.pureleagues.activity.RegisterPickerActivity.DATA_SIMPLE_TYPE;
import static com.specifix.pureleagues.activity.RegisterPickerActivity.LINEAR_LAYOUT_TYPE;
import static com.specifix.pureleagues.activity.RegisterPickerActivity.PICKER_DATA_KEY;
import static com.specifix.pureleagues.activity.RegisterPickerActivity.PICKER_DATA_TYPE_KEY;
import static com.specifix.pureleagues.activity.RegisterPickerActivity.PICKER_LAYOUT_TYPE_KEY;
import static com.specifix.pureleagues.activity.RegisterPickerActivity.PICKER_SELECTED_ITEM_KEY;
import static com.specifix.pureleagues.activity.RegisterPickerActivity.PICKER_TITLE_KEY;

public class RegisterActivity extends AppCompatActivity implements UserManager.UserListener{
    public static final int MIN_USERNAME_LENGTH = 6;
    public static final int MIN_PASSWORD_LENGTH = 8;
    private static final int GENDER_PICK_RC = 1001;
    private static final int USER_TYPE_PICK_RC = 1002;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.register_screen_title)
    TextView mRegisterTitle;
    @BindView(R.id.register_part_one)
    LinearLayout mPartOneLayout;
    @BindView(R.id.register_part_two)
    LinearLayout mPartTwoLayout;
    @BindView(R.id.registration_name)
    EditText mName;
    @BindView(R.id.registration_username)
    EditText mUserName;
    @BindView(R.id.registration_email)
    EditText mEmail;
    @BindView(R.id.registration_password)
    EditText mPassword;
    @BindView(R.id.registration_confirm_password)
    EditText mConfirmPassword;
    @BindView(R.id.registration_continue_button)
    Button mContinueBtn;
    @BindView(R.id.register_date_picker)
    TextView mDateTextView;
    @BindView(R.id.register_gender_picker)
    TextView mGenderTextView;
    @BindView(R.id.register_user_type)
    TextView mUserTypeTextView;
    @BindView(R.id.register_submit_button)
    Button mSubmitBtn;
    private PlayerProfileDialog mPlayerProfileDialog;
    private Typeface gothamBold;
    private Typeface gothamBook;
    private String mHeight;
    private String mWeight;
    private String mPosition;
    private String mProfile;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

        initToolbar();

        gothamBold = Typeface.createFromAsset(getAssets(), "GothamBold.otf");
        gothamBook = Typeface.createFromAsset(getAssets(), "GothamBook.otf");

        mRegisterTitle.setTypeface(gothamBold);
        mName.setTypeface(gothamBook);
        mUserName.setTypeface(gothamBook);
        mEmail.setTypeface(gothamBook);
        mPassword.setTypeface(gothamBook);
        mConfirmPassword.setTypeface(gothamBook);
        mDateTextView.setTypeface(gothamBook);
        mGenderTextView.setTypeface(gothamBook);
        mUserTypeTextView.setTypeface(gothamBook);
        mContinueBtn.setTypeface(gothamBold);
        mSubmitBtn.setTypeface(gothamBold);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPlayerProfileDialog != null && mPlayerProfileDialog.isShowing()) {
            mPlayerProfileDialog.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GENDER_PICK_RC: {
                    mGenderTextView.setText(data.getStringExtra(PICKER_SELECTED_ITEM_KEY));
                    break;
                }
                case USER_TYPE_PICK_RC: {
                    mUserTypeTextView.setText(data.getStringExtra(PICKER_SELECTED_ITEM_KEY));
                    if (data.getStringExtra(PICKER_SELECTED_ITEM_KEY).equals(getString(R.string.player_item))) {
//                        showPlayerProfileDialog();
                    }
                    break;
                }
            }
        }
    }

    private void showPlayerProfileDialog() {
        mPlayerProfileDialog = new PlayerProfileDialog(this, new ProfileInfoListener() {
            @Override
            public void onInfoSubmitted(String height, String weight, String position, String profile) {
                mHeight = height;
                mWeight = weight;
                mPosition = position;
                mProfile = profile;
            }
        });
        mPlayerProfileDialog.show();
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    public void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(
                this,
                R.style.AppTheme_DatePicker,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mDateTextView.setText(String.format(
                                Locale.getDefault(),
                                "%02d.%02d.%s",
                                dayOfMonth,
                                month + 1,
                                year));

                    }
                }, year, month, day);
        datePicker.getDatePicker().setMaxDate(new Date().getTime());
        datePicker.show();
    }

    private boolean checkCredentialsPartOne() {
        String name = mName.getText().toString().trim();
        mName.setText(name);
        String username = mUserName.getText().toString().trim().toLowerCase();
        mUserName.setText(username);
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        String confirmPassword = mConfirmPassword.getText().toString();

        if (name.equals("")) {
            mName.setError(getString(R.string.name_empty_error_message));
            return false;
        }
        mName.setError(null);

        if (username.equals("")) {
            mUserName.setError(getString(R.string.username_empty_error_message));
            return false;
        }
        if (username.split(" ").length > 1) {
            mUserName.setError(getString(R.string.username_contains_spaces_error_message));
            return false;
        }
        if (username.length() < MIN_USERNAME_LENGTH) {
            mUserName.setError(String.format(getString(R.string.username_min_length_error_message), MIN_USERNAME_LENGTH));
            return false;
        }
        mUserName.setError(null);

        if (email.equals("")) {
            mEmail.setError(getString(R.string.email_empty_error_message));
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError(getString(R.string.email_error_message));
            return false;
        }
        mEmail.setError(null);

        if (password.equals("")) {
            mPassword.setError(getString(R.string.password_empty_error_message));
            return false;
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            mPassword.setError(String.format(getString(R.string.password_min_length_error_message), MIN_PASSWORD_LENGTH));
            return false;
        }
        mPassword.setError(null);

        if (confirmPassword.equals("")) {
            mConfirmPassword.setError(getString(R.string.password_empty_error_message));
            return false;
        }
        if (!confirmPassword.equals(password)) {
            mConfirmPassword.setError(getString(R.string.password_not_match_error_message));
            return false;
        }
        mConfirmPassword.setError(null);

        return true;
    }

    private boolean checkCredentialsPartTwo() {
        String dateOfBirth = mDateTextView.getText().toString();
        String gender = mGenderTextView.getText().toString();
        String userType = mUserTypeTextView.getText().toString();

        if (dateOfBirth.equals(getString(R.string.date_of_birth_hint))) {
            showDatePickerDialog();
            return false;
        }

        if (gender.equals(getString(R.string.gender_hint))) {
            onGenderPickerClick();
            return false;
        }

        if (userType.equals(getString(R.string.user_type_hint))) {
            onUserTypeClick();
            return false;
        }
        return true;
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        view.clearFocus();
    }

    @OnClick(R.id.register_date_picker)
    public void onDatePickerClick() {
        showDatePickerDialog();
    }

    @OnClick(R.id.register_gender_picker)
    public void onGenderPickerClick() {
        Intent intent = new Intent(RegisterActivity.this, RegisterPickerActivity.class);
        intent.putExtra(PICKER_TITLE_KEY, getString(R.string.gender_hint));
        intent.putExtra(PICKER_LAYOUT_TYPE_KEY, LINEAR_LAYOUT_TYPE);
        intent.putExtra(PICKER_DATA_TYPE_KEY, DATA_SIMPLE_TYPE);
        intent.putExtra(PICKER_DATA_KEY, getResources().getStringArray(R.array.genders));
        startActivityForResult(intent, GENDER_PICK_RC);
    }

    @OnClick(R.id.register_user_type)
    public void onUserTypeClick() {
        Intent intent = new Intent(RegisterActivity.this, RegisterPickerActivity.class);
        intent.putExtra(PICKER_TITLE_KEY, getString(R.string.user_type_hint));
        intent.putExtra(PICKER_LAYOUT_TYPE_KEY, LINEAR_LAYOUT_TYPE);
        intent.putExtra(PICKER_DATA_TYPE_KEY, DATA_SIMPLE_TYPE);
        intent.putExtra(PICKER_DATA_KEY, getResources().getStringArray(R.array.user_types));
        startActivityForResult(intent, USER_TYPE_PICK_RC);
    }

    @OnClick(R.id.registration_continue_button)
    public void onContinueClick() {
        hideKeyboard();
        if (!checkCredentialsPartOne()) {
            return;
        }
        mPartOneLayout.setVisibility(GONE);
        mPartTwoLayout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.register_submit_button)
    public void onSubmitClick() {
        if (!checkCredentialsPartTwo()) {
            return;
        }
        User user = new User(
                null,
                mName.getText().toString(),
                mUserName.getText().toString().toLowerCase(),
                mPassword.getText().toString(),
                mEmail.getText().toString(),
                mDateTextView.getText().toString(),
                mGenderTextView.getText().toString(),
                mUserTypeTextView.getText().toString());

        if (user.getType().equals(getString(R.string.player_item))) {
            user.setHeight(mHeight);
            user.setWeight(mWeight);
            user.setPosition(mPosition);
            user.setProfile(mProfile);
        }

        UserManager.getInstance().registerUser(user, this);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.registering_user_message));
        mProgressDialog.show();
    }

    @Override
    public void onBackPressed() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            super.onBackPressed();
        }
        if (mPartOneLayout.getVisibility() == GONE) {
            mPartOneLayout.setVisibility(View.VISIBLE);
            mPartTwoLayout.setVisibility(GONE);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onSuccess() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }

        Intent intent = new Intent(RegisterActivity.this, RegisterContinueActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    /*public void onUserLogin(ParseUser user) {

    }*/
    public void onUserLogin() {

    }

    @Override
    public void onError(String message) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    class PlayerProfileDialog extends Dialog {
        @BindView(R.id.profile_height_label)
        TextView mHeightLabel;
        @BindView(R.id.profile_weight_label)
        TextView mWeightLabel;
        @BindView(R.id.profile_position_label)
        TextView mPositionLabel;
        @BindView(R.id.profile_label)
        TextView mProfileLabel;
        @BindView(R.id.profile_height_edit_text)
        EditText mHeightEditText;
        @BindView(R.id.profile_weight_edit_text)
        EditText mWeightEditText;
        @BindView(R.id.profile_position_edit_text)
        EditText mPositionEditText;
        @BindView(R.id.profile_edit_text)
        EditText mProfileEditText;
        @BindView(R.id.profile_submit_button)
        Button mSubmitBtn;
        private ProfileInfoListener mSubmitListener;

        PlayerProfileDialog(Context context, ProfileInfoListener listener) {
            super(context);
            getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_player_profile);

            ButterKnife.bind(this);

            mSubmitListener = listener;

            mHeightLabel.setTypeface(gothamBold);
            mWeightLabel.setTypeface(gothamBold);
            mPositionLabel.setTypeface(gothamBold);
            mProfileLabel.setTypeface(gothamBold);
            mHeightEditText.setTypeface(gothamBold);
            mWeightEditText.setTypeface(gothamBold);
            mPositionEditText.setTypeface(gothamBold);
            mSubmitBtn.setTypeface(gothamBold);

            mProfileEditText.setTypeface(gothamBook);
        }

        @OnClick(R.id.profile_submit_button)
        void onSubmitClick() {
            mSubmitListener.onInfoSubmitted(
                    mHeightEditText.getText().toString(),
                    mWeightEditText.getText().toString(),
                    mPositionEditText.getText().toString(),
                    mProfileEditText.getText().toString());
            dismiss();
        }
    }

    interface ProfileInfoListener {
        void onInfoSubmitted(String height, String weight, String position, String profile);
    }
}
