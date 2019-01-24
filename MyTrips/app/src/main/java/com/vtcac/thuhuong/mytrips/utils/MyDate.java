package com.vtcac.thuhuong.mytrips.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MyDate {
    private final static DateFormat DATE_TIME_FORMAT = new SimpleDateFormat("MMM dd, yyyy HH:mm a", Locale.getDefault());
    private final static DateFormat DATE_FORMAT = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
    private final static DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm a", Locale.getDefault());

    public static String getCurrentTimestamp() {
        return String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
    }

    public static String timestampToDateTime(long timestamp) {
        String dateInWord = DATE_TIME_FORMAT.format(new Date(timestamp));
        return dateInWord;
    }

    public static String timestampToDate(long timestamp) {
        return DATE_FORMAT.format(new Date(timestamp));
    }
    public static String timestampToTime(long timestamp) {
        String dateInWord = TIME_FORMAT.format(new Date(timestamp));
        return dateInWord;
    }
}
