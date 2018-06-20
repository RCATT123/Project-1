package com.specifix.pureleagues.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.specifix.pureleagues.R;
import com.specifix.pureleagues.adapter.StatisticsAdapter;
import com.specifix.pureleagues.adapter.StatisticsTeamAdapter;
import com.specifix.pureleagues.api.DataManager;
import com.specifix.pureleagues.api.UserManager;
import com.specifix.pureleagues.model.Scorer;
import com.specifix.pureleagues.model.Team;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;

public class StatisticsFragment extends Fragment {
    @BindView(R.id.statistics_recycler)
    RecyclerView mListView;
    @BindView(R.id.statistics_spinner)
    Spinner mSelectClub;
    @BindView(R.id.statistics_progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.stats_no_data_text_view)
    TextView mNoDataTextView;
    @BindView(R.id.stats_footer)
    LinearLayout mFooter;

    private StatisticsAdapter mAdapter;
    List<Team> mTeams;
    int mCurrentTeam = -1;
   // private List<Scorer> mScorers;

    public static StatisticsFragment newInstance() {
        StatisticsFragment fragment = new StatisticsFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListView.setHasFixedSize(true);
        mListView.setLayoutManager(new LinearLayoutManager(getContext()));

        //long currentTeamId = UserManager.getInstance().getCurrentTeamId();

        //mTeams = UserManager.getInstance().getCurrentUser().getTeams();

        if (mTeams != null && mTeams.size() > 0) {

            for (int i = 0; i < mTeams.size(); i++) {
                /*if (mTeams.get(i).getTeamId() == currentTeamId) {
                    mCurrentTeam = i;
                    break;
                }*/
            }

            List<String> spinnerList = new ArrayList<String>();
            spinnerList.add(mTeams.get(mCurrentTeam).getDivision());
            spinnerList.add(mTeams.get(mCurrentTeam).getClub());
            /*for (Team team : mTeams) {
                spinnerList.add(team.getClub());
            }*/
            StatisticsTeamAdapter adapter = new StatisticsTeamAdapter(getContext(), spinnerList);
            mSelectClub.setAdapter(adapter);
        }

        //List<Team> teams = UserManager.getInstance().getCurrentUser().getTeams();
        if (mTeams != null && mTeams.size() > 0) {
            getScorers(mTeams.get(mCurrentTeam).getDivisionId(), -1);
        } else {
            mAdapter = new StatisticsAdapter(new ArrayList<Scorer>(), getContext());
            mListView.setAdapter(mAdapter);
        }
    }

    @OnItemSelected(R.id.statistics_spinner)
    public void onTeamSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
            getScorers(mTeams.get(mCurrentTeam).getDivisionId(), -1);
        } else {
           //getScorers(mTeams.get(position - 1).getDivisionId(), mTeams.get(position - 1).getTeamId());
           getScorers(mTeams.get(mCurrentTeam).getDivisionId(), mTeams.get(mCurrentTeam).getTeamId());
        }
        mListView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        mNoDataTextView.setVisibility(View.GONE);
    }

    private void getScorers(final long divisionId, final long teamId) {
        //DataManager.getInstance().getScorers();
       // List<Scorer> scorers = DataManager.getInstance().getScorers();
        //if (scorers == null) {

        DataManager.getInstance().getClubs(getContext(), new DataManager.DataManagerPickListener() {
            @Override
            public void onListReady(List<?> list) {
                DataManager.getInstance().downloadScorers(getContext(), (List<Team>) list, divisionId, teamId, new DataManager.DataManagerPickListener() {
                    @Override
                    public void onListReady(List<?> list) {
                        mAdapter = new StatisticsAdapter((List<Scorer>)list, getContext());
                        mListView.setAdapter(mAdapter);
                        mFooter.setVisibility(View.VISIBLE);

                        if (list.size() == 1) {
                            mNoDataTextView.setVisibility(View.VISIBLE);
                        } else {
                            mListView.setVisibility(View.VISIBLE);
                            mNoDataTextView.setVisibility(View.GONE);
                        }
                        mProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                        mListView.setVisibility(View.VISIBLE);
                        mNoDataTextView.setVisibility(View.GONE);
                        mFooter.setVisibility(View.GONE);
                        mProgressBar.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onError(String message) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                mListView.setVisibility(View.VISIBLE);
                mNoDataTextView.setVisibility(View.GONE);
                mFooter.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.GONE);
            }
        }, divisionId);
/*
        } else {
            mAdapter = new StatisticsAdapter(scorers, getContext());
            mListView.setAdapter(mAdapter);
        }*/
    }

    /*List<Scorer> scorers = DataManager.getInstance().getScorers();
        if (scorers == null) {
            DataManager.getInstance().getClubs(new DataManager.DataManagerPickListener() {
                @Override
                public void onListReady(List<?> list) {
                    DataManager.getInstance().downloadScorers((List<Team>) list, new DataManager.DataManagerPickListener() {
                        @Override
                        public void onListReady(List<?> list) {
                            mAdapter = new StatisticsAdapter((List<Scorer>)list, getContext());
                            mListView.setAdapter(mAdapter);
                        }
                    });
                }
            }, mTeams.get(0).getDivisionId());*/

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
