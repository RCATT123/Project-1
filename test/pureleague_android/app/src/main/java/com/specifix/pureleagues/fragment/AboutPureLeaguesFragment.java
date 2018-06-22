package com.specifix.pureleagues.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.specifix.pureleagues.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutPureLeaguesFragment extends Fragment {
    @BindView(R.id.about_scroll_view)
    ScrollView mScroll;
    @BindView(R.id.about_leagues_title_text_view)
    TextView mTitle;
    @BindView(R.id.about_leagues_subtitle_text_view)
    TextView mSubTitle;
    @BindView(R.id.about_leagues_part_1_text_view)
    WebView mPartOne;

    public static AboutPureLeaguesFragment newInstance() {
        AboutPureLeaguesFragment fragment = new AboutPureLeaguesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_about_pure_leagues, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Typeface gothamBold = Typeface.createFromAsset(getContext().getAssets(), "GothamBold.otf");
        Typeface gothamBook = Typeface.createFromAsset(getContext().getAssets(), "GothamBook.otf");
        mTitle.setTypeface(gothamBold);
        mSubTitle.setTypeface(gothamBold);
//        mPartOne.setTypeface(gothamBook);
//        mPartOne.setText(Html.fromHtml(getString(R.string.about_pure_leagues)));
        mPartOne.loadData(getString(R.string.about_pure_leagues), "text/html", "utf-8");
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
}
