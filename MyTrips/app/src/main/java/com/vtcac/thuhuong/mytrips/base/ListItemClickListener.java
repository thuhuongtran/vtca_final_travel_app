package com.vtcac.thuhuong.mytrips.base;

import android.view.View;

import com.vtcac.thuhuong.mytrips.entity.TravelBaseEntity;

public interface ListItemClickListener {
    void onListItemClick(View v, int position, TravelBaseEntity entity);

    void onMoreVertMenuItemClick(int viewId, int position, TravelBaseEntity entity);
}
