package com.vtcac.thuhuong.mytrips.database;

import android.app.Application;
import android.os.AsyncTask;

import com.vtcac.thuhuong.mytrips.dao.DiaryDao;
import com.vtcac.thuhuong.mytrips.entity.Diary;

import java.util.List;

import androidx.lifecycle.LiveData;

public class DiaryRepository {
    private final static String TAG = DiaryRepository.class.getSimpleName();
    private static volatile DiaryRepository INSTANCE;
    private final Application application;
    private final DiaryDao diaryDao;

    public DiaryRepository(Application application) {
        this.application = application;
        AppDatabase db = AppDatabase.getDatabase(application.getBaseContext());
        diaryDao = db.diaryDao();
    }

    public static DiaryRepository getInstance(final Application application) {
        if (INSTANCE == null) {
            synchronized (DiaryRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DiaryRepository(application);
                }
            }
        }
        return INSTANCE;
    }

    public LiveData<List<Diary>> getAllDiariesByTravelId(long travelId) {
        return diaryDao.getAllDiaryByTravelId(travelId);
    }

    // insert
    public void insert(Diary diary) {
        new DiaryRepository.insertAsyncTask(diaryDao).execute(diary);
    }

    private static class insertAsyncTask extends AsyncTask<Diary, Void, Void> {
        private DiaryDao asyncTaskDao;

        insertAsyncTask(DiaryDao asyncTaskDao) {
            this.asyncTaskDao = asyncTaskDao;
        }

        @Override
        protected Void doInBackground(Diary... diaries) {
            asyncTaskDao.insert(diaries[0]);
            return null;
        }
    }

    // delete
    public void delete(Diary diary) {
        new DiaryRepository.deleteAsyncTask(diaryDao).execute(diary);
    }

    private static class deleteAsyncTask extends AsyncTask<Diary, Void, Void> {
        private DiaryDao asyncTaskDao;

        deleteAsyncTask(DiaryDao asyncTaskDao) {
            this.asyncTaskDao = asyncTaskDao;
        }

        @Override
        protected Void doInBackground(Diary... diaries) {
            asyncTaskDao.delete(diaries[0]);
            return null;
        }
    }

    // update
    public void update(Diary diary) {
        new DiaryRepository.updateAsyncTask(diaryDao).execute(diary);
    }

    private static class updateAsyncTask extends AsyncTask<Diary, Void, Void> {
        private DiaryDao mAsyncTaskDao;

        updateAsyncTask(DiaryDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Diary... diaries) {
            mAsyncTaskDao.update(diaries[0]);
            return null;
        }
    }
}
