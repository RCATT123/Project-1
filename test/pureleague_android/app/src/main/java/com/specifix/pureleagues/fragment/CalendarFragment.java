package com.specifix.pureleagues.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;
import com.specifix.pureleagues.R;
import com.specifix.pureleagues.api.DataManager;
import com.specifix.pureleagues.adapter.CustomCaldroidFragment;
import com.specifix.pureleagues.api.UserManager;
import com.specifix.pureleagues.model.Fixture;
import com.specifix.pureleagues.model.Result;
import com.specifix.pureleagues.util.DateFilter;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CalendarFragment extends Fragment {
    private static final String DASHBOARD_FRAGMENT = "dashboard_fragment";
    private static final String STATE_CALDROID = "CALDROID_SAVED_STATE";

    @BindView(R.id.calendar_scroll_view)
    ScrollView mScrollView;
    @BindView(R.id.calendar_container_legend)
    LinearLayout mCalendarLegend;

    private CaldroidFragment calendarFragment;
    private DashboardFragment dashboardFragment;

    public static CalendarFragment newInstance() {
        CalendarFragment fragment = new CalendarFragment();

        Bundle extras = new Bundle();
        fragment.setArguments(extras);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calendarFragment = new CustomCaldroidFragment();
        configureCalendarFragment(savedInstanceState);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.calendar_container, calendarFragment)
                .commit();

        dashboardFragment = (DashboardFragment) getActivity().getSupportFragmentManager().findFragmentByTag(DASHBOARD_FRAGMENT);
        if (dashboardFragment != null && dashboardFragment.isVisible()) {
            return;
        }

        dashboardFragment = DashboardFragment.newInstance(true);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.calendar_fragment_view, dashboardFragment);
        transaction.commit();
    }

    private void configureCalendarFragment(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            calendarFragment.restoreStatesFromKey(savedInstanceState,
                    STATE_CALDROID);
        } else {
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
            args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, false);

            calendarFragment.setArguments(args);
        }

        setCustomResourceForDates();

        calendarFragment.setCaldroidListener(listener);
    }

    private void setCustomResourceForDates() {
        if (calendarFragment != null) {
            Drawable resBg1 = ContextCompat.getDrawable(getContext(), R.drawable.item_calendar_orange);
            Drawable resBg2 = ContextCompat.getDrawable(getContext(), R.drawable.item_calendar_grey);

            long currentTeamId = UserManager.getInstance().getCurrentTeamId();
            Map<String, Object> extraData = new HashMap<>();
            List<Result> results = DataManager.getInstance().getResults();
            for (Result result : results) {
                if (result.getClubOneId() != currentTeamId && result.getClubTwoId() != currentTeamId)
                    continue;

                Date date = DateFilter.filterDate(result.getDate());
                if (date != null) {
                    calendarFragment.setBackgroundDrawableForDate(resBg1, date);
                    calendarFragment.setTextColorForDate(android.R.color.white, date);
                    extraData.put(date.toString(), result);
                }
            }
            List<Fixture> fixtures = DataManager.getInstance().getFixtures();
            for (Fixture fixture : fixtures) {
                if (fixture.getClubOneId() != currentTeamId && fixture.getClubTwoId() != currentTeamId)
                    continue;

                Date date = DateFilter.filterDate(fixture.getDate());
                if (date != null) {
                    calendarFragment.setBackgroundDrawableForDate(resBg2, date);
                    calendarFragment.setTextColorForDate(android.R.color.white, date);
                    extraData.put(date.toString(), fixture);
                }
            }

            calendarFragment.setExtraData(extraData);
        }
    }

    private CaldroidListener listener = new CaldroidListener() {
        @Override
        public void onSelectDate(Date date, View view) {
            if (calendarFragment != null) {
                Map<String, Object> extraData = calendarFragment.getExtraData();
                Object o = extraData.get(date.toString());

                if (o instanceof Result) {
                    Result result = (Result) o;
                    displayResult(result);
                } else if (o instanceof Fixture) {
                    Fixture fixture = (Fixture) o;
                    displayFixture(fixture);
                }
                if (o != null) {
                    mScrollView.smoothScrollTo(0, mCalendarLegend.getBottom());
                }
            }
        }
    };

    private void displayResult(Result result) {
        dashboardFragment.setFixture(result);
    }

    private void displayFixture(Fixture fixture) {
        dashboardFragment.setFixture(fixture);
    }
}
