package com.vtcac.thuhuong.mytrips.traveldetail.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vtcac.thuhuong.mytrips.R;
import com.vtcac.thuhuong.mytrips.entity.Travel;
import com.vtcac.thuhuong.mytrips.traveldetail.TravelDetailBaseFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FragmentDiary extends TravelDetailBaseFragment {
    private static final String TAG = FragmentDiary.class.getSimpleName();
    public static final int TITLE_ID = R.string.tab_diary;
    public FragmentDiary() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static FragmentDiary newInstance(int sectionNumber) {
        Log.d(TAG, "newInstance: sectionNumber=" + sectionNumber);
        FragmentDiary fragment = new FragmentDiary();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void requestAddItem(Travel travel) {

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_diary, container, false);
//        TextView textView = (TextView) rootView.findViewById(R.id.tvtes);
//        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        return rootView;
    }
}
