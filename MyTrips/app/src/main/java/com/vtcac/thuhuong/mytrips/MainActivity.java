package com.vtcac.thuhuong.mytrips;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vtcac.thuhuong.mytrips.adapter.TravelListAdapter;
import com.vtcac.thuhuong.mytrips.base.BaseActivity;
import com.vtcac.thuhuong.mytrips.entity.Travel;
import com.vtcac.thuhuong.mytrips.model.TravelViewModel;
import com.vtcac.thuhuong.mytrips.utils.MyConst;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private RelativeLayout rlIntroduce;
    private TravelListAdapter travelsAdapter;
    private TravelViewModel travelsViewModel;
    private final Observer<List<Travel>> travelsObserver = new Observer<List<Travel>>() {
        @Override
        public void onChanged(List<Travel> travels) {
            Log.d(TAG, "onChanged: travels.size=" + travels.size());
            travelsAdapter.setTravelList(travels);
            if (travels.size() > 0) {
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

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, EditTravelActivity.class)
                ,MyConst.REQ_EDIT_TRAVEL);
            }
        });
        rlIntroduce = findViewById(R.id.rlMainTravelIntroduce);

        travelsAdapter = new TravelListAdapter(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(travelsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        travelsViewModel = ViewModelProviders.of(this).get(TravelViewModel.class);
        travelsViewModel.getAllTravelsByStartDesc().observe(this, travelsObserver);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: requestCode=" + requestCode);
        Log.d(TAG, "onActivityResult: resultCode=" + resultCode);
        Log.d(TAG, "onActivityResult: data=" + data);
        if(resultCode!=RESULT_OK) return;
        switch (requestCode){

        }
    }
}
