package com.specifix.pureleagues.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.specifix.pureleagues.adapter.FixturesAdapter;
import com.specifix.pureleagues.fragment.FixturesWeekFragment;
import com.specifix.pureleagues.model.Fixture;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class FixturesWeekAdapter extends FragmentPagerAdapter {
    private List<Fixture> mFixtures;
    private List<List<Fixture>> mFixturesSublist;
    FixturesAdapter.FixturesAdapterListener mListener;

    public FixturesWeekAdapter(FragmentManager fm, List<Fixture> fixtures, FixturesAdapter.FixturesAdapterListener listener) {
        super(fm);
        this.mFixtures = fixtures;
        this.mListener = listener;

        //calculate week count
        Iterator<Fixture> iterator = mFixtures.iterator();
        while (iterator.hasNext()) {
            Fixture fixture = iterator.next();
            if (fixture.getCalendar() == null) {
                iterator.remove();
            }
        }

        mFixturesSublist = new ArrayList<>();
        if (mFixtures != null || mFixtures.size() > 0) {
            int week = -1;
            int year = -1;
            int startIndex = 0;

            for (int i = 0; i < mFixtures.size(); i++) {
                Fixture fixture = mFixtures.get(i);
                Calendar c = fixture.getCalendar();
                int nWeek = c.get(Calendar.WEEK_OF_YEAR);
                int nYear = c.get(Calendar.YEAR);

                if (nYear != year || nWeek != week) {

                    if (week != -1) {
                        mFixturesSublist.add(mFixtures.subList(startIndex, i));
                    }
                    week = nWeek;
                    year = nYear;

                    startIndex = i;
                }
            }
            mFixturesSublist.add(mFixtures.subList(startIndex, mFixtures.size()));
        }
    }

    public int getSublistCount(int position) {
        return mFixturesSublist.get(position).size();
    }

    @Override
    public Fragment getItem(int position) {
        //return new fragment with sublist of this
        return FixturesWeekFragment.newInstance(mFixturesSublist.get(position), mListener);//GalleryPreviewFragment.newInstance(mMessages.get(position));
    }

    @Override
    public int getCount() {
        return mFixturesSublist.size();
    }

}
