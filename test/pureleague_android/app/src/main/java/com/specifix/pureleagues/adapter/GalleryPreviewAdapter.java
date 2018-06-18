package com.specifix.pureleagues.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.specifix.pureleagues.fragment.GalleryPreviewFragment;
import com.specifix.pureleagues.model.ChatMessage;

import java.util.List;

public class GalleryPreviewAdapter extends FragmentPagerAdapter {
    private List<ChatMessage> mMessages;

    public GalleryPreviewAdapter(FragmentManager fm, List<ChatMessage> messages) {
        super(fm);
        mMessages = messages;
    }

    @Override
    public Fragment getItem(int position) {
        return GalleryPreviewFragment.newInstance(mMessages.get(position));
    }

    @Override
    public int getCount() {
        return mMessages.size();
    }
}
