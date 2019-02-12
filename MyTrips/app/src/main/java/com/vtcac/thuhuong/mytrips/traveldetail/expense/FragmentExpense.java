package com.vtcac.thuhuong.mytrips.traveldetail.expense;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.vtcac.thuhuong.mytrips.R;
import com.vtcac.thuhuong.mytrips.adapter.ExpenseListAdapter;
import com.vtcac.thuhuong.mytrips.base.BaseActivity;
import com.vtcac.thuhuong.mytrips.base.ListItemClickListener;
import com.vtcac.thuhuong.mytrips.base.ViewItemListClickListener;
import com.vtcac.thuhuong.mytrips.entity.Expense;
import com.vtcac.thuhuong.mytrips.entity.Travel;
import com.vtcac.thuhuong.mytrips.entity.TravelBaseEntity;
import com.vtcac.thuhuong.mytrips.model.ExpenseViewModel;
import com.vtcac.thuhuong.mytrips.traveldetail.TravelDetailBaseFragment;
import com.vtcac.thuhuong.mytrips.utils.MyConst;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.app.Activity.RESULT_OK;

public class FragmentExpense extends TravelDetailBaseFragment implements ListItemClickListener,
        ViewItemListClickListener {
    private static final String TAG = FragmentExpense.class.getSimpleName();
    public static final int TITLE_ID = R.string.tab_expense;
    private ExpenseListAdapter expenseListAdapter;
    private ExpenseViewModel expenseViewModel;
    private static Expense expense;

    private final Observer<List<Expense>> expenseObserver = new Observer<List<Expense>>() {
        @Override
        public void onChanged(List<Expense> expenses) {
            expenseListAdapter.setExpenseList(expenses);
        }
    };

    public FragmentExpense() {
    }

    public static FragmentExpense newInstance(int sectionNumber) {
        Log.d(TAG, "newInstance: sectionNumber=" + sectionNumber);
        FragmentExpense fragment = new FragmentExpense();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);

        return fragment;
    }
    /**
     * add new expense
     * */
    @Override
    public void requestAddItem(Travel travel) {
        Log.d(TAG, "requestAddItem: travel=" + travel.toString());
        if (travel == null) return;
        startActivityForResult(new Intent(getContext(), EditExpenseActivity.class)
                .putExtra(MyConst.AC_EXPENSE, MyConst.AC_ADD_EXPENSE)
                .putExtra(MyConst.OBJ_TRAVEL, travel), MyConst.REQCD_ADD_EXPENSE);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        expenseListAdapter = new ExpenseListAdapter(getContext());
        expenseListAdapter.setListItemClickListener(this);
        expenseListAdapter.setViewItemListClickListener(this);
        expenseViewModel = ViewModelProviders.of(this).get(ExpenseViewModel.class);
        travelDetailViewModel.getExpenseList().observe(this, expenseObserver);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_expense, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.rvExpenses);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(expenseListAdapter);
        return rootView;
    }
    @Override
    public void onListItemClick(View v, int position, TravelBaseEntity entity) {
        Expense item = (Expense) entity;
        Intent intent = new Intent(getContext(), EditExpenseActivity.class);
        intent.putExtra(MyConst.OBJ_EXPENSE, item);
        intent.putExtra(MyConst.AC_EXPENSE, MyConst.AC_EDIT_EXPENSE);
        startActivityForResult(intent, MyConst.REQCD_EDIT_EXPENSE);
        return;
    }
    @Override
    public void onMoreVertMenuItemClick(int viewId, int position, final TravelBaseEntity entity) {
        // do nothing
    }

    @Override
    public void onViewItemListClick(View v, int position, final TravelBaseEntity entity) {
        switch (v.getId()) {
            case R.id.tvExpensePlace:
                this.expense = (Expense) entity;
                ((BaseActivity) getActivity()).showPlacePicker();
                break;
            case R.id.ivExpDel:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage(R.string.title_dialog_delete_item)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                expenseViewModel.delete((Expense) entity);
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // DO NOTHING
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
                // set positive button background
                final Button positiveBtn = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                positiveBtn.setBackgroundColor(getResources().getColor(android.R.color.white));
                positiveBtn.setTextColor(getResources().getColor(R.color.colorSecondary));
                final Button negativeBtn = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                // set negative button background
                negativeBtn.setBackgroundColor(getResources().getColor(android.R.color.white));
                negativeBtn.setTextColor(getResources().getColor(R.color.colorSecondary));
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case MyConst.REQCD_PLACE_PICKER: {
                Place place = PlacePicker.getPlace(getContext(), data);
                Log.d(TAG, "onActivityResult: place=" + place);
                this.expense.setPlaceId(place.getId());
                this.expense.setPlaceName(place.getName().toString());
                this.expense.setPlaceAddr(place.getAddress().toString());
                this.expense.setPlaceLat(place.getLatLng().latitude);
                this.expense.setPlaceLng(place.getLatLng().longitude);
                Log.d(TAG, "onActivityResult: after update expense=" + expense.toString());
                expenseViewModel.update(expense);
            }
            break;
        }
    }
}
