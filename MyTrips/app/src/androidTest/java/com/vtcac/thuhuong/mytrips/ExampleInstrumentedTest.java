package com.vtcac.thuhuong.mytrips;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.vtcac.thuhuong.mytrips.dao.TravelDao;
import com.vtcac.thuhuong.mytrips.database.AppDatabase;
import com.vtcac.thuhuong.mytrips.database.TravelRepository;
import com.vtcac.thuhuong.mytrips.entity.Travel;
import com.vtcac.thuhuong.mytrips.traveldetail.TravelDetailRepository;

import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
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
        System.out.println("id="+travel.getId()+"name="+travel.getPlaceName());
    }

}
