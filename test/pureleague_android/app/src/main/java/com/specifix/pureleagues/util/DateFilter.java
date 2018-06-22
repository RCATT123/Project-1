package com.specifix.pureleagues.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFilter {

    public static Date filterDate(String input) {
        if (!TextUtils.isEmpty(input)) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.ENGLISH);
                return simpleDateFormat.parse(input);
            } catch (ParseException ignore) {
            }
        }

        return null;
    }
}
