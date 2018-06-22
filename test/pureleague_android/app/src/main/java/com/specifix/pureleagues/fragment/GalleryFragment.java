package com.specifix.pureleagues.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.specifix.pureleagues.R;
import com.specifix.pureleagues.adapter.GalleryAdapter;
import com.specifix.pureleagues.adapter.GalleryPreviewAdapter;
import com.specifix.pureleagues.api.DataManager;
import com.specifix.pureleagues.model.ChatMessage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GalleryFragment extends Fragment
        implements GalleryAdapter.GalleryAdapterListener {
    @BindView(R.id.gallery_recycler_view)
    RecyclerView mGridView;
    @BindView(R.id.gallery_view_pager)
    ViewPager mViewPager;
    @BindView(R.id.gallery_display_preview_layout)
    ViewGroup mViewGroup;
    @BindView(R.id.gallery_images_layout)
    FrameLayout mImagesLayout;
    @BindView(R.id.gallery_no_data_text_view)
    TextView mNoDataTextView;
    @BindView(R.id.gallery_preview_arrow_left)
    View mArrowLeft;
    @BindView(R.id.gallery_preview_arrow_right)
    View mArrowRight;

    private GalleryAdapter mAdapter;
    GalleryPreviewAdapter mPreviewAdapter;
    private List<ChatMessage> mMessages;

    public static GalleryFragment newInstance() {
        GalleryFragment fragment = new GalleryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mGridView.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mAdapter.getItemViewType(position)) {
                    case GalleryAdapter.IMAGE_TYPE:
                        return 1;
                    case GalleryAdapter.FOOTER_TYPE:
                        return 3;
                    default:
                        return -1;
                }
            }
        });
        mGridView.setLayoutManager(layoutManager);

        mMessages = DataManager.getInstance().getGalleryMessages();

        mAdapter = new GalleryAdapter(getContext(), mMessages, this);
        mGridView.setAdapter(mAdapter);

        mPreviewAdapter = new GalleryPreviewAdapter(getChildFragmentManager(), mMessages);
        mViewPager.setAdapter(mPreviewAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                hideButton();
            }
        });
        updateGallery();
    }

    public void updateGallery() {
        DataManager.getInstance().updateGallery(getContext(), new DataManager.DataManagerListener() {
            @Override
            public void onDataDownloaded() {
                if (mMessages.size() == 0) {
                    mImagesLayout.setVisibility(View.GONE);
                    mNoDataTextView.setVisibility(View.VISIBLE);
                } else {
                    mImagesLayout.setVisibility(View.VISIBLE);
                    mNoDataTextView.setVisibility(View.GONE);
                }
//                if (mMessages.size() < 7) {
//                    mFooter.setVisibility(View.VISIBLE);
//                } else {
//                    mFooter.setVisibility(View.GONE);
//                }
                mAdapter.notifyDataSetChanged();
                mPreviewAdapter.notifyDataSetChanged();
            }
        });
    }

    @OnClick(R.id.gallery_preview_arrow_left)
    public void onPrevImageClick() {
        if (mViewPager.getCurrentItem() != 0) {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
        }
        hideButton();
    }

    @OnClick(R.id.gallery_preview_arrow_right)
    public void onNextImageClick() {
        if (mViewPager.getCurrentItem() != mMessages.size() - 1) {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
        }
        hideButton();
    }

    @OnClick(R.id.gallery_preview_close)
    public void onCloseGalleryClick() {
        mViewGroup.setVisibility(View.GONE);
    }

    private void hideButton() {
        int leftVisibility = View.VISIBLE;
        int rightVisibility = View.VISIBLE;

        if (mViewPager.getCurrentItem() == mMessages.size() - 1) {
            rightVisibility = View.GONE;
        }

        if (mViewPager.getCurrentItem() == 0) {
            leftVisibility = View.GONE;
        }

//        mArrowLeft.setVisibility(leftVisibility);
//        mArrowRight.setVisibility(rightVisibility);
    }

    @Override
    public void onMessagesClick(ChatMessage message, int position) {
        mViewGroup.setVisibility(View.VISIBLE);
        mViewPager.setCurrentItem(position);
        hideButton();
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
