package com.vtcac.thuhuong.mytrips.database;

import android.app.Application;
import android.os.AsyncTask;

import com.vtcac.thuhuong.mytrips.dao.ExpenseDao;
import com.vtcac.thuhuong.mytrips.entity.Expense;

import java.util.List;

import androidx.lifecycle.LiveData;

public class ExpenseRepository {
    private final static String TAG = ExpenseRepository.class.getSimpleName();
    private static volatile ExpenseRepository INSTANCE;
    private final Application application;
    private final ExpenseDao expenseDao;

    public ExpenseRepository(Application application) {
        this.application = application;
        AppDatabase db = AppDatabase.getDatabase(application.getBaseContext());
        expenseDao = db.expenseDao();
    }

    public static ExpenseRepository getInstance(final Application application) {
        if (INSTANCE == null) {
            synchronized (ExpenseRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ExpenseRepository(application);
                }
            }
        }
        return INSTANCE;
    }

    public LiveData<List<Expense>> getAllExpensesByTravelId(long travelId) {
        return expenseDao.getAllExpensesByTravelId(travelId);
    }

    // insert
    public void insert(Expense expense) {
        new ExpenseRepository.insertAsyncTask(expenseDao).execute(expense);
    }

    private static class insertAsyncTask extends AsyncTask<Expense, Void, Void> {
        private ExpenseDao asyncTaskDao;

        insertAsyncTask(ExpenseDao asyncTaskDao) {
            this.asyncTaskDao = asyncTaskDao;
        }

        @Override
        protected Void doInBackground(Expense... expenses) {
            asyncTaskDao.insert(expenses[0]);
            return null;
        }
    }

    // delete
    public void delete(Expense expense) {
        new ExpenseRepository.deleteAsyncTask(expenseDao).execute(expense);
    }

    private static class deleteAsyncTask extends AsyncTask<Expense, Void, Void> {
        private ExpenseDao asyncTaskDao;

        deleteAsyncTask(ExpenseDao asyncTaskDao) {
            this.asyncTaskDao = asyncTaskDao;
        }

        @Override
        protected Void doInBackground(Expense... expenses) {
            asyncTaskDao.delete(expenses[0]);
            return null;
        }
    }

    // update
    public void update(Expense expense) {
        new ExpenseRepository.updateAsyncTask(expenseDao).execute(expense);
    }

    private static class updateAsyncTask extends AsyncTask<Expense, Void, Void> {
        private ExpenseDao mAsyncTaskDao;

        updateAsyncTask(ExpenseDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Expense... expenses) {
            mAsyncTaskDao.update(expenses[0]);
            return null;
        }
    }
}
