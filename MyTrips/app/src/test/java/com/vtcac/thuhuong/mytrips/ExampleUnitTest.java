package com.vtcac.thuhuong.mytrips;

import android.util.Log;

import com.vtcac.thuhuong.mytrips.dao.TravelDao;
import com.vtcac.thuhuong.mytrips.database.AppDatabase;
import com.vtcac.thuhuong.mytrips.utils.MyDate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private static final String TAG = ExampleUnitTest.class.getSimpleName();
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testGetCurrentTimestamp() {
        String timestamp = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
        System.out.println(timestamp);
        Log.d(TAG, "testGetCurrentTimestamp: date="+timestamp);
    }

    @Test
    public void testTimestampToDateInWord() {
        DateFormat df = new SimpleDateFormat("MMM dd, yyyy", Locale.UK);
        Date date = new Date(System.currentTimeMillis());
        String dateInWord = df.format(date);
        System.out.println(dateInWord);
    }

    @Test
    public void testCalendarToTimestamp() {
        //1567351201213
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, 8, 1);
        System.out.println(calendar.getTimeInMillis());
        System.out.println(System.currentTimeMillis());
        System.out.println(MyDate.timestampToDate(calendar.getTimeInMillis()));
    }

    @Test
    public void testTimestampToTime() {
        System.out.println(System.currentTimeMillis());
        System.out.println(MyDate.timestampToTime(System.currentTimeMillis()));
        System.out.println(MyDate.timestampToTime(1549968950));
    }

}