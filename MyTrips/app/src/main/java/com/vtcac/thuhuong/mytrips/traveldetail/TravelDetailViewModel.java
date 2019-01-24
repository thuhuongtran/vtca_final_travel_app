package com.vtcac.thuhuong.mytrips.traveldetail;

import android.app.Application;

import com.vtcac.thuhuong.mytrips.database.PlanRepository;
import com.vtcac.thuhuong.mytrips.entity.Plan;
import com.vtcac.thuhuong.mytrips.entity.Travel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class TravelDetailViewModel extends AndroidViewModel {
    private final TravelDetailRepository travelDetailRepo;
    private final PlanRepository planRepository;
    private long travelId;

    public void setTravelId(long travelId) {
        this.travelId = travelId;
    }

    public TravelDetailViewModel(@NonNull Application application) {
        super(application);
        travelDetailRepo = TravelDetailRepository.getInstance(application);
        planRepository = PlanRepository.getInstance(application);
    }

    public LiveData<Travel> getTravelById() {
        return travelDetailRepo.getTravelById(travelId);
    }

    public LiveData<List<Plan>> getPlanList() {
        return planRepository.getAllPlansByTravelId(travelId);
    }

}
