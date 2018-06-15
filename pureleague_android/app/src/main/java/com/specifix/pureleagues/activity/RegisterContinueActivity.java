package com.specifix.pureleagues.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.specifix.pureleagues.R;
import com.specifix.pureleagues.api.DataManager;
import com.specifix.pureleagues.api.UserManager;
import com.specifix.pureleagues.model.Team;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.specifix.pureleagues.activity.RegisterPickerActivity.DATA_ASSOCIATION_TYPE;
import static com.specifix.pureleagues.activity.RegisterPickerActivity.DATA_CLUB_TYPE;
import static com.specifix.pureleagues.activity.RegisterPickerActivity.DATA_DIVISION_TYPE;
import static com.specifix.pureleagues.activity.RegisterPickerActivity.DATA_TEAM_COLOURS_TYPE;
import static com.specifix.pureleagues.activity.RegisterPickerActivity.GRID_LAYOUT_TYPE;
import static com.specifix.pureleagues.activity.RegisterPickerActivity.LINEAR_LAYOUT_TYPE;
import static com.specifix.pureleagues.activity.RegisterPickerActivity.PICKER_ASSOCIATION_ID;
import static com.specifix.pureleagues.activity.RegisterPickerActivity.PICKER_DATA_KEY;
import static com.specifix.pureleagues.activity.RegisterPickerActivity.PICKER_DATA_TYPE_KEY;
import static com.specifix.pureleagues.activity.RegisterPickerActivity.PICKER_DIVISION_ID;
import static com.specifix.pureleagues.activity.RegisterPickerActivity.PICKER_LAYOUT_TYPE_KEY;
import static com.specifix.pureleagues.activity.RegisterPickerActivity.PICKER_SELECTED_ITEM_ASSOCIATION_ID_KEY;
import static com.specifix.pureleagues.activity.RegisterPickerActivity.PICKER_SELECTED_ITEM_DIVISIONSEAS_ID_KEY;
import static com.specifix.pureleagues.activity.RegisterPickerActivity.PICKER_SELECTED_ITEM_KEY;
import static com.specifix.pureleagues.activity.RegisterPickerActivity.PICKER_SELECTED_ITEM_TEAM_DIVISION_ID_KEY;
import static com.specifix.pureleagues.activity.RegisterPickerActivity.PICKER_SELECTED_ITEM_TEAM_ID_KEY;
import static com.specifix.pureleagues.activity.RegisterPickerActivity.PICKER_TITLE_KEY;
import static com.specifix.pureleagues.fragment.EditProfileFragment.TEAM_ID;
import static com.specifix.pureleagues.fragment.EditTeamFragment.CHANNEL_PREFIX;

public class RegisterContinueActivity extends AppCompatActivity implements UserManager.UserListener {
    public static final String ADD_TEAM_KEY = "add_team";
    private static final int ASSOCIATION_PICK_RC = 5201;
    private static final int CLUB_PICK_RC = 5202;
    private static final int DIVISION_PICK_RC = 5203;
    private static final int TEAM_COLOURS_PICK_RC = 5204;
    private static final int TEAM_COLORS_COUNT = 20;
    @BindView(R.id.register_continue_association_layout)
    LinearLayout mAssociationLayout;
    @BindView(R.id.register_continue_submit_layout)
    LinearLayout mSubmitLayout;
    @BindView(R.id.register_continue_association_picker)
    TextView mAssociationPicker;
    @BindView(R.id.register_continue_club_textview)
    TextView mClubPicker;
    @BindView(R.id.register_continue_division_text_view)
    TextView mDivisionPicker;
    @BindView(R.id.register_continue_team_colors)
    TextView mTeamColorPicker;
    @BindView(R.id.register_continue_next_button)
    Button mNextBtn;
    @BindView(R.id.register_continue_submit_button)
    Button mSubmitBtn;
    private long mAssociationId;
    private long mDivisionId;
    private Team mTeam;
    private boolean mIsAddTeam;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_continue);

        ButterKnife.bind(this);

        mIsAddTeam = getIntent().getBooleanExtra(ADD_TEAM_KEY, false);
        if (mIsAddTeam) {
            mSubmitBtn.setText(R.string.welcome_add_team_button_text_button_text);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ASSOCIATION_PICK_RC: {
                    mAssociationPicker.setText(data.getStringExtra(PICKER_SELECTED_ITEM_KEY));
                    mAssociationId = data.getLongExtra(PICKER_SELECTED_ITEM_ASSOCIATION_ID_KEY, 0);
                    mDivisionPicker.setEnabled(true);
                    break;
                }
                case CLUB_PICK_RC: {
                    mTeam = new Team(
                            data.getLongExtra(PICKER_SELECTED_ITEM_TEAM_ID_KEY, 0),
                            data.getLongExtra(PICKER_SELECTED_ITEM_TEAM_DIVISION_ID_KEY, 0),
                            mDivisionPicker.getText().toString(),
                            data.getStringExtra(PICKER_SELECTED_ITEM_KEY),
                            ""
                    );
                    mClubPicker.setText(mTeam.getClub());
                    mTeamColorPicker.setEnabled(true);
                    mTeamColorPicker.setText(R.string.team_colors_hint);
                    break;
                }
                case DIVISION_PICK_RC: {
                    refreshPartTwo();
                    mDivisionPicker.setText(data.getStringExtra(PICKER_SELECTED_ITEM_KEY));
                    mDivisionId = data.getLongExtra(PICKER_SELECTED_ITEM_DIVISIONSEAS_ID_KEY, 0);
                    mClubPicker.setEnabled(true);
                    break;
                }
                case TEAM_COLOURS_PICK_RC: {
                    mTeam.setColor(data.getStringExtra(PICKER_SELECTED_ITEM_KEY));
                    mTeamColorPicker.setText(mTeam.getColor());
                    break;
                }
            }
        }
    }

    private boolean checkInputPartOne() {
        if (mAssociationPicker.getText().equals(getString(R.string.football_association_hint))) {
            onAssociationClick();
            return false;
        }
        return true;
    }

    private boolean checkInputPartTwo() {
        if (mDivisionPicker.getText().equals(getString(R.string.division_hint)) || mDivisionId <= 0) {
            Toast.makeText(this, R.string.division_select_message, Toast.LENGTH_SHORT).show();
//            onDivisionClick();
            return false;
        }
        if (mClubPicker.getText().equals(getString(R.string.club_hint)) || mTeam == null) {
            Toast.makeText(this, R.string.club_select_message, Toast.LENGTH_SHORT).show();
//            onClubClick();
            return false;
        }
        if (mTeamColorPicker.getText().equals(getString(R.string.team_colors_hint))) {
            Toast.makeText(this, R.string.team_colours_select_message, Toast.LENGTH_SHORT).show();
//            onTeamColorsClick();
            return false;
        }

        return true;
    }

    private void refreshPartTwo() {
        mDivisionPicker.setText(R.string.division_hint);
        mClubPicker.setText(R.string.club_hint);
        mTeamColorPicker.setText(R.string.team_colors_hint);

        mTeam = null;
        mDivisionId = -1;
    }

    @OnClick(R.id.register_continue_association_picker)
    public void onAssociationClick() {
        Intent intent = new Intent(RegisterContinueActivity.this, RegisterPickerActivity.class);
        intent.putExtra(PICKER_TITLE_KEY, getString(R.string.football_association));
        intent.putExtra(PICKER_LAYOUT_TYPE_KEY, LINEAR_LAYOUT_TYPE);
        intent.putExtra(PICKER_DATA_TYPE_KEY, DATA_ASSOCIATION_TYPE);
        startActivityForResult(intent, ASSOCIATION_PICK_RC);
    }

    @OnClick(R.id.register_continue_club_textview)
    public void onClubClick() {
        Intent intent = new Intent(RegisterContinueActivity.this, RegisterPickerActivity.class);
        intent.putExtra(PICKER_TITLE_KEY, getString(R.string.club_hint));
        intent.putExtra(PICKER_LAYOUT_TYPE_KEY, LINEAR_LAYOUT_TYPE);
        intent.putExtra(PICKER_DATA_TYPE_KEY, DATA_CLUB_TYPE);
        intent.putExtra(PICKER_DIVISION_ID, mDivisionId);
        startActivityForResult(intent, CLUB_PICK_RC);
    }

    @OnClick(R.id.register_continue_division_text_view)
    public void onDivisionClick() {
        Intent intent = new Intent(RegisterContinueActivity.this, RegisterPickerActivity.class);
        intent.putExtra(PICKER_TITLE_KEY, getString(R.string.division_hint));
        intent.putExtra(PICKER_LAYOUT_TYPE_KEY, LINEAR_LAYOUT_TYPE);
        intent.putExtra(PICKER_DATA_TYPE_KEY, DATA_DIVISION_TYPE);
        intent.putExtra(PICKER_ASSOCIATION_ID, mAssociationId);
        startActivityForResult(intent, DIVISION_PICK_RC);
    }

    @OnClick(R.id.register_continue_team_colors)
    public void onTeamColorsClick() {
        Intent intent = new Intent(RegisterContinueActivity.this, RegisterPickerActivity.class);
        intent.putExtra(PICKER_TITLE_KEY, getString(R.string.team_colors_title));
        intent.putExtra(PICKER_LAYOUT_TYPE_KEY, GRID_LAYOUT_TYPE);
        intent.putExtra(PICKER_DATA_TYPE_KEY, DATA_TEAM_COLOURS_TYPE);
        intent.putExtra(PICKER_DATA_KEY, UserManager.getInstance().getTeamColorNames());
        startActivityForResult(intent, TEAM_COLOURS_PICK_RC);
    }

    @OnClick(R.id.register_continue_next_button)
    public void onContinueClick() {
        if (!checkInputPartOne()) {
            return;
        }
        mAssociationLayout.setVisibility(View.GONE);
        mSubmitLayout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.register_continue_submit_button)
    public void onSubmitClick() {
        if (!checkInputPartTwo()) {
            return;
        }

        UserManager.getInstance().addTeam(mTeam, this);
        mSubmitBtn.setEnabled(false);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.registering_message));
        mProgressDialog.show();
    }

    @Override
    public void onBackPressed() {
        if (mAssociationLayout.getVisibility() == View.GONE) {
            mAssociationLayout.setVisibility(View.VISIBLE);
            mSubmitLayout.setVisibility(View.GONE);
            refreshPartTwo();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onUserLogin(ParseUser user) {

    }

    @Override
    public void onError(String message) {
        mSubmitBtn.setEnabled(true);
        Toast.makeText(RegisterContinueActivity.this, message, Toast.LENGTH_SHORT).show();
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onSuccess() {
        mSubmitBtn.setEnabled(true);
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("user", ParseUser.getCurrentUser());
        installation.saveInBackground();
        PreferenceManager.getDefaultSharedPreferences(this).edit().putLong(TEAM_ID, mTeam.getTeamId()).apply();
        ParsePush.subscribeInBackground(CHANNEL_PREFIX + mTeam.getTeamId());
        UserManager.getInstance().setCurrentTeamId(mTeam.getTeamId());
        DataManager.getInstance().downloadEvents(mTeam, this, new DataManager.DataManagerUpdateEvent() {
            @Override
            public void onFixturesReady() {
                if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                Intent intent = new Intent(RegisterContinueActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

            @Override
            public void onResultsReady() {

            }
        });
    }
}
