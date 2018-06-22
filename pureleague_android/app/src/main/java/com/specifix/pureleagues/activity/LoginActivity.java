package com.specifix.pureleagues.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.parse.ParseUser;
import com.specifix.pureleagues.R;
import com.specifix.pureleagues.api.DataManager;
import com.specifix.pureleagues.api.UserManager;
import com.specifix.pureleagues.manager.PrefsManager;
import com.specifix.pureleagues.model.Team;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.specifix.pureleagues.activity.RegisterActivity.MIN_PASSWORD_LENGTH;
import static com.specifix.pureleagues.activity.RegisterActivity.MIN_USERNAME_LENGTH;
import static com.specifix.pureleagues.activity.RegisterContinueActivity.ADD_TEAM_KEY;
import static com.specifix.pureleagues.fragment.EditProfileFragment.TEAM_ID;

public class LoginActivity extends AppCompatActivity implements UserManager.UserListener{
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.login_screen_title)
    TextView mTitle;
    @BindView(R.id.login_layout)
    LinearLayout mLoginLayout;
    @BindView(R.id.login_username_edittext)
    EditText mUsernameEditText;
    @BindView(R.id.login_password_edittext)
    EditText mPasswordEditText;
    @BindView(R.id.login_forgot_password_textview)
    TextView mForgotPassTextView;
    @BindView(R.id.login_register_text_view)
    TextView mRegisterTextView;
    @BindView(R.id.login_submit_button)
    Button mSubmitBtn;
    @BindView(R.id.login_email_layout)
    LinearLayout mEmailLayout;
    @BindView(R.id.login_recovery_email_edittext)
    EditText mEmailEditText;
    @BindView(R.id.login_email_submit_button)
    Button mEmailSubmitBtn;
    @BindView(R.id.login_password_recovery_text)
    TextView mPassRecoveryTextView;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        initToolbar();

        Typeface gothamBold = Typeface.createFromAsset(getAssets(), "GothamBold.otf");
        Typeface gothamMedium = Typeface.createFromAsset(getAssets(), "GothamMedium.otf");
        Typeface gothamBook = Typeface.createFromAsset(getAssets(), "GothamBook.otf");
        mTitle.setTypeface(gothamBold);
        mUsernameEditText.setTypeface(gothamBook);
        mPasswordEditText.setTypeface(gothamBook);
        mEmailEditText.setTypeface(gothamBook);
        mForgotPassTextView.setTypeface(gothamMedium);
        mRegisterTextView.setTypeface(gothamMedium);
        mSubmitBtn.setTypeface(gothamBold);
        mEmailSubmitBtn.setTypeface(gothamBold);
        mPassRecoveryTextView.setTypeface(gothamBook);
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    @OnClick(R.id.login_forgot_password_textview)
    public void onForgotPasswordClick() {
        mLoginLayout.setVisibility(View.GONE);
        mEmailLayout.setVisibility(View.VISIBLE);
        mEmailEditText.requestFocus();
    }

    @OnClick(R.id.login_submit_button)
    public void onSubmitClick() {
        hideKeyboard();

        //ParseUser.logOut();
        if (!checkCredentials()) {
            System.out.println("LoginActivity.onSubmitClick === check credenitals");
            return;
        }

        mSubmitBtn.setEnabled(false);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.login_in_message));
        mProgressDialog.setCancelable(false);
        //mProgressDialog.show();

        UserManager.getInstance().loginUser(
                mUsernameEditText.getText().toString().trim().toLowerCase(),
                mPasswordEditText.getText().toString(), this);
    }

    @OnClick(R.id.login_email_submit_button)
    public void onEmailResetClick() {
        if (!checkEmail()) {
            return;
        }
        UserManager.getInstance().resetPassword(mEmailEditText.getText().toString(), this);
        mEmailSubmitBtn.setEnabled(false);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(getString(R.string.sending_email_message));
        mProgressDialog.show();
    }

    @OnClick(R.id.login_register_text_view)
    public void onRegisterClick() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
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

    private boolean checkCredentials() {
        String username = mUsernameEditText.getText().toString().trim();
        mUsernameEditText.setText(username);
        if (username.equals("")) {
            mUsernameEditText.setError(getString(R.string.username_empty_error_message));
            return false;
        }
        if (username.trim().length() < MIN_USERNAME_LENGTH) {
            mUsernameEditText.setError(getString(R.string.username_min_length_error_message));
            return false;
        }
        mUsernameEditText.setError(null);

        String password = mPasswordEditText.getText().toString();
        if (password.equals("")) {
            mPasswordEditText.setError(getString(R.string.password_empty_error_message));
            return false;
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            mPasswordEditText.setError(getString(R.string.password_min_length_error_message));
            return false;
        }
        mPasswordEditText.setError(null);

        return true;
    }

    private boolean checkEmail() {
        String email = mEmailEditText.getText().toString();
        if (email.equals("")) {
            mEmailEditText.setError(getString(R.string.email_empty_error_message));
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmailEditText.setError(getString(R.string.email_error_message));
            return false;
        }
        mEmailEditText.setError(null);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            super.onBackPressed();
            return;
        }
        if (mEmailLayout.getVisibility() == View.VISIBLE) {
            mLoginLayout.setVisibility(View.VISIBLE);
            mEmailLayout.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
    }

    //@Override
    //public void onUserLogin(ParseUser user) {
    public void onUserLogin(String userId) {
        mSubmitBtn.setEnabled(true);
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        PrefsManager prefsManager = new PrefsManager();
        prefsManager.setUserId(userId);
        final Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        /*List<Team> teamList = UserManager.getInstance().getCurrentUser().getTeams();
        if (teamList != null && teamList.size() != 0) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
            Team currentTeam = teamList.get(0);
            sp.edit().putLong(TEAM_ID, currentTeam.getTeamId()).apply();
            UserManager.getInstance().setCurrentTeamId(currentTeam.getTeamId());
            DataManager.getInstance().downloadEvents(currentTeam, this, new DataManager.DataManagerUpdateEvent() {
                boolean fixturesReady = false, resultsReady = false;
                @Override
                public void onFixturesReady() {
                    fixturesReady = true;
                    if (resultsReady) {
                        mSubmitBtn.setEnabled(true);
                        if (mProgressDialog != null && mProgressDialog.isShowing()) {
                            mProgressDialog.dismiss();
                        }
                        startActivity(intent);
                    }
                }

                @Override
                public void onResultsReady() {
                    resultsReady = true;
                    if (fixturesReady) {
                        mSubmitBtn.setEnabled(true);
                        if (mProgressDialog != null && mProgressDialog.isShowing()) {
                            mProgressDialog.dismiss();
                        }
                        startActivity(intent);
                    }
                }
            });

        } else {
            mSubmitBtn.setEnabled(true);
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
            Intent addTeamIntent = new Intent(LoginActivity.this, RegisterContinueActivity.class);
            addTeamIntent.putExtra(ADD_TEAM_KEY, true);
            startActivity(intent);
        }*/
    }

    @Override
    public void onSuccess() {
        Toast.makeText(LoginActivity.this, R.string.reset_email_sent, Toast.LENGTH_SHORT).show();
        mEmailSubmitBtn.setEnabled(true);
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onError(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
        mSubmitBtn.setEnabled(true);
        mEmailSubmitBtn.setEnabled(true);
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }

    }
}
