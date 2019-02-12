package com.vtcac.thuhuong.mytrips.traveldetail;

import android.content.Context;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.vtcac.thuhuong.mytrips.traveldetail.diary.FragmentDiary;
import com.vtcac.thuhuong.mytrips.traveldetail.expense.FragmentExpense;
import com.vtcac.thuhuong.mytrips.traveldetail.plan.FragmentPlan;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    // Sparse array to keep track of registered fragments in memory
    private SparseArray<Fragment> registeredFragments = new SparseArray<>();
    private final Context mContext;

    public SectionsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return FragmentDiary.newInstance(position);
            case 2:
                return FragmentExpense.newInstance(position);
            default:
                return FragmentPlan.newInstance(position);
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 1:
                return mContext.getString(FragmentDiary.TITLE_ID);
            case 2:
                return mContext.getString(FragmentExpense.TITLE_ID);
            default:
                return mContext.getString(FragmentPlan.TITLE_ID);
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    // Register the fragment when the item is instantiated
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    // Unregister when the item is inactive
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    // Returns the fragment for the position (if instantiated)
    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }

}
