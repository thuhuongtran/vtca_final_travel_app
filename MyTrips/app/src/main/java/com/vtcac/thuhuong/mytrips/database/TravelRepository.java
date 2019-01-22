package com.vtcac.thuhuong.mytrips.database;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.vtcac.thuhuong.mytrips.dao.TravelDao;
import com.vtcac.thuhuong.mytrips.entity.Travel;

import java.io.File;
import java.util.List;

import androidx.lifecycle.LiveData;

public class TravelRepository {
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

    public LiveData<List<Travel>> getAllTravelsDefault() {
        return travelDao.getAllTravelsDefault();
    }

    public LiveData<List<Travel>> getAllTravelsByStartDesc() {
        return travelDao.getAllTravelsByStartDesc();
    }

    public LiveData<List<Travel>> getAllTravelsByStartAsc() {
        return travelDao.getAllTravelsByStartAsc();
    }

    public LiveData<List<Travel>> getAllTravelsByTitleDsc() {
        return travelDao.getAllTravelsByTitleDsc();
    }

    public LiveData<List<Travel>> getAllTravelsByTitleAsc() {
        return travelDao.getAllTravelsByTitleAsc();
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

    // delete
    public void delete(Travel travel) {
        new deleteAsyncTask(travelDao).execute(travel);
    }

    private static class deleteAsyncTask extends AsyncTask<Travel, Void, Void> {
        private TravelDao asyncTaskDao;

        deleteAsyncTask(TravelDao asyncTaskDao) {
            this.asyncTaskDao = asyncTaskDao;
        }

        @Override
        protected Void doInBackground(Travel... travels) {
            for (Travel travel : travels) {
                TravelRepository.INSTANCE.deleteTravelDirectory(travel.getId());
            }
            asyncTaskDao.delete(travels[0]);
            return null;
        }
    }
    /**
     * Delete the image directory of a selected travel
     *
     * @param travelId
     */
    private void deleteTravelDirectory(long travelId) {
        try {
            final File rootDir = new File(application.getFilesDir(), "t" + travelId);
            if (!rootDir.exists()) return;
            // Get all files of the directory to be deleted
            File[] files = rootDir.listFiles();
            if (files != null) {
                // delete all files
                for (File file : files) file.delete();
            }
            // delete a directory
            rootDir.delete();
        } catch (Exception e) {
            Log.e("TravelRepository", e.getMessage(), e);
        }
    }

    // update
    public void update(Travel travel) {
        new updateAsyncTask(travelDao).execute(travel);
    }
    private static class updateAsyncTask extends AsyncTask<Travel, Void, Void> {
        private TravelDao mAsyncTaskDao;

        updateAsyncTask(TravelDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Travel... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }
}
