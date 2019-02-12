package com.vtcac.thuhuong.mytrips.database;

import android.content.Context;

import com.vtcac.thuhuong.mytrips.dao.DiaryDao;
import com.vtcac.thuhuong.mytrips.dao.ExpenseDao;
import com.vtcac.thuhuong.mytrips.dao.PlanDao;
import com.vtcac.thuhuong.mytrips.dao.TravelDao;
import com.vtcac.thuhuong.mytrips.entity.Diary;
import com.vtcac.thuhuong.mytrips.entity.Expense;
import com.vtcac.thuhuong.mytrips.entity.Plan;
import com.vtcac.thuhuong.mytrips.entity.Travel;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Travel.class, Plan.class, Diary.class, Expense.class},version = 13)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;
    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext()
                            , AppDatabase.class, "travel_db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    public abstract TravelDao travelDao();

    public abstract PlanDao planDao();

    public abstract DiaryDao diaryDao();

    public abstract ExpenseDao expenseDao();
}
