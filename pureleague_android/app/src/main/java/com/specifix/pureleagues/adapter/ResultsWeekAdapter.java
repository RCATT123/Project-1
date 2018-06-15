package com.specifix.pureleagues.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.specifix.pureleagues.fragment.ResultsWeekFragment;
import com.specifix.pureleagues.model.Result;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class ResultsWeekAdapter extends FragmentPagerAdapter {
    private List<Result> mResults;
    private List<List<Result>> mResultsSublist;
    ResultsAdapter.ResultAdapterListener mListener;

    public ResultsWeekAdapter(FragmentManager fm, List<Result> results, ResultsAdapter.ResultAdapterListener listener) {
        super(fm);
        this.mResults = results;
        this.mListener = listener;

        //calculate week count
        Iterator<Result> iterator = mResults.iterator();
        while (iterator.hasNext()) {
            Result result = iterator.next();
            if (result.getCalendar() == null) {
                iterator.remove();
            }
        }

        mResultsSublist = new ArrayList<>();

        if (mResults != null || mResults.size() > 0) {
            int week = -1;
            int year = -1;
            int startIndex = 0;

            for (int i = 0; i < mResults.size(); i++) {
                Result fixture = mResults.get(i);
                Calendar c = fixture.getCalendar();
                int nWeek = c.get(Calendar.WEEK_OF_YEAR);
                int nYear = c.get(Calendar.YEAR);

                if (nYear != year || nWeek != week) {

                    if (week != -1) {
//                        mWeekCount++;
                        mResultsSublist.add(mResults.subList(startIndex, i));
                    }
                    week = nWeek;
                    year = nYear;

                    startIndex = i;
                }
            }
            mResultsSublist.add(mResults.subList(startIndex, mResults.size()));
        }
    }

    @Override
    public Fragment getItem(int position) {
        //return new fragment with sublist of this
        int reversePosition = mResultsSublist.size() - position - 1;
        return ResultsWeekFragment.newInstance(mResultsSublist.get(reversePosition), mListener);//GalleryPreviewFragment.newInstance(mMessages.get(position));
    }

    @Override
    public int getCount() {
        return mResultsSublist.size();
    }
}