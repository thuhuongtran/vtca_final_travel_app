package com.vtcac.thuhuong.mytrips.model;

import android.app.Application;

import com.vtcac.thuhuong.mytrips.database.TravelRepository;
import com.vtcac.thuhuong.mytrips.entity.Travel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class TravelViewModel extends AndroidViewModel {
    private final TravelRepository travelRepo;
    private LiveData<List<Travel>> travelList;
    public TravelViewModel(@NonNull Application application) {
        super(application);
        travelRepo = TravelRepository.getInstance(application);
    }

    public LiveData<List<Travel>> getAllTravelsByStartDesc() {
        return travelRepo.getAllTravelsByStartDesc();
    }
    public LiveData<List<Travel>> getAllTravelsByEndDesc() {
        return travelRepo.getAllTravelsByEndDesc();
    }

    // for search activity
    public LiveData<List<Travel>> getAllTravelsByTypingCity(String placeName) {
        return travelRepo.getAllTravelsByTypingCity(placeName);
    }

    public void insertTravel(Travel travel) {
        travelRepo.insertTravel(travel);
    }
}
