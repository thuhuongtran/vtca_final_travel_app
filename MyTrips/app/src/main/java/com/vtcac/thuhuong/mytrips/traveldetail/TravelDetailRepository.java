package com.vtcac.thuhuong.mytrips.traveldetail;

import android.app.Application;

import com.vtcac.thuhuong.mytrips.dao.PlanDao;
import com.vtcac.thuhuong.mytrips.dao.TravelDao;
import com.vtcac.thuhuong.mytrips.database.AppDatabase;
import com.vtcac.thuhuong.mytrips.database.TravelRepository;
import com.vtcac.thuhuong.mytrips.entity.Travel;

import androidx.lifecycle.LiveData;

public class TravelDetailRepository {
    private final static String TAG = TravelDetailRepository.class.getSimpleName();
    private static volatile TravelDetailRepository INSTANCE;
    private final Application application;
    private final TravelDao travelDao;
    private final PlanDao planDao;

    public TravelDetailRepository(Application application) {
        this.application = application;
        AppDatabase db = AppDatabase.getDatabase(application.getBaseContext());
        travelDao = db.travelDao();
        planDao = db.planDao();
    }

    public static TravelDetailRepository getInstance(final Application application) {
        if (INSTANCE == null) {
            synchronized (TravelRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TravelDetailRepository(application);
                }
            }
        }
        return INSTANCE;
    }

    public LiveData<Travel> getTravelById(long travelId) {
        return travelDao.getTravelById(travelId);
    }

}
