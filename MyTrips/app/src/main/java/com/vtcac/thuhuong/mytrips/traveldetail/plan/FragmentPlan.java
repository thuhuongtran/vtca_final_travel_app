package com.vtcac.thuhuong.mytrips.traveldetail.plan;

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
import com.vtcac.thuhuong.mytrips.adapter.PlanListAdapter;
import com.vtcac.thuhuong.mytrips.base.BaseActivity;
import com.vtcac.thuhuong.mytrips.base.ListItemClickListener;
import com.vtcac.thuhuong.mytrips.base.ViewItemListClickListener;
import com.vtcac.thuhuong.mytrips.entity.Plan;
import com.vtcac.thuhuong.mytrips.entity.Travel;
import com.vtcac.thuhuong.mytrips.entity.TravelBaseEntity;
import com.vtcac.thuhuong.mytrips.model.PlanViewModel;
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

public class FragmentPlan extends TravelDetailBaseFragment implements ListItemClickListener,
        ViewItemListClickListener {
    private static final String TAG = FragmentPlan.class.getSimpleName();
    public static final int TITLE_ID = R.string.tab_plan;
    private PlanListAdapter planListAdapter;
    private PlanViewModel planViewModel;
    private static Plan plan;

    private final Observer<List<Plan>> planObserver = new Observer<List<Plan>>() {
        @Override
        public void onChanged(List<Plan> plans) {
            planListAdapter.setPlanList(plans);
        }
    };

    public FragmentPlan() {
    }

    public static FragmentPlan newInstance(int sectionNumber) {
        Log.d(TAG, "newInstance: sectionNumber=" + sectionNumber);
        FragmentPlan fragment = new FragmentPlan();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * add new plan
     *
     * @param travel
     */
    @Override
    public void requestAddItem(Travel travel) {
        Log.d(TAG, "requestAddItem: travel=" + travel.toString());
        if (travel == null) return;
        startActivityForResult(new Intent(getContext(), EditPlanActivity.class)
                .putExtra(MyConst.AC_PLAN, MyConst.AC_ADD_PLAN)
                .putExtra(MyConst.OBJ_TRAVEL, travel), MyConst.REQCD_ADD_PLAN);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        planListAdapter = new PlanListAdapter(getContext());
        planListAdapter.setListItemClickListener(this);
        planListAdapter.setViewItemListClickListener(this);
        planViewModel = ViewModelProviders.of(this).get(PlanViewModel.class);
        travelDetailViewModel.getPlanList().observe(this, planObserver);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_plan, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.rvPlans);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(planListAdapter);
        return rootView;
    }

    /**
     * list item click
     *
     * @param v
     * @param position
     * @param entity
     */
    @Override
    public void onListItemClick(View v, int position, TravelBaseEntity entity) {
        Plan item = (Plan) entity;
        Intent intent = new Intent(getContext(), EditPlanActivity.class);
        intent.putExtra(MyConst.OBJ_PLAN, item);
        intent.putExtra(MyConst.AC_PLAN, MyConst.AC_EDIT_PLAN);
        startActivityForResult(intent, MyConst.REQCD_EDIT_PLAN);
        return;
    }

    @Override
    public void onMoreVertMenuItemClick(int viewId, int position, TravelBaseEntity entity) {
        // do nothing
    }

    @Override
    public void onViewItemListClick(View v, int position, final TravelBaseEntity entity) {
        switch (v.getId()) {
            case R.id.tvPlanPlace:
                this.plan = (Plan) entity;
                ((BaseActivity) getActivity()).showPlacePicker();
                break;
            case R.id.ivDelPlan:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage(R.string.title_dialog_delete_item)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                planViewModel.delete((Plan) entity);
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
//        Log.d(TAG, "onActivityResult: before update plan=" + plan.toString());
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case MyConst.REQCD_PLACE_PICKER: {
                Place place = PlacePicker.getPlace(getContext(), data);
                Log.d(TAG, "onActivityResult: place=" + place);
                this.plan.setPlaceId(place.getId());
                this.plan.setPlaceName(place.getName().toString());
                this.plan.setPlaceAddr(place.getAddress().toString());
                this.plan.setPlaceLat(place.getLatLng().latitude);
                this.plan.setPlaceLng(place.getLatLng().longitude);
                Log.d(TAG, "onActivityResult: after update plan=" + plan.toString());
                planViewModel.update(plan);
            }
            break;
        }

    }
}
