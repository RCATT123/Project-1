package com.specifix.pureleagues.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.specifix.pureleagues.fragment.DashboardFixtureFragment;
import com.specifix.pureleagues.model.Fixture;
import com.specifix.pureleagues.model.Result;

import java.util.ArrayList;
import java.util.List;

public class DashboardFixturesAdapter extends FragmentPagerAdapter {
    private List<Object> mData;

    public DashboardFixturesAdapter(FragmentManager fm, List<Object> data) {
        super(fm);
        if (data != null) {
            mData = data;
        } else {
            mData = new ArrayList<>();
        }
    }

    @Override
    public Fragment getItem(int position) {
        Object item = mData.get(position);
        if (item instanceof Fixture) {
            return DashboardFixtureFragment.newInstance((Fixture) item);
        } else if (item instanceof Result) {
            return DashboardFixtureFragment.newInstance((Result) item);
        } else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    public void addItem(Object fixture) {
        mData.add(fixture);
        notifyDataSetChanged();
    }
}
