package com.vtcac.thuhuong.mytrips.database;

import android.app.Application;
import android.os.AsyncTask;

import com.vtcac.thuhuong.mytrips.dao.TravelDao;
import com.vtcac.thuhuong.mytrips.entity.Travel;

import java.util.List;

import androidx.lifecycle.LiveData;

public class TravelRepository
{
    private final static String TAG = TravelRepository.class.getSimpleName();
    private static volatile TravelRepository INSTANCE;
    private final Application application;
    private final TravelDao travelDao;

    public TravelRepository(Application application) {
        this.application = application;
        AppDatabase db = AppDatabase.getDatabase(application.getBaseContext());
        travelDao = db.travelDao();
    }

    public static TravelRepository getInstance(final Application application) {
        if (INSTANCE == null) {
            synchronized (TravelRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TravelRepository(application);
                }
            }
        }
        return INSTANCE;
    }

    public LiveData<List<Travel>> getAllTravelsByStartDesc() {
        return travelDao.getAllTravelsByStartDesc();
    }

    public LiveData<List<Travel>> getAllTravelsByEndDesc() {
        return travelDao.getAllTravelsByEndDesc();
    }
    // for search activity
    public LiveData<List<Travel>> getAllTravelsByTypingCity(String placeName) {
        return travelDao.getAllTravelsByTypingCity(placeName);
    }
    // insert
    public void insertTravel(Travel travel) {
        new insertAsyncTask(travelDao).execute(travel);
    }

    private static class insertAsyncTask extends AsyncTask<Travel, Void, Void> {
        private TravelDao asyncTaskDao;

         insertAsyncTask(TravelDao asyncTaskDao) {
            this.asyncTaskDao = asyncTaskDao;
        }

        @Override
        protected Void doInBackground(Travel... travels) {
             asyncTaskDao.insertTravel(travels[0]);
            return null;
        }
    }
}
