package com.specifix.pureleagues.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.specifix.pureleagues.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.specifix.pureleagues.activity.RegisterContinueActivity.ADD_TEAM_KEY;

public class WelcomeActivity extends AppCompatActivity{
    @BindView(R.id.welcome_title)
    TextView mTitle;
    @BindView(R.id.welcome_subtitle_textview)
    TextView mSubtitle;
    @BindView(R.id.welcome_login_button)
    Button mLoginBtn;
    @BindView(R.id.welcome_add_team_button)
    Button mAddTeamBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        ButterKnife.bind(this);

        Typeface gothamMedium = Typeface.createFromAsset(getAssets(), "GothamMedium.otf");
        Typeface gothamBold = Typeface.createFromAsset(getAssets(), "GothamMedium.otf");
        mTitle.setTypeface(gothamMedium);
        mSubtitle.setTypeface(gothamMedium);
        mLoginBtn.setTypeface(gothamBold);
        mAddTeamBtn.setTypeface(gothamBold);
    }

    @OnClick(R.id.welcome_login_button)
    public void onLoginClick() {
        if (mLoginBtn.getText().equals(getString(R.string.welcome_login_button_text))) {
            Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(WelcomeActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.welcome_add_team_button)
    public void onAddTeamClick() {
        if (mAddTeamBtn.getText().equals(getString(R.string.welcome_add_team_button_text))) {
            mSubtitle.setText(R.string.welcome_register_subtitle_text);
            mLoginBtn.setText(R.string.yes_text);
            mAddTeamBtn.setText(R.string.no_text);
        } else {
            ParseAnonymousUtils.logIn(new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (e != null) {
                        Log.d("MyApp", "Anonymous login failed.");
                    } else {
                        Intent addTeamIntent = new Intent(WelcomeActivity.this, RegisterContinueActivity.class);
                        addTeamIntent.putExtra(ADD_TEAM_KEY, true);
                        startActivity(addTeamIntent);
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        if (!mAddTeamBtn.getText().equals(getString(R.string.welcome_add_team_button_text))) {
            mSubtitle.setText(R.string.welcome_screen_subtitle);
            mLoginBtn.setText(R.string.welcome_login_button_text);
            mAddTeamBtn.setText(R.string.welcome_add_team_button_text);
        } else {
            super.onBackPressed();
        }
    }
}
