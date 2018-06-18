package com.specifix.pureleagues.fragment;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParsePush;
import com.parse.ParseUser;
import com.specifix.pureleagues.R;
import com.specifix.pureleagues.activity.RegisterPickerActivity;
import com.specifix.pureleagues.api.UserManager;
import com.specifix.pureleagues.model.Team;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static com.specifix.pureleagues.activity.RegisterPickerActivity.DATA_TEAM_COLOURS_TYPE;
import static com.specifix.pureleagues.activity.RegisterPickerActivity.GRID_LAYOUT_TYPE;
import static com.specifix.pureleagues.activity.RegisterPickerActivity.PICKER_DATA_KEY;
import static com.specifix.pureleagues.activity.RegisterPickerActivity.PICKER_DATA_TYPE_KEY;
import static com.specifix.pureleagues.activity.RegisterPickerActivity.PICKER_LAYOUT_TYPE_KEY;
import static com.specifix.pureleagues.activity.RegisterPickerActivity.PICKER_SELECTED_ITEM_KEY;
import static com.specifix.pureleagues.activity.RegisterPickerActivity.PICKER_TITLE_KEY;

public class EditTeamFragment extends Fragment implements UserManager.UserListener {
    public static final String CHANNEL_PREFIX = "ch_";
    private static final String CLUB_NAME_KEY = "club_name";
    private static final String CLUB_DIVISION_KEY = "club_division";
    private static final String CLUB_COLOR_KEY = "club_color";
    private static final String ALLOW_NOTIFICATION_KEY = "allow_notification";
    private static final int TEAM_COLOURS_PICK_RC = 4377;
    private static final String CLUB_TEAM_ID_KEY = "team_id";
    private static final String CLUB_DIVISION_ID_KEY = "division_id";
    @BindView(R.id.edit_team_title)
    TextView mTitle;
    @BindView(R.id.edit_team_club_name)
    TextView mName;
    @BindView(R.id.edit_team_division)
    TextView mDivision;
    @BindView(R.id.edit_team_shirt_image)
    ImageView mShirt;
    @BindView(R.id.edit_team_shirt_label)
    TextView mShirtLabel;
    @BindView(R.id.edit_team_notifications_switch)
    SwitchCompat mNotifSwitch;
    @BindView(R.id.edit_team_notifications_label)
    TextView mNotifLabel;
    @BindView(R.id.edit_team_allow_notif_label)
    TextView mAllowNotifLabel;
    @BindView(R.id.edit_team_delete_button)
    Button mDeleteBtn;
    private EditTeamListener mCallback;
    private Team mTeam;
    private ProgressDialog mProgressDialog;

    public static EditTeamFragment newInstance(Team team) {
        EditTeamFragment fragment = new EditTeamFragment();
        Bundle args = new Bundle();
        args.putLong(CLUB_TEAM_ID_KEY, team.getTeamId());
        args.putLong(CLUB_DIVISION_ID_KEY, team.getDivisionId());
        args.putString(CLUB_NAME_KEY, team.getClub());
        args.putString(CLUB_DIVISION_KEY, team.getDivision());
        args.putString(CLUB_COLOR_KEY, team.getColor());
        args.putBoolean(ALLOW_NOTIFICATION_KEY, team.isAllowNotifications());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context Context) {
        super.onAttach(Context);

        try {
            mCallback = (EditTeamFragment.EditTeamListener) Context;
        } catch (ClassCastException e) {
            throw new ClassCastException(Context.toString()
                    + " must implement EditTeamListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        mTeam = new Team(
                args.getLong(CLUB_TEAM_ID_KEY),
                args.getLong(CLUB_DIVISION_ID_KEY),
                args.getString(CLUB_DIVISION_KEY),
                args.getString(CLUB_NAME_KEY),
                args.getString(CLUB_COLOR_KEY)
        );
        mTeam.setAllowNotifications(args.getBoolean(ALLOW_NOTIFICATION_KEY));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_team, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mName.setText(mTeam.getClub());
        mDivision.setText(mTeam.getDivision());
        mShirt.setImageResource(UserManager.getInstance().getTeamColorRes(mTeam.getColor()));
        mNotifSwitch.setChecked(mTeam.isAllowNotifications());
        mNotifSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mTeam.setAllowNotifications(isChecked);
                UserManager.getInstance().updateTeam(mTeam);
                if (isChecked){
                    ParsePush.subscribeInBackground(CHANNEL_PREFIX + mTeam.getTeamId());
                } else {
                    ParsePush.unsubscribeInBackground(CHANNEL_PREFIX + mTeam.getTeamId());
                }
            }
        });

        Typeface gothamBold = Typeface.createFromAsset(getContext().getAssets(), "GothamBold.otf");
        Typeface gothamBook = Typeface.createFromAsset(getContext().getAssets(), "GothamBook.otf");
        mTitle.setTypeface(gothamBold);
        mName.setTypeface(gothamBold);
        mDivision.setTypeface(gothamBook);
        mShirtLabel.setTypeface(gothamBook);
        mNotifLabel.setTypeface(gothamBold);
        mAllowNotifLabel.setTypeface(gothamBook);
        mDeleteBtn.setTypeface(gothamBold);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TEAM_COLOURS_PICK_RC: {
                    mShirt.setImageResource(UserManager.getInstance().getTeamColorRes(data.getStringExtra(PICKER_SELECTED_ITEM_KEY)));
                    mTeam.setColor(data.getStringExtra(PICKER_SELECTED_ITEM_KEY));
                    UserManager.getInstance().updateTeam(mTeam);
                    mCallback.onTeamColoursChanged(data.getStringExtra(PICKER_SELECTED_ITEM_KEY), false);
                    break;
                }
            }
        }
    }

    @OnClick(R.id.edit_team_shirt_picker_layout)
    public void onShirtClick() {
        Intent intent = new Intent(getContext(), RegisterPickerActivity.class);
        intent.putExtra(PICKER_TITLE_KEY, getString(R.string.edit_team_title));
        intent.putExtra(PICKER_LAYOUT_TYPE_KEY, GRID_LAYOUT_TYPE);
        intent.putExtra(PICKER_DATA_TYPE_KEY, DATA_TEAM_COLOURS_TYPE);
        intent.putExtra(PICKER_DATA_KEY, UserManager.getInstance().getTeamColorNames());
        startActivityForResult(intent, TEAM_COLOURS_PICK_RC);
    }

    @OnClick(R.id.edit_team_delete_button)
    public void onDeleteClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle(getString(R.string.delete_team_confirm_message) + " " + mTeam.getClub() + "?")
                .setPositiveButton(R.string.delete_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ParsePush.unsubscribeInBackground(CHANNEL_PREFIX + mTeam.getTeamId());
                        UserManager.getInstance().deleteTeam(mTeam, EditTeamFragment.this);
                        mProgressDialog = new ProgressDialog(getContext());
                        mProgressDialog.setMessage(getString(R.string.deleting_message));
                        mProgressDialog.setCancelable(false);
                        mProgressDialog.show();
                    }
                })
                .setNegativeButton(R.string.no_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
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
    public void onSuccess() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        Toast.makeText(getContext(), "Team deleted", Toast.LENGTH_SHORT).show();
        mCallback.onTeamDeleted();

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
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public interface EditTeamListener {

        void onTeamColoursChanged(String color, boolean backToDashboard);

        void onTeamDeleted();
    }
}
