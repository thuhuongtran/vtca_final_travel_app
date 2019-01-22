package com.vtcac.thuhuong.mytrips.dao;

import com.vtcac.thuhuong.mytrips.entity.Travel;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TravelDao {
    @Query("SELECT *,`rowid` FROM travel")
    LiveData<List<Travel>> getAllTravelsDefault();
    /**
     * get travels sorted by newest end date
     *
     * @return paged_list travels
     */
    @Query("SELECT *,`rowid` FROM travel ORDER BY endDt DESC")
    LiveData<List<Travel>> getAllTravelsByEndDesc();

    @Query("SELECT *,`rowid` FROM travel WHERE placeName MATCH :placeName")
    LiveData<List<Travel>> getAllTravelsByTypingCity(String placeName);

    @Query("SELECT *,`rowid` FROM travel ORDER BY title ASC")
    LiveData<List<Travel>> getAllTravelsByTitleAsc();

    @Query("SELECT *,`rowid` FROM travel ORDER BY title DESC")
    LiveData<List<Travel>> getAllTravelsByTitleDsc();

    /**
     * get travels sorted by newest start date
     *
     * @return paged_list travels
     */
    @Query("SELECT *,`rowid` FROM travel ORDER BY startDt DESC")
    LiveData<List<Travel>> getAllTravelsByStartDesc();

    @Query("SELECT *,`rowid` FROM travel ORDER BY startDt ASC")
    LiveData<List<Travel>> getAllTravelsByStartAsc();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTravel(Travel... travels);

    @Delete
    void delete(Travel... travels);

    @Update
    void update(Travel... travels);

}
