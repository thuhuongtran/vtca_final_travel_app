package com.vtcac.thuhuong.mytrips.model;

import android.app.Application;

import com.vtcac.thuhuong.mytrips.database.DiaryRepository;
import com.vtcac.thuhuong.mytrips.database.PlanRepository;
import com.vtcac.thuhuong.mytrips.entity.Diary;
import com.vtcac.thuhuong.mytrips.entity.Plan;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class DiaryViewModel extends AndroidViewModel {
    private final DiaryRepository diaryRepo;

    public DiaryViewModel(@NonNull Application application) {
        super(application);
        diaryRepo = DiaryRepository.getInstance(application);
    }

    public LiveData<List<Diary>> getAllDiariesByTravelId(long travelId) {
        return diaryRepo.getAllDiariesByTravelId(travelId);
    }

    public void insert(Diary diary) {
        diaryRepo.insert(diary);
    }

    public void delete(Diary diary) {
        diaryRepo.delete(diary);
    }

    public void update(Diary diary) {
        diaryRepo.update(diary);
    }
}
