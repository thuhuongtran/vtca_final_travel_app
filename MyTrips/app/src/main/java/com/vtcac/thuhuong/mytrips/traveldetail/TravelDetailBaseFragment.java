package com.vtcac.thuhuong.mytrips.traveldetail;

import android.os.Bundle;

import com.vtcac.thuhuong.mytrips.entity.Travel;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public abstract class TravelDetailBaseFragment extends Fragment {
    private final static String TAG = TravelDetailBaseFragment.class.getSimpleName();
    protected TravelDetailViewModel travelDetailViewModel;
    protected static final String ARG_SECTION_NUMBER = "section_number";
   /* private final Observer<Travel> travelObserver = new Observer<Travel>() {
        @Override
        public void onChanged(Travel travel) {
//            onTravelChanged(travel);
        }
    };*/
    public abstract void requestAddItem(Travel travel);

//    protected abstract void onTravelChanged(Travel travel);
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // init id
        travelDetailViewModel = ViewModelProviders.of(getActivity()).get(TravelDetailViewModel.class);
//        travelDetailViewModel.getTravelById().observe(this, travelObserver);
//        Log.d(TAG, "onCreate: travelDetailViewModel=" + travelDetailViewModel);
    }


}
