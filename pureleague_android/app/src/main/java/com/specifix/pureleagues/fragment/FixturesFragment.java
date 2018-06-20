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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.specifix.pureleagues.R;
import com.specifix.pureleagues.adapter.FixturesAdapter;
import com.specifix.pureleagues.adapter.FixturesWeekAdapter;
import com.specifix.pureleagues.api.DataManager;
import com.specifix.pureleagues.api.UserManager;
import com.specifix.pureleagues.model.Fixture;
import com.specifix.pureleagues.model.Team;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.grantland.widget.AutofitTextView;

import static com.specifix.pureleagues.fragment.MessagesFragment.EVENT_TYPE_FIXTURES;

public class FixturesFragment extends Fragment implements FixturesAdapter.FixturesAdapterListener {
    @BindView(R.id.fixtures_title_text_view)
    TextView mTitle;
//    @BindView(R.id.fixtures_recycler_view)
//    RecyclerView mListView;
    @BindView(R.id.fixtures_view_pager)
    ViewPager mViewPager;
    @BindView(R.id.fixtures_no_data_text_view)
    AutofitTextView mNoDataTextView;
    @BindView(R.id.fixtures_footer)
    LinearLayout mFooter;
//    private FixturesAdapter mAdapter;
    private FixturesWeekAdapter mWeekAdapter;
    private DashboardFragment.DashboardFragmentListener mCallback;
    private Typeface gothamBold;

    public static FixturesFragment newInstance() {
        FixturesFragment fragment = new FixturesFragment();
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

        View view = inflater.inflate(R.layout.fragment_fixtures, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTitle.setTypeface(gothamBold);
        mNoDataTextView.setTypeface(gothamBold);


        mWeekAdapter = new FixturesWeekAdapter(getChildFragmentManager(), DataManager.getInstance().getFixtures(), this);
        mViewPager.setAdapter(mWeekAdapter);
//        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            int oldPosition = -1;
//
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                if (oldPosition != position) {
//                    oldPosition = position;
//                    int count = mWeekAdapter.getSublistCount(position);
////                    if (count < 7) {
////                        mFooter.setVisibility(View.VISIBLE);
////                    } else {
////                        mFooter.setVisibility(View.GONE);
////                    }
//                    Log.d("SCROLL", "onPageScrolled " + position);
//                }
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//            }
//        });

//        mListView.setHasFixedSize(true);
//
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
//        mListView.setLayoutManager(layoutManager);
//
//        mAdapter = new FixturesAdapter(getContext(), DataManager.getInstance().getFixtures(), this);
//        mListView.setAdapter(mAdapter);

        if (DataManager.getInstance().getFixtures().size() == 0) {
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
            //mNoDataTextView.setText(String.format(getString(R.string.no_fixtures_available), teamName));
            mNoDataTextView.setVisibility(View.VISIBLE);
            mFooter.setVisibility(View.VISIBLE);
        } else {
//            mListView.setVisibility(View.VISIBLE);
            mViewPager.setVisibility(View.VISIBLE);
            mNoDataTextView.setVisibility(View.GONE);
            mFooter.setVisibility(View.GONE);
        }
//        if (DataManager.getInstance().getFixtures().size() < 7) {
//            mFooter.setVisibility(View.VISIBLE);
//        }
    }

    @OnClick(R.id.fixtures_arrow_left)
    public void onPrevImageClick() {
        if (mViewPager.getCurrentItem() != 0) {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, true);
        }
    }

    @OnClick(R.id.fixtures_arrow_right)
    public void onNextImageClick() {
        if (mViewPager.getCurrentItem() != mWeekAdapter.getCount() - 1) {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
        }
    }

    @Override
    public void onMessagesClick(Fixture fixture) {
        mCallback.onAllMessagesClick(EVENT_TYPE_FIXTURES, fixture);
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
