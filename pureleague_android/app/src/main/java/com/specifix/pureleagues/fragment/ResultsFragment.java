package com.specifix.pureleagues.fragment;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.specifix.pureleagues.R;
import com.specifix.pureleagues.adapter.ResultsAdapter;
import com.specifix.pureleagues.adapter.ResultsWeekAdapter;
import com.specifix.pureleagues.api.DataManager;
import com.specifix.pureleagues.api.UserManager;
import com.specifix.pureleagues.model.Result;
import com.specifix.pureleagues.model.Team;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.grantland.widget.AutofitTextView;

import static com.specifix.pureleagues.fragment.MessagesFragment.EVENT_TYPE_RESULTS;

public class ResultsFragment extends Fragment
        implements ResultsAdapter.ResultAdapterListener {
    @BindView(R.id.results_title_text_view)
    TextView mTitle;
    //    @BindView(R.id.results_recycler_view)
//    RecyclerView mListView;
    @BindView(R.id.results_view_pager)
    ViewPager mViewPager;
    @BindView(R.id.results_no_data_text_view)
    AutofitTextView mNoDataTextView;
    @BindView(R.id.results_footer)
    LinearLayout mFooter;
    //    private ResultsAdapter mAdapter;
    private ResultsWeekAdapter mWeekAdapter;
    private DashboardFragment.DashboardFragmentListener mCallback;
    private Typeface gothamBold;

    public static ResultsFragment newInstance() {
        ResultsFragment fragment = new ResultsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (DashboardFragment.DashboardFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement DashboardFragmentListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gothamBold = Typeface.createFromAsset(getContext().getAssets(), "GothamBold.otf");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_results, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTitle.setTypeface(gothamBold);
        mNoDataTextView.setTypeface(gothamBold);


        mWeekAdapter = new ResultsWeekAdapter(getChildFragmentManager(), DataManager.getInstance().getResults(), this);
        mViewPager.setAdapter(mWeekAdapter);
//        mListView.setHasFixedSize(true);
//
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
//        mListView.setLayoutManager(layoutManager);
//
//        mAdapter = new ResultsAdapter(getContext(), DataManager.getInstance().getResults(), this);
//
//        //mAdapter = new ResultsAdapter(list, getContext());
//        mListView.setAdapter(mAdapter);

        if (DataManager.getInstance().getResults().size() == 0) {
            /*long teamId = UserManager.getInstance().getCurrentTeamId();
            String teamName = "";
            for (Team team : DataManager.getInstance().getUserDivisionsTeams()) {
                if (team.getTeamId() == teamId) {
                    teamName = team.getClub();
                    break;
                }
            }*/
//            mListView.setVisibility(View.GONE);
            mViewPager.setVisibility(View.GONE);
            //mNoDataTextView.setText(String.format(getString(R.string.no_results_available), teamName));
            mNoDataTextView.setVisibility(View.VISIBLE);
            mFooter.setVisibility(View.VISIBLE);
        } else {
//            mListView.setVisibility(View.VISIBLE);
            mViewPager.setVisibility(View.VISIBLE);
            mNoDataTextView.setVisibility(View.GONE);
            //mFooter.setVisibility(View.VISIBLE);
        }
//        if (DataManager.getInstance().getResults().size() < 7) {
//            mFooter.setVisibility(View.VISIBLE);
//        }
    }

    @OnClick(R.id.results_arrow_left)
    public void onPrevImageClick() {
        if (mViewPager.getCurrentItem() != 0) {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, true);
        }
    }

    @OnClick(R.id.results_arrow_right)
    public void onNextImageClick() {
        if (mViewPager.getCurrentItem() != mWeekAdapter.getCount() - 1) {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
        }
    }

    @Override
    public void onMessagesClick(Result result) {
        mCallback.onAllMessagesClick(EVENT_TYPE_RESULTS, result);
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
