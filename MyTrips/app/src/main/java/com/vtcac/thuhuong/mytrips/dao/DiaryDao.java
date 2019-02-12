package com.vtcac.thuhuong.mytrips.dao;

import com.vtcac.thuhuong.mytrips.entity.Diary;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface DiaryDao {
    @Query("SELECT * FROM `diary` WHERE travelId=:travelId ORDER BY startDt DESC")
    LiveData<List<Diary>> getAllDiaryByTravelId(long travelId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Diary... diaries);

    @Delete
    void delete(Diary... diaries);

    @Update
    void update(Diary... diaries);
}
