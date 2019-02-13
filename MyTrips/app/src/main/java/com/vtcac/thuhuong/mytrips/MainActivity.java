package com.vtcac.thuhuong.mytrips;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.vtcac.thuhuong.mytrips.adapter.TravelListAdapter;
import com.vtcac.thuhuong.mytrips.base.BaseActivity;
import com.vtcac.thuhuong.mytrips.base.ListItemClickListener;
import com.vtcac.thuhuong.mytrips.base.MyApplication;
import com.vtcac.thuhuong.mytrips.base.ViewItemListClickListener;
import com.vtcac.thuhuong.mytrips.entity.Travel;
import com.vtcac.thuhuong.mytrips.entity.TravelBaseEntity;
import com.vtcac.thuhuong.mytrips.model.TravelViewModel;
import com.vtcac.thuhuong.mytrips.traveldetail.TravelDetailActivity;
import com.vtcac.thuhuong.mytrips.utils.MyConst;
import com.vtcac.thuhuong.mytrips.utils.MyString;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends BaseActivity implements View.OnClickListener
        , ListItemClickListener, ViewItemListClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private RelativeLayout rlIntroduce;
    private ImageView ivSearch;
    private ImageView ivMoreSort;
    private int sortOption = 0;
    private Travel travelItem;
    FloatingActionButton fab;

    private TravelListAdapter travelsAdapter;
    private TravelViewModel travelsViewModel;
    private final Observer<List<Travel>> travelsObserver = new Observer<List<Travel>>() {
        @Override
        public void onChanged(List<Travel> travels) {
            Log.d(TAG, "onChanged: travels.size=" + travels.size());
            travelsAdapter.setTravelList(travels);
            if (travels.size() > 0) {
                ((MyApplication) getApplication()).setTravelListSize(travels.size());
                rlIntroduce.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.tbToolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, EditTravelActivity.class)
                                .putExtra(MyConst.AC_TRAVEL, MyConst.AC_ADD_TRAVEL)
                        , MyConst.REQCD_ADD_TRAVEL);
            }
        });
        rlIntroduce = findViewById(R.id.rlMainTravelIntroduce);
        ivSearch = findViewById(R.id.ivSearch);
        // search
        ivSearch.setOnClickListener(this);

        // sort list of travel
        ivMoreSort = findViewById(R.id.ivMoreSort);
        ivMoreSort.setOnClickListener(this);

        travelsAdapter = new TravelListAdapter(this);
        travelsAdapter.setListItemClickListener(this);
        travelsAdapter.setViewItemListClickListener(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(travelsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        travelsViewModel = ViewModelProviders.of(this).get(TravelViewModel.class);
        travelsViewModel.getAllTravelsSorted(((MyApplication) getApplication()).getTravelSortOption())
                .observe(this, travelsObserver);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: requestCode=" + requestCode);
        Log.d(TAG, "onActivityResult: resultCode=" + resultCode);
        Log.d(TAG, "onActivityResult: data=" + data);
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case MyConst.REQCD_IMAGE_GALLERY:
                if(MyString.isEmpty(getImgPath())) return;
                Log.d(TAG, "onActivityResult: img-path=" + getImgPath());
                travelItem.setImgUri(getImgPath());
                travelsViewModel.updateTravel(travelItem);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivSearch:
                startActivityForResult(new Intent(this, SearchableActivity.class)
                        , MyConst.REQCD_SEARCH_TRAVELS_BY_CITY);
                break;
            case R.id.ivMoreSort:
                onCreateDialog();
                break;
        }
    }

    /**
     * show dialog sorting options
     *
     * @return
     */
    private void onCreateDialog() {
        int checkedItem = ((MyApplication) getApplication()).getTravelSortOption();
        Log.d(TAG, "onCreateDialog: checked-sort-option=" + checkedItem);
        LayoutInflater inflater = getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCustomTitle(inflater.inflate(R.layout.dialog_title_sort_travel, null))
                .setSingleChoiceItems(R.array.sort_options, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sortOption = which;
                        Log.d(TAG, "onClick: which=" + sortOption);
                    }
                })
                .setPositiveButton("ACCEPT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((MyApplication) getApplication()).setOptionSort(sortOption);
                        travelsViewModel.getAllTravelsSorted(sortOption).observe(MainActivity.this
                                , travelsObserver);
                        Log.d(TAG, "onClick: which_button=" + which);
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
        final Button positiveBtn = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        positiveBtn.setBackgroundColor(getResources().getColor(android.R.color.white));
        positiveBtn.setTextColor(getResources().getColor(R.color.colorSecondary));
    }

    @Override
    public void onListItemClick(View v, int position, TravelBaseEntity entity) {
        Travel item = (Travel) entity;
        Log.d(TAG, "onListItemClick: item id=" + item.getId());
        // call TravelDetailActivity
        Intent intent = new Intent(MainActivity.this, TravelDetailActivity.class);
        intent.putExtra(MyConst.REQKEY_TRAVEL_ID, item.getId());
        Log.d(TAG, "onListItemClick: item.id="+item.getId());
        startActivity(intent);
        return;
    }

    @Override
    public void onMoreVertMenuItemClick(int viewId, int position, final TravelBaseEntity entity) {
        switch (viewId) {
            case R.id.mniEdit:
                startActivityForResult(new Intent(this, EditTravelActivity.class)
                                .putExtra(MyConst.AC_TRAVEL, MyConst.AC_EDIT_TRAVEL)
                                .putExtra(MyConst.OBJ_TRAVEL, entity)
                        , MyConst.REQCD_EDIT_TRAVEL);
                break;
            case R.id.mniDelete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.title_dialog_delete_item)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                travelsViewModel.deleteTravel((Travel) entity);
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
    public void onViewItemListClick(View v, int position, TravelBaseEntity entity) {
        if(entity == null) return;
        switch (v.getId()) {
            case R.id.ivTravelImg:
                travelItem = (Travel) entity;
                requestPermissions(MyConst.REQCD_ACCESS_GALLERY);
                break;
        }
    }

    @Override
    protected void postRequestPermissionsResult(int reqCd, boolean result) {
        super.postRequestPermissionsResult(reqCd, result);
        if (!result) {
            Snackbar.make(fab, R.string.permission_not_granted, Snackbar.LENGTH_LONG).show();
            return;
        }
        switch (reqCd) {
            case MyConst.REQCD_ACCESS_GALLERY:
                takePhotoFromGallery();
                break;
        }
    }
}
