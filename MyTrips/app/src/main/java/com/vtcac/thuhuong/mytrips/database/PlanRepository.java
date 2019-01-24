package com.vtcac.thuhuong.mytrips.database;

import android.app.Application;
import android.os.AsyncTask;

import com.vtcac.thuhuong.mytrips.dao.PlanDao;
import com.vtcac.thuhuong.mytrips.entity.Plan;

import java.util.List;

import androidx.lifecycle.LiveData;

public class PlanRepository {
    private final static String TAG = PlanRepository.class.getSimpleName();
    private static volatile PlanRepository INSTANCE;
    private final Application application;
    private final PlanDao planDao;

    public PlanRepository(Application application) {
        this.application = application;
        AppDatabase db = AppDatabase.getDatabase(application.getBaseContext());
        planDao = db.planDao();
    }

    public static PlanRepository getInstance(final Application application) {
        if (INSTANCE == null) {
            synchronized (PlanRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PlanRepository(application);
                }
            }
        }
        return INSTANCE;
    }

    public LiveData<List<Plan>> getAllPlansByTravelId(long travelId) {
        return planDao.getAllPlansByTravelId(travelId);
    }
    // insert
    public void insert(Plan plan) {
        new insertAsyncTask(planDao).execute(plan);
    }

    private static class insertAsyncTask extends AsyncTask<Plan, Void, Void> {
        private PlanDao asyncTaskDao;

        insertAsyncTask(PlanDao asyncTaskDao) {
            this.asyncTaskDao = asyncTaskDao;
        }

        @Override
        protected Void doInBackground(Plan... plans) {
            asyncTaskDao.insert(plans[0]);
            return null;
        }
    }

    // delete
    public void delete(Plan plan) {
        new deleteAsyncTask(planDao).execute(plan);
    }

    private static class deleteAsyncTask extends AsyncTask<Plan, Void, Void> {
        private PlanDao asyncTaskDao;

        deleteAsyncTask(PlanDao asyncTaskDao) {
            this.asyncTaskDao = asyncTaskDao;
        }

        @Override
        protected Void doInBackground(Plan... plans) {
            asyncTaskDao.delete(plans[0]);
            return null;
        }
    }

    // update
    public void update(Plan plan) {
        new updateAsyncTask(planDao).execute(plan);
    }
    private static class updateAsyncTask extends AsyncTask<Plan, Void, Void> {
        private PlanDao mAsyncTaskDao;

        updateAsyncTask(PlanDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Plan... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }
}
