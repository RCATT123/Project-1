package com.specifix.pureleagues.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.specifix.pureleagues.R;
import com.specifix.pureleagues.adapter.TablesAdapter;
import com.specifix.pureleagues.api.DataManager;
import com.specifix.pureleagues.api.UserManager;
import com.specifix.pureleagues.model.ClubStats;
import com.specifix.pureleagues.model.Team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.specifix.pureleagues.fragment.EditProfileFragment.TEAM_ID;

public class TablesFragment extends Fragment {
    @BindView(R.id.tables_title_text_view)
    TextView mTitle;
    @BindView(R.id.tables_recycler_view)
    RecyclerView mListView;
    @BindView(R.id.tables_progress_bar)
    ProgressBar mProgressBar;
//    @BindView(R.id.tables_footer)
//    LinearLayout mFooter;
//    @BindView(R.id.tables_scroll_view)
//    ScrollView mScrollView;
//    @BindView(R.id.spacer)
//    View mSpacer;

    private TablesAdapter mAdapter;
    private Typeface gothamBold;

    public static TablesFragment newInstance() {
        TablesFragment fragment = new TablesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gothamBold = Typeface.createFromAsset(getContext().getAssets(), "GothamBold.otf");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tables, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTitle.setTypeface(gothamBold);

        mListView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mListView.setLayoutManager(layoutManager);

        /*List<ClubStats> list = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            list.add(new ClubStats(
                    i,
                    "Club " + i,
                    String.valueOf(new Random().nextInt(100)),
                    String.valueOf(new Random().nextInt(100)),
                    String.valueOf(new Random().nextInt(100)),
                    String.valueOf(new Random().nextInt(100)),
                    String.valueOf(new Random().nextInt(100)),
                    String.valueOf(new Random().nextInt(100)),
                    String.valueOf(new Random().nextInt(100)),
                    String.valueOf(new Random().nextInt(100))));
        }*/
        List<Team> userTeams = UserManager.getInstance().getCurrentUser().getTeams();
        if (userTeams == null || userTeams.size() == 0) {
            mAdapter = new TablesAdapter(new ArrayList<ClubStats>(), getContext());
            mListView.setAdapter(mAdapter);
            return;
        }

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        List<Team> teams = UserManager.getInstance().getCurrentUser().getTeams();
        Team currentTeam = null;
        for (Team team : teams) {
            if (team.getTeamId() == sp.getLong(TEAM_ID, 0)) {
                currentTeam = team;
            }
        }

        mListView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        final Team finalCurrentTeam = currentTeam;
        if (currentTeam != null) {
            DataManager.getInstance().getClubs(getContext(), new DataManager.DataManagerPickListener() {
                @Override
                public void onListReady(List<?> list) {
                    DataManager.getInstance().downloadClubStats(finalCurrentTeam, getContext(), (List<Team>) list, new DataManager.DataManagerPickListener() {
                        @Override
                        public void onListReady(List<?> list) {
                            Collections.sort((List<ClubStats>) list, new Comparator<ClubStats>() {
                                @Override
                                public int compare(ClubStats o1, ClubStats o2) {
                                    if (o1.getPoints() < 0)
                                        return -1;
                                    if (o2.getPoints() < 0)
                                        return 1;
                                    if (o1.getPoints() > o2.getPoints())
                                        return -1;
                                    if (o1.getPoints() < o2.getPoints())
                                        return 1;
                                    return 0;
                                }
                            });
                            mAdapter = new TablesAdapter((List<ClubStats>) list, getContext());
                            mListView.setAdapter(mAdapter);
                            mListView.setVisibility(View.VISIBLE);
                            mProgressBar.setVisibility(View.GONE);
//                            if (list.size() < 15) {
//                                mFooter.setVisibility(View.VISIBLE);
//                            } else {
//                                mFooter.setVisibility(View.GONE);
//                            }
                        }

                        @Override
                        public void onError(String message) {
                            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                            mProgressBar.setVisibility(View.GONE);
                        }
                    });
                }

                @Override
                public void onError(String message) {
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    mProgressBar.setVisibility(View.GONE);
                }
            }, currentTeam.getDivisionId());
        } else {
            mAdapter = new TablesAdapter(new ArrayList<ClubStats>(), getContext());
            mListView.setAdapter(mAdapter);
            mListView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);
//            mFooter.setVisibility(View.VISIBLE);
        }

//        mScrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            public void onGlobalLayout() {
////                mScrollView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//
//                int newSpace = mScrollView.getHeight() - mFooter.getHeight() - mListView.getHeight();
//                newSpace = Math.max(newSpace, 0);
//                ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) mSpacer.getLayoutParams();
//                params.height = newSpace;
//                mSpacer.setLayoutParams(params);
//            }
//        });
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
                url = view.getContext().getString(R.string.facebook_url);
                break;
            }
            case R.id.footer_twitter_icon: {
                url = view.getContext().getString(R.string.twitter_url);
                break;
            }
            case R.id.footer_instagram_icon: {
                url = view.getContext().getString(R.string.instagram_url);
                break;
            }
            case R.id.footer_youtube_icon: {
                url = view.getContext().getString(R.string.youtube_url);
                break;
            }
            case R.id.footer_snapchat_icon: {
                url = view.getContext().getString(R.string.snapchat_url);
                break;
            }
        }

        try {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            view.getContext().startActivity(i);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }
}
