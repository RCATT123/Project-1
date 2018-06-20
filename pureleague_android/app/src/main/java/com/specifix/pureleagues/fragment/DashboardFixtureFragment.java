package com.specifix.pureleagues.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.specifix.pureleagues.R;
import com.specifix.pureleagues.model.Converter;
import com.specifix.pureleagues.model.Fixture;
import com.specifix.pureleagues.model.Result;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.grantland.widget.AutofitTextView;

public class DashboardFixtureFragment extends Fragment {
    private static final String EXTRA_FIXTURE = "extra_fixture";
    private static final String EXTRA_RESULT = "extra_result";

    @BindView(R.id.dashboard_fixture_club_1)
    AutofitTextView mFixtureClubOne;
    @BindView(R.id.dashboard_fixture_club_2)
    AutofitTextView mFixtureClubTwo;
    @BindView(R.id.dashboard_fixture_versus_label)
    TextView mVsLabel;
    @BindView(R.id.dashboard_fixture_address)
    AutofitTextView mFixtureAddress;
    @BindView(R.id.dashboard_fixture_date)
    TextView mFixtureDate;
    @BindView(R.id.dashboard_fixture_time)
    TextView mFixtureTime;
    @BindView(R.id.score)
    TextView score;
    @BindView(R.id.dashboard_fixture_address_point)
    ImageView mAddressPoint;
    @BindView(R.id.dashboard_fixture_datetime_layout)
    LinearLayout mDateTime;
    private Object mData;

    public static DashboardFixtureFragment newInstance(Fixture fixture) {
        DashboardFixtureFragment fragment = new DashboardFixtureFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_FIXTURE, fixture);
        fragment.setArguments(args);
        return fragment;
    }

    public static DashboardFixtureFragment newInstance(Result result) {
        DashboardFixtureFragment fragment = new DashboardFixtureFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_RESULT, result);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle.containsKey(EXTRA_FIXTURE)) {
            mData = bundle.getParcelable(EXTRA_FIXTURE);
        } else {
            mData = bundle.getParcelable(EXTRA_RESULT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dashboard_fixture_fragment, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Typeface gothamBold = Typeface.createFromAsset(getContext().getAssets(), "GothamBold.otf");
        Typeface gothamBook = Typeface.createFromAsset(getContext().getAssets(), "GothamBook.otf");
        mFixtureClubOne.setTypeface(gothamBold);
        mFixtureClubTwo.setTypeface(gothamBold);
        mVsLabel.setTypeface(gothamBold);
        mFixtureAddress.setTypeface(gothamBook);
        mFixtureDate.setTypeface(gothamBook);
        mFixtureTime.setTypeface(gothamBook);

        if (mData instanceof Fixture) {
            Fixture mFixture = (Fixture) mData;
            mFixtureClubOne.setText(String.valueOf(mFixture.getHome_name()));
            mFixtureClubTwo.setText(String.valueOf(mFixture.getAway_name()));
            mFixtureAddress.setText(mFixture.getlocation());
            mFixtureAddress.setVisibility(View.VISIBLE);
            mAddressPoint.setVisibility(View.VISIBLE);
            mFixtureDate.setText(mFixture.getFormatDate());
            mFixtureTime.setText(mFixture.getTime());
            score.setVisibility(View.GONE);
            mDateTime.setPadding(0, 0, 0, 0);

        } else {
            Result result = (Result) mData;
            mFixtureClubOne.setText(String.valueOf(result.getClubOneName()));
            mFixtureClubTwo.setText(String.valueOf(result.getClubTwoName()));
            mFixtureAddress.setVisibility(View.GONE);
            mAddressPoint.setVisibility(View.GONE);
            mFixtureDate.setText(result.getFormatDate());
            mFixtureTime.setText(result.getTime());
            score.setText(result.getScore());
            score.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mDateTime.getLayoutParams();
            params.setMargins(params.leftMargin, Converter.dpToPx(getContext(), -12), params.rightMargin, params.bottomMargin); // left, top, right, bottom
            mDateTime.setLayoutParams(params);
        }
    }
}
