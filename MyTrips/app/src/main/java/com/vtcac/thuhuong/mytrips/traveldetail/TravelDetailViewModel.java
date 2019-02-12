package com.vtcac.thuhuong.mytrips.traveldetail;

import android.app.Application;

import com.vtcac.thuhuong.mytrips.database.DiaryRepository;
import com.vtcac.thuhuong.mytrips.database.ExpenseRepository;
import com.vtcac.thuhuong.mytrips.database.PlanRepository;
import com.vtcac.thuhuong.mytrips.database.TravelRepository;
import com.vtcac.thuhuong.mytrips.entity.Diary;
import com.vtcac.thuhuong.mytrips.entity.Expense;
import com.vtcac.thuhuong.mytrips.entity.Plan;
import com.vtcac.thuhuong.mytrips.entity.Travel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class TravelDetailViewModel extends AndroidViewModel {
    private final TravelDetailRepository travelDetailRepo;
    private final TravelRepository travelRepo;
    private final PlanRepository planRepository;
    private final DiaryRepository diaryRepository;
    private final ExpenseRepository expenseRepository;
    private long travelId;

    public void setTravelId(long travelId) {
        this.travelId = travelId;
    }

    public TravelDetailViewModel(@NonNull Application application) {
        super(application);
        travelDetailRepo = TravelDetailRepository.getInstance(application);
        planRepository = PlanRepository.getInstance(application);
        diaryRepository = DiaryRepository.getInstance(application);
        expenseRepository = ExpenseRepository.getInstance(application);
        travelRepo = TravelRepository.getInstance(application);
    }

    public LiveData<Travel> getTravelById() {
        return travelDetailRepo.getTravelById(travelId);
    }

    public LiveData<List<Plan>> getPlanList() {
        return planRepository.getAllPlansByTravelId(travelId);
    }
    public LiveData<List<Diary>> getDiaryList() {
        return diaryRepository.getAllDiariesByTravelId(travelId);
    }
    public LiveData<List<Expense>> getExpenseList() {
        return expenseRepository.getAllExpensesByTravelId(travelId);
    }

    public void updateTravel(Travel travel) {
        travelRepo.update(travel);
    }
    public LiveData<List<Expense>> getBudgetStatus() {
        return travelDetailRepo.getBudgetStatus(travelId);
    }
}
