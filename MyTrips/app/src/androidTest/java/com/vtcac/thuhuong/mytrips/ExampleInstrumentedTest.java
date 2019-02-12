package com.vtcac.thuhuong.mytrips;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.vtcac.thuhuong.mytrips.entity.Travel;
import com.vtcac.thuhuong.mytrips.traveldetail.TravelDetailRepository;
import com.vtcac.thuhuong.mytrips.utils.MyDate;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.ParseException;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private static final String TAG = ExampleInstrumentedTest.class.getSimpleName();

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.vtcac.thuhuong.mytrips", appContext.getPackageName());
    }
    /*private TravelDao dao;
    private AppDatabase mDb;

    @Before
    public void createDb() {
        Context context = (new Application()).getBaseContext();
        mDb = Room.inMemoryDatabaseBuilder(context, TestDatabase.class).build();
        dao = mDb.travelDao();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }*/

    @Test
    public void testGetTravelById() {
//        AppDatabase db = AppDatabase.getDatabase((new Application()).getBaseContext());
        TravelDetailRepository repo = TravelDetailRepository.getInstance(new Application());
//        TravelDao dao = db.travelDao();
        Travel travel = repo.getTravelById(3).getValue();
        System.out.println(travel.toString());
        System.out.println("id=" + travel.getId() + "name=" + travel.getPlaceName());
    }

    @Test
    public void testTimestampToTime() throws ParseException {
        String d = "2019-2-1 17:32:50";
        Log.d(TAG, "testTimestampToTime: " + d + " to timestamp=" + MyDate.dateTxtToTimestamp(d));
        Log.d(TAG, "testTimestampToTime: " + System.currentTimeMillis());
        Log.d(TAG, "testTimestampToTime: " + MyDate.timestampToTime(System.currentTimeMillis()));
        Log.d(TAG, "testTimestampToTime: test timestamp to date=" + MyDate.timestampToTime(MyDate.dateTxtToTimestamp(d)));
    }

}
