package com.vtcac.thuhuong.mytrips.base;

import android.view.View;

import com.vtcac.thuhuong.mytrips.entity.TravelBaseEntity;

public interface ViewItemListClickListener {
    void onViewItemListClick(View v, int position, TravelBaseEntity entity);
}
