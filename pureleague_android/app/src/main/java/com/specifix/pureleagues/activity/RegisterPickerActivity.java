package com.specifix.pureleagues.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.specifix.pureleagues.R;
import com.specifix.pureleagues.adapter.RegisterPickerAdapter;
import com.specifix.pureleagues.api.DataManager;
import com.specifix.pureleagues.model.Division;
import com.specifix.pureleagues.model.FootballAssociation;
import com.specifix.pureleagues.model.Team;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterPickerActivity extends AppCompatActivity
        implements RegisterPickerAdapter.RegisterPickerListener, DataManager.DataManagerPickListener {
    public static final String PICKER_TITLE_KEY = "picker_title";
    public static final String PICKER_LAYOUT_TYPE_KEY = "picker_layout_type";
    public static final String PICKER_DATA_TYPE_KEY = "picker_data_type";
    public static final String PICKER_DATA_KEY = "picker_data";
    public static final String PICKER_SELECTED_ITEM_KEY = "picker_selected_item";
    public static final String PICKER_SELECTED_ITEM_ASSOCIATION_ID_KEY = "association_id";
    public static final String PICKER_SELECTED_ITEM_DIVISIONSEAS_ID_KEY = "divisionseas_id";
    public static final String PICKER_ASSOCIATION_ID = "association_id";
    public static final String PICKER_DIVISION_ID = "division_id";
    public static final String PICKER_SELECTED_ITEM_TEAM_DIVISION_ID_KEY = "team_division_id";
    public static final String PICKER_SELECTED_ITEM_TEAM_DIVISION_NAME_KEY = "division_name";
    public static final String PICKER_SELECTED_ITEM_TEAM_ID_KEY = "team_id";
    public static final int LINEAR_LAYOUT_TYPE = 0;
    public static final int GRID_LAYOUT_TYPE = 1;
    public static final int DATA_SIMPLE_TYPE = 0;
    public static final int DATA_ASSOCIATION_TYPE = 1;
    public static final int DATA_TEAM_COLOURS_TYPE = 2;
    public static final int DATA_DIVISION_TYPE = 3;
    public static final int DATA_CLUB_TYPE = 4;
    private static final int SPAN_COUNT = 4;
    @BindView(R.id.register_picker_layout)
    ViewGroup mLayout;
    @BindView(R.id.register_picker_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.register_picker_title)
    TextView mPickerTitle;
    @BindView(R.id.register_picker_search_layout)
    LinearLayout mSearchLayout;
    @BindView(R.id.register_picker_search_edittext)
    EditText mSearchEditText;
    @BindView(R.id.register_picker_list)
    RecyclerView mList;
    @BindView(R.id.register_picker_progress_bar)
    ProgressBar mLoadingView;
    @BindView(R.id.footer_facebook_icon)
    ImageView mFacebookBth;
    @BindView(R.id.footer_twitter_icon)
    ImageView mTwitterBtn;
    @BindView(R.id.footer_instagram_icon)
    ImageView mInstagramBtn;
    @BindView(R.id.footer_youtube_icon)
    ImageView mYoutubeBtn;
    @BindView(R.id.footer_snapchat_icon)
    ImageView mSnapchatBtn;
    @BindView(R.id.register_picker_footer)
    ViewGroup mFooter;

    private RegisterPickerAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_picker);

        ButterKnife.bind(this);

        initToolbar();

        initList();

        initKeyboardListener();
    }

    private void initKeyboardListener() {
        mLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int heightDiff = mLayout.getRootView().getHeight() - mLayout.getHeight();
                if (heightDiff > dpToPx(RegisterPickerActivity.this, 200)) { // if more than 200 dp, it's probably a keyboard...
                    mFooter.setVisibility(View.GONE);
                } else {
                    mFooter.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    public static float dpToPx(Context context, float valueInDp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        mPickerTitle.setText(getIntent().getStringExtra(PICKER_TITLE_KEY));
    }

    private void initList() {
        int layoutType = getIntent().getIntExtra(PICKER_LAYOUT_TYPE_KEY, LINEAR_LAYOUT_TYPE);
        int dataType =  getIntent().getIntExtra(PICKER_DATA_TYPE_KEY, DATA_SIMPLE_TYPE);

        if (dataType != DATA_ASSOCIATION_TYPE) {
            mSearchLayout.setVisibility(View.GONE);
        } else {
            mSearchEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    DataManager.getInstance().getAssociations(RegisterPickerActivity.this, s.toString());
                    mList.setVisibility(View.GONE);
                    mLoadingView.setVisibility(View.VISIBLE);
//                    mAdapter.filterList(s.toString());
                }
            });
        }

        mList.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager;
        if (dataType == DATA_ASSOCIATION_TYPE) {
            DataManager.getInstance().getAssociations(this, "");
            mAdapter = new RegisterPickerAdapter(new ArrayList<>(), dataType, this);
        } else if (dataType == DATA_TEAM_COLOURS_TYPE) {
            List<String> list = new ArrayList<>(Arrays.asList(getIntent().getStringArrayExtra(PICKER_DATA_KEY)));
            mAdapter = new RegisterPickerAdapter(list, dataType, this);
        } else if (dataType == DATA_DIVISION_TYPE) {
            DataManager.getInstance().getDivisions(this, getIntent().getLongExtra(PICKER_ASSOCIATION_ID, 0));
            mAdapter = new RegisterPickerAdapter(new ArrayList<>(), dataType, this);
        } else if (dataType == DATA_CLUB_TYPE){
            DataManager.getInstance().getClubs(this, this, getIntent().getLongExtra(PICKER_DIVISION_ID, 0));
            mAdapter = new RegisterPickerAdapter(new ArrayList<>(), dataType, this);
        } else if (dataType == DATA_SIMPLE_TYPE){
            String[] genders = getIntent().getStringArrayExtra(PICKER_DATA_KEY);
            mAdapter = new RegisterPickerAdapter(Arrays.asList(genders), dataType, this);
        }
        if (layoutType == LINEAR_LAYOUT_TYPE) {
            layoutManager = new LinearLayoutManager(this);
        } else {
            layoutManager = new GridLayoutManager(this, SPAN_COUNT);
        }
        mList.setLayoutManager(layoutManager);
        mList.setItemAnimator(new DefaultItemAnimator());
        mList.setAdapter(mAdapter);

        if (dataType != DATA_SIMPLE_TYPE && dataType != DATA_TEAM_COLOURS_TYPE) {
            mList.setVisibility(View.GONE);
            mLoadingView.setVisibility(View.VISIBLE);
        }
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
    public void onAssociationSelected(FootballAssociation association) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(PICKER_SELECTED_ITEM_KEY, association.getName());
        resultIntent.putExtra(PICKER_SELECTED_ITEM_ASSOCIATION_ID_KEY, association.getAssociationId());
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onDivisionSelected(Division division) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(PICKER_SELECTED_ITEM_KEY, division.getName());
        resultIntent.putExtra(PICKER_SELECTED_ITEM_DIVISIONSEAS_ID_KEY, division.getSeasonId());
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onTeamSelected(Team team) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(PICKER_SELECTED_ITEM_KEY, team.getClub());
        resultIntent.putExtra(PICKER_SELECTED_ITEM_TEAM_DIVISION_ID_KEY, team.getDivisionId());
        resultIntent.putExtra(PICKER_SELECTED_ITEM_TEAM_ID_KEY, team.getTeamId());
        resultIntent.putExtra(PICKER_SELECTED_ITEM_TEAM_DIVISION_NAME_KEY, team.getDivision());
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onItemSelected(String item) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(PICKER_SELECTED_ITEM_KEY, item);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onListReady(List<?> list) {
        mAdapter.setData(list);
        mList.setVisibility(View.VISIBLE);
        mLoadingView.setVisibility(View.GONE);
    }

    @Override
    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        mLoadingView.setVisibility(View.GONE);
    }
}
