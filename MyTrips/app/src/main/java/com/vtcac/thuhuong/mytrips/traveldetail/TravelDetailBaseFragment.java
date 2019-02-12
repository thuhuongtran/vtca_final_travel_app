package com.vtcac.thuhuong.mytrips.traveldetail;

import android.os.Bundle;

import com.vtcac.thuhuong.mytrips.entity.Expense;
import com.vtcac.thuhuong.mytrips.entity.Travel;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

public abstract class TravelDetailBaseFragment extends Fragment {
    private final static String TAG = TravelDetailBaseFragment.class.getSimpleName();
    protected TravelDetailViewModel travelDetailViewModel;
    protected static final String ARG_SECTION_NUMBER = "section_number";

    public abstract void requestAddItem(Travel travel);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // init id
        travelDetailViewModel = ViewModelProviders.of(getActivity()).get(TravelDetailViewModel.class);
    }

}
