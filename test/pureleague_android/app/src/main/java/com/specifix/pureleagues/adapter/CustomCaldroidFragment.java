package com.specifix.pureleagues.adapter;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CalendarHelper;
import com.roomorama.caldroid.WeekdayArrayAdapter;
import com.specifix.pureleagues.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import hirondelle.date4j.DateTime;

public class CustomCaldroidFragment extends CaldroidFragment {

    @Override
    public WeekdayArrayAdapter getNewWeekdayAdapter(int themeResource) {
        return new WeekdayArrayAdapter(getContext(), R.layout.weekday_view, getDaysOfWeek(), themeResource);
    }

    @Override
    protected ArrayList<String> getDaysOfWeek() {
        ArrayList<String> list = new ArrayList<String>();

        SimpleDateFormat fmt = new SimpleDateFormat("EEE", Locale.getDefault());

        // 17 Feb 2013 is Sunday
        DateTime sunday = new DateTime(2013, 2, 17, 0, 0, 0, 0);
        DateTime nextDay = sunday.plusDays(startDayOfWeek - SUNDAY);

        for (int i = 0; i < 7; i++) {
            Date date = CalendarHelper.convertDateTimeToDate(nextDay);
            list.add(fmt.format(date).substring(0, 1).toUpperCase());
            nextDay = nextDay.plusDays(1);
        }

        return list;
    }
}
