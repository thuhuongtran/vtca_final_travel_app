package com.vtcac.thuhuong.mytrips.dao;

import com.vtcac.thuhuong.mytrips.entity.Plan;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface PlanDao {
    @Query("SELECT * FROM `plan` WHERE travelId=:travelId ORDER BY startDt DESC, id DESC")
    LiveData<List<Plan>> getAllPlansByTravelId(long travelId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Plan... plans);

    @Delete
    void delete(Plan... plans);

    @Update
    void update(Plan... plans);


}
