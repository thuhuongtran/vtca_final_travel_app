package com.vtcac.thuhuong.mytrips.model;

import android.app.Application;

import com.vtcac.thuhuong.mytrips.database.TravelRepository;
import com.vtcac.thuhuong.mytrips.entity.Travel;
import com.vtcac.thuhuong.mytrips.utils.MyConst;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class TravelViewModel extends AndroidViewModel {
    private final TravelRepository travelRepo;

    public TravelViewModel(@NonNull Application application) {
        super(application);
        travelRepo = TravelRepository.getInstance(application);
    }

    public LiveData<List<Travel>> getAllTravelsByDefault() {
        return travelRepo.getAllTravelsDefault();
    }

    public LiveData<List<Travel>> getAllTravelsSorted(int sortOption) {
        switch (sortOption) {
            case MyConst.SORT_BY_DEFAULT:
                return travelRepo.getAllTravelsDefault();
            case MyConst.SORT_BY_TITLE_ASC:
                return travelRepo.getAllTravelsByTitleAsc();
            case MyConst.SORT_BY_TITLE_DSC:
                return travelRepo.getAllTravelsByTitleDsc();
            case MyConst.SORT_BY_DATE_ASC:
                return travelRepo.getAllTravelsByStartAsc();
            case MyConst.SORT_BY_DATE_DSC:
                return travelRepo.getAllTravelsByStartDesc();
        }
        return travelRepo.getAllTravelsDefault();
    }

    // for search activity
    public LiveData<List<Travel>> getAllTravelsByTypingCity(String placeName) {
        return travelRepo.getAllTravelsByTypingCity(placeName);
    }

    public void insertTravel(Travel travel) {
        travelRepo.insertTravel(travel);
    }
    public void deleteTravel(Travel travel) {
        travelRepo.delete(travel);
    }
    public void updateTravel(Travel travel) {
        travelRepo.update(travel);
    }

}
