package com.vtcac.thuhuong.mytrips.traveldetail.expense;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vtcac.thuhuong.mytrips.R;
import com.vtcac.thuhuong.mytrips.base.ListItemClickListener;
import com.vtcac.thuhuong.mytrips.entity.Expense;
import com.vtcac.thuhuong.mytrips.entity.Travel;
import com.vtcac.thuhuong.mytrips.entity.TravelBaseEntity;
import com.vtcac.thuhuong.mytrips.traveldetail.TravelDetailBaseFragment;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BudgetStatusFragment extends TravelDetailBaseFragment implements ListItemClickListener {
    private static final String TAG = BudgetStatusFragment.class.getSimpleName();
    private BudgetStatusListAdapter mListAdapter;

    public BudgetStatusFragment() {
    }

    @Override
    public void requestAddItem(Travel travel) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListAdapter = new BudgetStatusListAdapter(getContext());
        mListAdapter.setListItemClickListener(this);
        travelDetailViewModel.getBudgetStatus().observe(this, new Observer<List<Expense>>() {
            @Override
            public void onChanged(List<Expense> items) {
                mListAdapter.setList(items);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_budget_status, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.budget_list);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mListAdapter);
        return rootView;
    }

    @Override
    public void onListItemClick(View v, int position, TravelBaseEntity entity) {
        Expense item = (Expense) entity;
        Log.d(TAG, "onListItemClick: item=" + item);
    }

    @Override
    public void onMoreVertMenuItemClick(int viewId, int position, TravelBaseEntity entity) {
// do nothing
    }
}
