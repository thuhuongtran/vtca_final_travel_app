package com.vtcac.thuhuong.mytrips.model;

import android.app.Application;

import com.vtcac.thuhuong.mytrips.database.PlanRepository;
import com.vtcac.thuhuong.mytrips.entity.Plan;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class PlanViewModel extends AndroidViewModel {
    private final PlanRepository planRepo;

    public PlanViewModel(@NonNull Application application) {
        super(application);
        planRepo = PlanRepository.getInstance(application);
    }

    public LiveData<List<Plan>> getAllPlansByTravelId(long travelId) {
        return planRepo.getAllPlansByTravelId(travelId);
    }

    public void insert(Plan plan) {
        planRepo.insert(plan);
    }
    public void delete(Plan plan) {
        planRepo.delete(plan);
    }
    public void update(Plan plan) {
        planRepo.update(plan);
    }
}
