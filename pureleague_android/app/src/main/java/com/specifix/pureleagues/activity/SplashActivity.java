package com.specifix.pureleagues.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.specifix.pureleagues.R;
import com.specifix.pureleagues.api.DataManager;
import com.specifix.pureleagues.api.UserManager;
import com.specifix.pureleagues.model.Team;
import com.specifix.pureleagues.model.User;
import com.specifix.pureleagues.receiver.PushReceiver;

import java.util.List;

import static com.specifix.pureleagues.fragment.EditProfileFragment.TEAM_ID;
import static com.specifix.pureleagues.receiver.PushReceiver.EVENT_TYPE;
import static com.specifix.pureleagues.receiver.PushReceiver.FROM_PUSH;
import static com.specifix.pureleagues.receiver.PushReceiver.LIST_POSITION;
import static com.specifix.pureleagues.receiver.PushReceiver.PUSH_TEAM_ID;

public class SplashActivity extends AppCompatActivity implements DataManager.DataManagerUpdateEvent {
    private static final long SPLASH_DISPLAY_LENGTH = 1000;
    private boolean mDownloadStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        if (getIntent().getBooleanExtra(FROM_PUSH, false)) {
            PushReceiver.counter = 0;
            String teamId = getIntent().getStringExtra(PUSH_TEAM_ID);
            if (!TextUtils.isEmpty(teamId)) {
                sp.edit().putLong(TEAM_ID, Long.parseLong(teamId)).apply();
            }
//            sp.edit().putLong(TEAM_ID, Long.parseLong(getIntent().getStringExtra(PUSH_TEAM_ID))).apply();
        }
        /*User user = UserManager.getInstance().getCurrentUser();
        if (user != null) {
            List<Team> teamList = user.getTeams();
            if (teamList.size() == 0) {
                goToContinueRegistration();
            } else {
                if (sp.getLong(TEAM_ID, -1) == -1) {
                    long teamId = teamList.get(0).getTeamId();
                    sp.edit().putLong(TEAM_ID, teamId).apply();
                    UserManager.getInstance().setCurrentTeamId(teamId);
                    DataManager.getInstance().downloadEvents(teamList.get(0), this, this);
                } else {
                    for (Team team : teamList) {
                        if (team.getTeamId() == sp.getLong(TEAM_ID, -1)) {
                            UserManager.getInstance().setCurrentTeamId(team.getTeamId());
                            DataManager.getInstance().downloadEvents(team, this, this);
                            mDownloadStarted = true;
                        }
                    }
                    if (!mDownloadStarted) {
                        UserManager.getInstance().setCurrentTeamId(teamList.get(0).getTeamId());
                        DataManager.getInstance().downloadEvents(teamList.get(0), this, this);
                        sp.edit().putLong(TEAM_ID, teamList.get(0).getTeamId()).apply();
                    }
                }
            }
        } else {
            onFixturesReady();
        }*/
        onFixturesReady();
    }

    private void goToRegistrationActivity() {
        Intent mainIntent = new Intent(SplashActivity.this, WelcomeActivity.class);
        startActivity(mainIntent);
        finish();
    }

    private void goToContinueRegistration() {
        Intent intent = new Intent(SplashActivity.this, RegisterContinueActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void goToMainActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        if (getIntent().getBooleanExtra(FROM_PUSH, false)) {
            intent.putExtra(FROM_PUSH, true);
            intent.putExtra(EVENT_TYPE, getIntent().getStringExtra(EVENT_TYPE));
            intent.putExtra(LIST_POSITION, getIntent().getStringExtra(LIST_POSITION));
        }
        startActivity(intent);
    }


    @Override
    public void onFixturesReady() {
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if (UserManager.getInstance().getCurrentUser() == null) {
                    goToRegistrationActivity();
                } else {
                    /*if (UserManager.getInstance().isUserCompleteRegistered()) {
                        goToMainActivity();
                    } else {
                        goToContinueRegistration();
                    }*/
                    goToMainActivity();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    public void onResultsReady() {

    }
}
