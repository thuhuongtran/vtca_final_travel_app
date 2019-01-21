package com.vtcac.thuhuong.mytrips.dao;

import com.vtcac.thuhuong.mytrips.entity.Travel;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface TravelDao {
    /**
     * get travels sorted by newest start date
     *
     * @return paged_list travels
     */
    @Query("SELECT *,`rowid` FROM travel ORDER BY startDt DESC")
    LiveData<List<Travel>> getAllTravelsByStartDesc();
    /**
     * get travels sorted by newest end date
     *
     * @return paged_list travels
     */
    @Query("SELECT *,`rowid` FROM travel ORDER BY endDt DESC")
    LiveData<List<Travel>> getAllTravelsByEndDesc();

    @Query("SELECT *,`rowid` FROM travel WHERE placeName MATCH :placeName")
    LiveData<List<Travel>> getAllTravelsByTypingCity(String placeName);

    @Insert
    void insertTravel(Travel... travels);

}
