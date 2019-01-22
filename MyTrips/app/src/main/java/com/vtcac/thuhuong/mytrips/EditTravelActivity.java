package com.vtcac.thuhuong.mytrips;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.vtcac.thuhuong.mytrips.base.BaseActivity;
import com.vtcac.thuhuong.mytrips.entity.Travel;
import com.vtcac.thuhuong.mytrips.model.TravelViewModel;
import com.vtcac.thuhuong.mytrips.utils.MyConst;
import com.vtcac.thuhuong.mytrips.utils.MyDate;
import com.vtcac.thuhuong.mytrips.utils.MyString;

import java.util.Calendar;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

public class EditTravelActivity extends BaseActivity implements View.OnClickListener
        , DatePickerDialog.OnDateSetListener {
    private final static String TAG = EditTravelActivity.class.getSimpleName();
    private TextInputEditText travelTitle;
    private TextView travelPlace;
    private TextView travelStartDt;
    private long startTimestmp;
    private long endTimestmp;
    private Place place;
    private TextView travelEndDt;
    private Button btnAdd;
    private Travel travel;
    private TravelViewModel travelViewModel;
    private Intent receivedIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_travel);
        Toolbar toolbar = findViewById(R.id.tbToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        travelTitle = findViewById(R.id.etTravelTitle);
        travelPlace = findViewById(R.id.tvPlace);
        travelStartDt = findViewById(R.id.tvStartDt);
        travelEndDt = findViewById(R.id.tvEndDt);
        btnAdd = findViewById(R.id.btnAddEdit);
        // get type of action - add or edit
        receivedIntent = getIntent();
        switch (receivedIntent.getStringExtra(MyConst.AC_TRAVEL)) {
            case MyConst.AC_ADD_TRAVEL:
                toolbar.setTitle(R.string.title_toolbar_add_travel);
                btnAdd.setText(R.string.button_txt_add);
                break;
            case MyConst.AC_EDIT_TRAVEL:
                toolbar.setTitle(R.string.title_toolbar_edit_travel);
                btnAdd.setText(R.string.button_txt_edit);
                // set value to view
                travel = (Travel) receivedIntent.getSerializableExtra(MyConst.OBJ_TRAVEL);
                Log.d(TAG, "onCreate: travel = name:"+travel.getPlaceName());
                if (travel == null) return;

                if (!travel.getStartDt().equals("") && travel.getStartDt() != null)
                    travelStartDt.setText(travel.getStartDt());
                Log.d(TAG, "onCreate: travel-start="+travel.getStartDt());

                if (!travel.getEndDt().equals("") && travel.getEndDt() != null)
                    travelEndDt.setText(travel.getEndDt());
                Log.d(TAG, "onCreate: travel-end="+travel.getEndDt());

                if (!travel.getPlaceName().equals("") && travel.getPlaceName() != null) {
                    travelPlace.setTextColor(getResources().getColor(R.color.colorSecondary));
                    travelPlace.setText(travel.getPlaceName());
                }

                if (!travel.getTitle().equals("") && travel.getTitle() != null)
                    travelTitle.setText(travel.getTitle());
                Log.d(TAG, "onCreate: travel-title="+travel.getTitle());

                break;
        }
        travelPlace.setOnClickListener(this);
        travelStartDt.setOnClickListener(this);
        travelEndDt.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        travelViewModel = ViewModelProviders.of(this).get(TravelViewModel.class);

        // set init value
        startTimestmp = System.currentTimeMillis();
        travelStartDt.setText(MyDate.timestampToDate(System.currentTimeMillis()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddEdit:
                validate();
                break;
            case R.id.tvPlace:
                showPlaceAutocomplete();
                break;
            case R.id.tvStartDt: {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dpd = new DatePickerDialog(this, this
                        , calendar.get(Calendar.YEAR)
                        , calendar.get(Calendar.MONTH)
                        , calendar.get(Calendar.DAY_OF_MONTH));
                dpd.getDatePicker().setTag(v.getId());
                if (endTimestmp > 0) {
                    dpd.getDatePicker().setMaxDate(endTimestmp);
                }
                dpd.show();
            }
            break;
            case R.id.tvEndDt: {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dpd = new DatePickerDialog(this, this
                        , calendar.get(Calendar.YEAR)
                        , calendar.get(Calendar.MONTH)
                        , calendar.get(Calendar.DAY_OF_MONTH));
                dpd.getDatePicker().setTag(v.getId());
                if (startTimestmp > 0) {
                    dpd.getDatePicker().setMinDate(startTimestmp);
                }
                dpd.show();
            }
            break;
        }
    }

    private void validate() {
        String title = travelTitle.getText().toString();
        if (MyString.isEmpty(title)) {
            Snackbar.make(travelTitle, R.string.warn_empty_travel_title, Snackbar.LENGTH_LONG).show();
            return;
        }
        switch (receivedIntent.getStringExtra(MyConst.AC_TRAVEL)) {
            case MyConst.AC_ADD_TRAVEL: {
                if (MyString.isEmpty(travelPlace.getText().toString())) {
                    Snackbar.make(travelTitle, R.string.warn_empty_travel_place, Snackbar.LENGTH_LONG).show();
                    return;
                }
                if (endTimestmp == 0) {
                    Snackbar.make(travelTitle, R.string.warn_empty_end_date, Snackbar.LENGTH_LONG).show();
                    return;
                }
                // add a new travel
                if (travel == null) {
                    travel = new Travel(title);
                } else {
                    travel.setTitle(title);
                }
                if (travelPlace != null) {
                    travel.setPlaceId(place.getId());
                    travel.setPlaceName((String) place.getName());
                    travel.setPlaceAddr((String) place.getAddress());
                    travel.setPlaceLat(place.getLatLng().latitude);
                    travel.setPlaceLng(place.getLatLng().longitude);
                }
                travel.setStartDt(MyDate.timestampToDate(startTimestmp));
                travel.setEndDt(MyDate.timestampToDate(endTimestmp));
                travelViewModel.insertTravel(travel);
            }
            break;
            case MyConst.AC_EDIT_TRAVEL: {
                Log.d(TAG, "validate before update: travel="+travel.getTitle()+" "+travel.getPlaceName()+" "+travel.getStartDt()
                        +" "+travel.getEndDt());
                travel.setTitle(travelTitle.getText().toString());
                if (!travelPlace.getText().toString().equals(travel.getPlaceName())) {
                    travel.setPlaceId(place.getId());
                    travel.setPlaceName((String) place.getName());
                    travel.setPlaceAddr((String) place.getAddress());
                    travel.setPlaceLat(place.getLatLng().latitude);
                    travel.setPlaceLng(place.getLatLng().longitude);
                }
                if (!travel.getStartDt().equals(MyDate.timestampToDate(startTimestmp))) {
                    travel.setStartDt(MyDate.timestampToDate(startTimestmp));
                }
                if (!travel.getEndDt().equals(MyDate.timestampToDate(endTimestmp))) {
                    travel.setEndDt(MyDate.timestampToDate(endTimestmp));
                }
                Log.d(TAG, "validate after update: travel="+travel.getTitle()+" "+travel.getPlaceName()+" "+travel.getStartDt()
                +" "+travel.getEndDt());
                travelViewModel.updateTravel(travel);
            }
            break;
        }
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Object tag = view.getTag();
        Calendar calendar = Calendar.getInstance();
        if (tag.equals(R.id.tvStartDt)) {
            calendar.set(year, month, dayOfMonth, 0, 0, 0);
            if (endTimestmp > 0 && endTimestmp < calendar.getTimeInMillis()) return;
            startTimestmp = calendar.getTimeInMillis();
            travelStartDt.setText(MyDate.timestampToDate(startTimestmp));
        } else {
            calendar.set(year, month, dayOfMonth, 23, 59, 59);
            if (startTimestmp > 0 && startTimestmp > calendar.getTimeInMillis()) return;
            endTimestmp = calendar.getTimeInMillis();
            travelEndDt.setText(MyDate.timestampToDate(endTimestmp));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case MyConst.REQCD_PLACE_AUTOCOMPLETE: {
                place = PlaceAutocomplete.getPlace(this, data);
                Log.i(TAG, "onActivityResult: place=" + place);
                travelPlace.setText(place.getName());
                travelPlace.setTextColor(getResources().getColor(R.color.colorSecondary));
            }
            break;
        }
    }
}
