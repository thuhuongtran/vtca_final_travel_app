package com.vtcac.thuhuong.mytrips.base;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.vtcac.thuhuong.mytrips.utils.MyConst;

public class MyApplication extends Application {
    private int optionSort;
    private int listSize;

    @Override
    public void onCreate() {
        super.onCreate();
        getTravelSortOption();
    }

    public void setOptionSort(int optionSort) {
        SharedPreferences sp = getSharedPreferences(MyConst.SORT_OPTION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(MyConst.TRAVEL_SORT_KEY, optionSort);
        editor.apply();
        this.optionSort = optionSort;
    }

    public int getTravelSortOption() {
        SharedPreferences sp = getSharedPreferences(MyConst.SORT_OPTION, Context.MODE_PRIVATE);
        int optionSort = sp.getInt(MyConst.TRAVEL_SORT_KEY, MyConst.SORT_BY_DEFAULT);
        this.optionSort = optionSort;
        return optionSort;
    }
    public void setTravelListSize(int size) {
        SharedPreferences sp = getSharedPreferences(MyConst.TRAVEL_LIST_SIZE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(MyConst.TRAVEL_LIST_SIZE, size);
        editor.apply();
        this.listSize = size;
    }

    public int getTravelListSize() {
        SharedPreferences sp = getSharedPreferences(MyConst.TRAVEL_LIST_SIZE, Context.MODE_PRIVATE);
        int size = sp.getInt(MyConst.TRAVEL_LIST_SIZE, 0);
        this.listSize = size;
        return size;
    }
}
