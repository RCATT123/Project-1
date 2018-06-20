package com.specifix.pureleagues.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.parse.ParseAnonymousUtils;
//import com.parse.ParseUser;
import com.specifix.pureleagues.R;
import com.specifix.pureleagues.api.DataManager;
import com.specifix.pureleagues.api.UserManager;
import com.specifix.pureleagues.fragment.AboutPureLeaguesFragment;
import com.specifix.pureleagues.fragment.CalendarFragment;
import com.specifix.pureleagues.fragment.ContactUsFragment;
import com.specifix.pureleagues.fragment.DashboardFragment;
import com.specifix.pureleagues.fragment.EditProfileFragment;
import com.specifix.pureleagues.fragment.EditTeamFragment;
import com.specifix.pureleagues.fragment.FixturesFragment;
import com.specifix.pureleagues.fragment.GalleryFragment;
import com.specifix.pureleagues.fragment.MessagesFragment;
import com.specifix.pureleagues.fragment.PrivacyFragment;
import com.specifix.pureleagues.fragment.ResultsFragment;
import com.specifix.pureleagues.fragment.StatisticsFragment;
import com.specifix.pureleagues.fragment.TablesFragment;
import com.specifix.pureleagues.fragment.TermsFragment;
import com.specifix.pureleagues.manager.PrefsManager;
import com.specifix.pureleagues.model.Fixture;
import com.specifix.pureleagues.model.Result;
import com.specifix.pureleagues.model.Team;
import com.specifix.pureleagues.model.User;

import java.util.List;
import java.util.Stack;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.specifix.pureleagues.activity.RegisterContinueActivity.ADD_TEAM_KEY;
import static com.specifix.pureleagues.fragment.EditProfileFragment.TEAM_ID;
import static com.specifix.pureleagues.fragment.MessagesFragment.EVENT_TYPE_FIXTURES;

public class MainActivity extends AppCompatActivity
        implements EditProfileFragment.EditProfileListener, EditTeamFragment.EditTeamListener,
        DashboardFragment.DashboardFragmentListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String TAB_DASHBOARD = "dashboard_fragment";
    private static final String TAB_FIXTURES = "fixtures_fragment";
    private static final String TAB_RESULTS = "results_fragment";
    private static final String TAB_TABLES = "tables_fragment";
    private static final String TAB_EDIT_PROFILE = "edit_profile_fragment";
    private static final String TAB_ABOUT = "about_pure_leagues_fragment";
    private static final String TAB_TERMS = "terms_fragment";
    private static final String TAB_PRIVACY = "privacy_fragment";
    private static final String TAB_CONTACT_US = "contact_us_fragment";
    private static final String TAB_EDIT_TEAM = "edit_team_fragment";
    private static final String TAB_GALLERY = "gallery_fragment";
    private static final String TAB_CALENDAR = "calendar_fragment";
    private static final String TAB_STATISTICS = "statistics_fragment";
    private static final String TAB_ALL_MESSAGES = "all_messages_fragment";
    private static final String BACK_STACK_ROOT_TAG = "back_stack_root";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_team_color)
    ImageView mTeamColor;
    @BindView(R.id.main_tabs_layout)
    LinearLayout mTabsLayout;
    @BindView(R.id.logo_image_view)
    ImageView mTabDashboard;
    @BindView(R.id.tab_fixtures)
    TextView mTabFixtures;
    @BindView(R.id.tab_results)
    TextView mTabResults;
    @BindView(R.id.tab_tables)
    TextView mTabTables;
    @BindView(R.id.tab_stats)
    TextView mTabStats;
    @BindView(R.id.tab_gallery)
    TextView mTabGallery;
    @BindView(R.id.tab_calendar)
    TextView mTabCalendar;
    @BindView(R.id.main_container)
    FrameLayout mMainContainer;
    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;
    @BindView(R.id.drawer)
    DrawerLayout mDrawerLayout;
    private User mUserProfile;
    private Stack<Fragment> mBackStack;
    private BroadcastReceiver mConnectivityReceiver;
    private ProgressDialog mProgressDialog;

    private static boolean mInRun = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        //mUserProfile = UserManager.getInstance().getCurrentUser();
        mBackStack = new Stack<>();

        initToolbar();

        initDrawer();

        initTabs();

        registerConnectivityReceiver();

//        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
//        String instId = installation.getObjectId();
//        Map<String, String> params = new HashMap<>();
//        params.put("channelName", "ch_" + String.valueOf(UserManager.getInstance().getCurrentTeamId()));
//        params.put("installationId", instId);
//        params.put("message", "New some test data");
//        params.put("title", "Pure league title");
//        ParseCloud.callFunctionInBackground("push", params, new FunctionCallback<Object>() {
//            @Override
//            public void done(Object object, ParseException e) {
//
//            }
//        });

        /*ParsePush.subscribeInBackground("TEST", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                Log.d("Wraith", e + " ONDONE");
                ParseInstallation installation = ParseInstallation.getCurrentInstallation();
                installation.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        Log.d("Wraith", e + " Saves");
                    }
                });
            }
        });*/
        /*
        Map<String, String> params = new HashMap<>();
        params.put("channelName", "testChannel");
        ParseCloud.callFunctionInBackground("push", params);*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        mInRun = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mInRun = false;
    }

    public static boolean isActive() {
        return mInRun;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mConnectivityReceiver != null) {
            unregisterReceiver(mConnectivityReceiver);
        }
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private void registerConnectivityReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

        mConnectivityReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                if (activeNetwork != null) {
                    /*
                    Get list of messges by using datamanager class and get it from broadcast receiver
                     */
                    /* DataManager.getInstance().downloadMessages(MainActivity.this, new DataManager.DataManagerUpdateListener() {
                        @Override
                        public void onMessageDownloaded(String eventType, int i) {
                           //// TODO: 22.05.2017
                        }
                    }); */
                }
            }
        };
        registerReceiver(mConnectivityReceiver, filter);
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        mTeamColor.setVisibility(View.VISIBLE);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        /* List<Team> teams = UserManager.getInstance().getCurrentUser().getTeams();
        long teamId = sp.getLong(TEAM_ID, 0);
        if (teamId == 0 && teams.size() == 1) {
            sp.edit().putLong(TEAM_ID, teams.get(0).getTeamId()).apply();
            UserManager.getInstance().setCurrentTeamId(teams.get(0).getTeamId());
        }
        for (Team team : teams) {
            if (team.getTeamId() == teamId) {
                mTeamColor.setImageResource(
                        UserManager.getInstance().getTeamColorRes(team.getColor()));
            }
        } */
    }

    private void initDrawer() {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                mToolbar,
                R.string.openDrawer,
                R.string.closeDrawer){

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                hideKeyboard();
            }
        };
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        /*if (ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
            mNavigationView.getMenu().getItem(1).setVisible(true);
            mNavigationView.getMenu().getItem(2).setVisible(false);
        } else {
            mNavigationView.getMenu().getItem(1).setVisible(false);
            mNavigationView.getMenu().getItem(2).setVisible(true);
        }*/

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawers();
                unselectAllTabs();

                switch (item.getItemId()) {
                    case R.id.nav_item_dashboard: {
                        clearBackStack();
                        replaceFragment(TAB_DASHBOARD, true);
                        break;
                    }
                    case R.id.nav_item_register: {
                        Intent addTeamIntent = new Intent(MainActivity.this, RegisterActivity.class);
                        startActivity(addTeamIntent);
                        break;
                    }
                    case R.id.nav_item_edit_profile: {
                        replaceFragment(TAB_EDIT_PROFILE, true);
                        break;
                    }
                    case R.id.nav_item_share: {
                        shareContent();
                        break;
                    }
                    case R.id.nav_item_about: {
                        replaceFragment(TAB_ABOUT, true);
                        break;
                    }
                    case R.id.nav_item_terms: {
                        replaceFragment(TAB_TERMS, true);
                        break;
                    }
                    case R.id.nav_item_privacy: {
                        replaceFragment(TAB_PRIVACY, true);
                        break;
                    }
                    case R.id.nav_item_contact_us: {
                        replaceFragment(TAB_CONTACT_US, true);
                        break;
                    }
                    case R.id.nav_item_logout: {
                        mProgressDialog = new ProgressDialog(MainActivity.this);
                        mProgressDialog.setMessage(getString(R.string.logout_message));
                        mProgressDialog.setCancelable(false);
                        mProgressDialog.show();

                        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                        sp.edit().putLong(TEAM_ID, -1).apply();
                        UserManager.getInstance().logoutUser(new UserManager.LogoutListener() {
                            @Override
                            public void onLogout() {
                                if (mProgressDialog != null && mProgressDialog.isShowing()) {
                                    mProgressDialog.dismiss();
                                }
                                new PrefsManager().clearPrefs();
                                Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
                        break;
                    }
                }
                return false;
            }
        });
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

    private void initTabs() {
        Typeface gothamBold = Typeface.createFromAsset(getAssets(), "GothamBold.otf");
        mTabFixtures.setTypeface(gothamBold);
        mTabResults.setTypeface(gothamBold);
        mTabTables.setTypeface(gothamBold);
        mTabStats.setTypeface(gothamBold);
        mTabGallery.setTypeface(gothamBold);
        mTabCalendar.setTypeface(gothamBold);

        Fragment fragment = DashboardFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_container, fragment, TAB_DASHBOARD);
        transaction.commit();
        addToBackStack(fragment);
    }

    private void addToBackStack(Fragment fragment) {
        mBackStack.push(fragment);
    }

    private void clearBackStack() {
        mBackStack.clear();
    }

    private void addTabToBackStack(Fragment fragment) {
        if (mBackStack.size() <= 1) {
            mBackStack.push(fragment);
        } else {
            mBackStack.pop();
            mBackStack.push(fragment);
        }
    }

    private void replaceFragment(String tab, boolean addToBackStack) {
        if (tab.equals(TAB_EDIT_PROFILE) || tab.equals(TAB_EDIT_TEAM)) {
            mTabsLayout.setVisibility(View.GONE);
        } else {
            mTabsLayout.setVisibility(View.VISIBLE);
        }
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tab);
        if (fragment != null && fragment.isVisible()){
            return;
        }
        if (fragment == null) {
            switch (tab) {
                case TAB_DASHBOARD: {
                    fragment = DashboardFragment.newInstance();
                    break;
                }
                case TAB_FIXTURES: {
                    fragment = FixturesFragment.newInstance();
                    addTabToBackStack(fragment);
                    break;
                }
                case TAB_RESULTS: {
                    fragment = ResultsFragment.newInstance();
                    addTabToBackStack(fragment);
                    break;
                }
                case TAB_TABLES: {
                    fragment = TablesFragment.newInstance();
                    addTabToBackStack(fragment);
                    break;
                }
                case TAB_EDIT_PROFILE: {
                    fragment = EditProfileFragment.newInstance();
                    break;
                }
                case TAB_ABOUT: {
                    fragment = AboutPureLeaguesFragment.newInstance();
                    break;
                }
                case TAB_TERMS: {
                    fragment = TermsFragment.newInstance();
                    break;
                }
                case TAB_PRIVACY: {
                    fragment = PrivacyFragment.newInstance();
                    break;
                }
                case TAB_CONTACT_US: {
                    fragment = ContactUsFragment.newInstance();
                    break;
                }
                case TAB_GALLERY: {
                    fragment = GalleryFragment.newInstance();
                    addTabToBackStack(fragment);
                    break;
                }
                case TAB_CALENDAR: {
                    fragment = CalendarFragment.newInstance();
                    addTabToBackStack(fragment);
                    break;
                }
                case TAB_STATISTICS: {
                    fragment = StatisticsFragment.newInstance();
                    addTabToBackStack(fragment);
                    break;
                }
            }
        }
        if (addToBackStack) {
            addToBackStack(fragment);
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.main_container, fragment, tab);
        transaction.commit();
    }

    private void editTeam(Team team) {
        Fragment fragment = EditTeamFragment.newInstance(team);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.main_container, fragment);
        transaction.commit();

        addToBackStack(fragment);
    }

    private void viewAllMessages(String eventType, int position) {
        unselectAllTabs();
        Fragment fragment = MessagesFragment.newInstance(eventType, position);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.main_container, fragment);
        transaction.commit();

        addToBackStack(fragment);
    }

    private void setTabSelected(View view) {
        unselectAllTabs();

        switch (view.getId()) {
            case R.id.logo_image_view: {
                clearBackStack();
                replaceFragment(TAB_DASHBOARD, true);
                break;
            }
            case R.id.tab_fixtures: {
                mTabFixtures.setSelected(true);
                break;
            }
            case R.id.tab_results: {
                mTabResults.setSelected(true);
                break;
            }
            case R.id.tab_tables: {
                mTabTables.setSelected(true);
                break;
            }
            case R.id.tab_stats: {
                mTabStats.setSelected(true);
                break;
            }
            case R.id.tab_gallery: {
                mTabGallery.setSelected(true);
                break;
            }
            case R.id.tab_calendar: {
                mTabCalendar.setSelected(true);
                break;
            }
        }
    }

    private void shareContent() {
        Uri imageUri = Uri.parse("android.resource://" + getPackageName()
                + "/drawable/" + "logo");
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.branch_share_link));
        sendIntent.setType("image/*");
        sendIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
    }

    private void unselectAllTabs() {
        mTabFixtures.setSelected(false);
        mTabResults.setSelected(false);
        mTabTables.setSelected(false);
        mTabStats.setSelected(false);
        mTabGallery.setSelected(false);
        mTabCalendar.setSelected(false);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
        } if (mBackStack.size() > 0) {
            if (mBackStack.peek() instanceof DashboardFragment) {
                super.onBackPressed();
            } else {
                if (mBackStack.pop() instanceof EditProfileFragment) {
                    mTabsLayout.setVisibility(View.VISIBLE);
                }
                if (mBackStack.size() == 1) {
                    unselectAllTabs();
                }
                /*if (mBackStack.size() == 0) {
                    super.onBackPressed();
                    return;
                }*/
                if (mBackStack.size() == 0) {
                    super.onBackPressed();
                } else {
                    if(!isFinishing()) {
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction
                                .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right)
                                .replace(R.id.main_container, mBackStack.peek())
                                .commitAllowingStateLoss();
                    }
                }
            }
        } else {
            super.onBackPressed();
        }
    }

    @OnClick(R.id.logo_image_view)
    public void onDashboardClick() {
        setTabSelected(mTabDashboard);
    }

    @OnClick(R.id.tab_fixtures)
    public void onFixturesTabClick() {
        setTabSelected(mTabFixtures);
        replaceFragment(TAB_FIXTURES, false);
    }

    @OnClick(R.id.tab_results)
    public void onResultsTabClick() {
        setTabSelected(mTabResults);
        replaceFragment(TAB_RESULTS, false);
    }

    @OnClick(R.id.tab_tables)
    public void onTablesTabClick() {
        setTabSelected(mTabTables);
        replaceFragment(TAB_TABLES, false);
    }

    @OnClick(R.id.tab_stats)
    public void onStatsTabClick() {
        /*if (ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
            Toast.makeText(this, R.string.register_error_message, Toast.LENGTH_SHORT).show();
            return;
        }*/
        setTabSelected(mTabStats);
        replaceFragment(TAB_STATISTICS, false);
    }

    @OnClick(R.id.tab_gallery)
    public void onGalleryTabClick() {
        /*if (ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
            Toast.makeText(this, R.string.register_error_message, Toast.LENGTH_SHORT).show();
            return;
        }*/
        /*if (UserManager.getInstance().getUserAge() < UserManager.MIN_ALLOWED_AGE) {
            Toast.makeText(this, R.string.min_age_not_reached_error_message, Toast.LENGTH_SHORT).show();
            return;
        }*/
        setTabSelected(mTabGallery);
        replaceFragment(TAB_GALLERY, false);
    }

    @OnClick(R.id.tab_calendar)
    public void onCalendarTabClick() {
        /*if (ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
            Toast.makeText(this, R.string.register_error_message, Toast.LENGTH_SHORT).show();
            return;
        }*/
        setTabSelected(mTabCalendar);
        replaceFragment(TAB_CALENDAR, false);
    }


    @OnClick(R.id.toolbar_team_color)
    public void onTeamColoursClick() {
        /*if (ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
            Toast.makeText(this, R.string.register_error_message, Toast.LENGTH_SHORT).show();
            return;
        }*/
        replaceFragment(TAB_EDIT_PROFILE, true);
    }

    @Override
    public void onAddTeamClick() {
        Intent intent = new Intent(MainActivity.this, RegisterContinueActivity.class);
        intent.putExtra(ADD_TEAM_KEY, true);
        startActivity(intent);
    }

    @Override
    public void onEditTeamClick(Team team) {
        editTeam(team);
    }

    @Override
    public void onTeamColoursChanged(String color, boolean backToDashboard) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        long teamId = sp.getLong(TEAM_ID, 0);
        /*List<Team> teams = UserManager.getInstance().getCurrentUser().getTeams();
        UserManager.getInstance().setCurrentTeamId(teamId);
        for (Team team : teams) {
            if (team.getTeamId() == teamId) {
                mTeamColor.setImageResource(
                        UserManager.getInstance().getTeamColorRes(color));
            }
        }
        if (backToDashboard) {
            onDashboardClick();
        }*/
    }

    @Override
    public void onTeamDeleted() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        /*List<Team> teams = UserManager.getInstance().getCurrentUser().getTeams();
        if (teams != null && teams.size() > 0) {
            sp.edit().putLong(TEAM_ID, teams.get(0).getTeamId()).apply();
            UserManager.getInstance().setCurrentTeamId(teams.get(0).getTeamId());
            DataManager.getInstance().downloadEvents(teams.get(0), this, new DataManager.DataManagerUpdateEvent() {
                @Override
                public void onFixturesReady() {
                    onBackPressed();
                }

                @Override
                public void onResultsReady() {

                }
            });
        } else {
            sp.edit().putLong(TEAM_ID, 0).apply();
            UserManager.getInstance().setCurrentTeamId(0);

            onBackPressed();
            Intent addTeamIntent = new Intent(MainActivity.this, RegisterContinueActivity.class);
            addTeamIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            addTeamIntent.putExtra(ADD_TEAM_KEY, true);
            startActivity(addTeamIntent);
        }*/
    }

    @Override
    public void onAllMessagesClick(String eventType, Object object) {
        /*if (ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
            Toast.makeText(this, R.string.register_error_message, Toast.LENGTH_SHORT).show();
            return;
        }*/
        /*if (UserManager.getInstance().getUserAge() < UserManager.MIN_ALLOWED_AGE) {
            Toast.makeText(this, R.string.min_age_not_reached_error_message, Toast.LENGTH_SHORT).show();
            return;
        }*/
        int index = -1;
        if (eventType.equals(EVENT_TYPE_FIXTURES)) {
            List<Fixture> fixtures = DataManager.getInstance().getFixtures();
            for (int i = 0; i < fixtures.size(); i++) {
                if (fixtures.get(i).getLeague_id().equals(((Fixture)object).getLeague_id())) {
                    index = i;
                    break;
                }
            }
        } else {
            //index = DataManager.getInstance().getResults().indexOf(object);

            List<Result> results = DataManager.getInstance().getResults();
            for (int i = 0; i < results.size(); i++) {
                if (results.get(i).getId().equals(((Result)object).getId())) {
                    index = i;
                    break;
                }
            }
        }
        viewAllMessages(eventType, index);
    }
}
