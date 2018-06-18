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
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.specifix.pureleagues.R;
import com.specifix.pureleagues.adapter.ResultsAdapter;
import com.specifix.pureleagues.model.Result;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResultsWeekFragment extends Fragment {
    private static final String CURRENT_WEEK = "current_week";

    @BindView(R.id.results_recycler_view)
    RecyclerView mListView;
//    @BindView(R.id.spacer)
//    View mSpacer;
//    @BindView(R.id.results_footer)
//    View mFooter;
//    @BindView(R.id.results_linear_layout)
//    LinearLayout mLayout;

    private ResultsAdapter mAdapter;
    private List<Result> mResults;
    ResultsAdapter.ResultAdapterListener mListener;

    public static ResultsWeekFragment newInstance(List<Result> results, ResultsAdapter.ResultAdapterListener listener) {
        ResultsWeekFragment fragment = new ResultsWeekFragment();
        fragment.mResults = results;
        fragment.mListener = listener;

        Bundle args = new Bundle();
//        args.putInt(CURRENT_WEEK, week);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
//        mCurrentWeek = bundle.getInt(CURRENT_WEEK);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.week_results_fragment, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mListView.setLayoutManager(layoutManager);

        mAdapter = new ResultsAdapter(getContext(), mResults, mListener);
        mListView.setAdapter(mAdapter);

//        mLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            public void onGlobalLayout() {
//                mLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//
//                int newSpace = mLayout.getHeight() - mFooter.getHeight() - mListView.getHeight();
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
