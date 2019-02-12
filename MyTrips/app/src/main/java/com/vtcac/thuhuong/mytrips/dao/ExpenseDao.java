package com.vtcac.thuhuong.mytrips.dao;

import com.vtcac.thuhuong.mytrips.entity.Expense;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ExpenseDao {
    @Query("SELECT * FROM `expense` WHERE travelId=:travelId ORDER BY startDt DESC")
    LiveData<List<Expense>> getAllExpensesByTravelId(long travelId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Expense... expenses);

    @Delete
    void delete(Expense... expenses);

    @Update
    void update(Expense... expenses);

    @Query("select count(*) as id" +
            ", travelId" +
            ", `startDt`" +
            ", title" +
            ", `desc`" +
            ", placeId" +
            ", placeName" +
            ", placeAddr" +
            ", (select sum(amount) from expense where travelId=te.travelId and currency=te.currency and type='BUDGET') as placeLat" +
            ", placeLng" +
            ", type" +
            ", (select sum(amount) from expense where travelId=te.travelId and currency=te.currency and type='EXPENSE') as amount" +
            ", currency " +
            "from expense  te " +
            "where travelId=:travelId group by currency order by id desc, amount desc")
    LiveData<List<Expense>> getBudgetStatus(long travelId);
}
