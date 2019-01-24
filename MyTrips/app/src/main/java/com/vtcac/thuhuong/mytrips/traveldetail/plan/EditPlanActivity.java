package com.vtcac.thuhuong.mytrips.traveldetail.plan;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.vtcac.thuhuong.mytrips.R;
import com.vtcac.thuhuong.mytrips.base.BaseActivity;
import com.vtcac.thuhuong.mytrips.entity.Plan;
import com.vtcac.thuhuong.mytrips.entity.Travel;
import com.vtcac.thuhuong.mytrips.model.PlanViewModel;
import com.vtcac.thuhuong.mytrips.utils.MyConst;
import com.vtcac.thuhuong.mytrips.utils.MyDate;
import com.vtcac.thuhuong.mytrips.utils.MyString;

import java.util.Calendar;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

public class EditPlanActivity extends BaseActivity implements View.OnClickListener
        , DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private static final String TAG = EditPlanActivity.class.getSimpleName();
    private TextInputEditText etPlanTitle;
    private TextInputEditText etPlanDesc;
    private TextView tvPlanDate;
    private TextView tvPlanTime;
    private TextView tvPlanPlace;
    private Button btnAdd;

    private Intent receivedIntent;
    private Calendar calendar;
    private String planDate;
    private String planTime;
    private Plan plan;
    private Place place;
    private Travel travel;
    private PlanViewModel planViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_plan);
        Toolbar toolbar = findViewById(R.id.tbToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // get view by id
        etPlanTitle = findViewById(R.id.etPlanTitle);
        tvPlanDate = findViewById(R.id.tvPlanDate);
        etPlanDesc = findViewById(R.id.etPlanDesc);
        tvPlanTime = findViewById(R.id.tvPlanTime);
        tvPlanPlace = findViewById(R.id.tvPlanPlace);
        btnAdd = findViewById(R.id.btnAddEdit);

        // init object
        planViewModel = ViewModelProviders.of(this).get(PlanViewModel.class);
        plan = new Plan();

        // set onlick
        tvPlanDate.setOnClickListener(this);
        tvPlanTime.setOnClickListener(this);
        tvPlanPlace.setOnClickListener(this);
        btnAdd.setOnClickListener(this);

        // get intent
        receivedIntent = getIntent();
        switch (receivedIntent.getStringExtra(MyConst.AC_PLAN)) {
            case MyConst.AC_ADD_PLAN:
                toolbar.setTitle(R.string.title_toolbar_add_plan);
                btnAdd.setText(R.string.button_txt_add);
                travel = (Travel) receivedIntent.getSerializableExtra(MyConst.OBJ_TRAVEL);
                if (travel == null) return;
                plan.setTravelId(travel.getId());
                calendar = Calendar.getInstance();
                planDate = MyDate.timestampToDate(calendar.getTimeInMillis()); // init plan date time
                planTime = MyDate.timestampToTime(calendar.getTimeInMillis());

                // set value on view - current date -current time - travel place name
                tvPlanDate.setText(planDate);
                tvPlanTime.setText(planTime);
                tvPlanPlace.setTextColor(getResources().getColor(R.color.colorSecondary));
                tvPlanPlace.setText(travel.getPlaceName());
                break;
            case MyConst.AC_EDIT_PLAN:
                toolbar.setTitle(R.string.title_toolbar_edit_plan);
                btnAdd.setText(R.string.button_txt_edit);
                // set value to view
                plan = (Plan) receivedIntent.getSerializableExtra(MyConst.OBJ_PLAN);
                if (plan == null) return;
                calendar = Calendar.getInstance();
                planDate = plan.getStartDt(); // init plan date time
                planTime = plan.getTime();
                if (!plan.getTitle().equals("") && plan.getTitle() != null)
                    etPlanTitle.setText(plan.getTitle());
                //desc
                if (!plan.getDesc().equals("") && plan.getDesc() != null)
                    etPlanDesc.setText(plan.getDesc());
                else etPlanTitle.setText(plan.getTitle());
                //date
                if (!plan.getStartDt().equals("") && plan.getStartDt() != null) {
                    planDate = plan.getStartDt();
                    tvPlanDate.setText(plan.getStartDt());
                }
                //time
                if (!plan.getTime().equals("") && plan.getTime() != null) {
                    planTime = plan.getTime();
                    tvPlanTime.setText(plan.getTime());
                }
                // place
                if (!plan.getPlaceName().equals("") && plan.getPlaceName() != null) {
                    tvPlanPlace.setTextColor(getResources().getColor(R.color.colorSecondary));
                    tvPlanPlace.setText(plan.getPlaceName());
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddEdit:
                validate();
                break;
            case R.id.tvPlanDate: {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dpd = new DatePickerDialog(this, this
                        , calendar.get(Calendar.YEAR)
                        , calendar.get(Calendar.MONTH)
                        , calendar.get(Calendar.DAY_OF_MONTH));
                dpd.getDatePicker().setTag(v.getId());
                dpd.show();
            }
            break;
            case R.id.tvPlanTime: {
                Calendar calendar = Calendar.getInstance();
                TimePickerDialog tpd = new TimePickerDialog(this, this
                        , calendar.get(Calendar.HOUR_OF_DAY)
                        , calendar.get(Calendar.MINUTE), true);
                tpd.show();
            }
            break;
            case R.id.tvPlanPlace:
                showPlacePicker();
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(year, month, dayOfMonth);
        planDate = MyDate.timestampToDate(calendar.getTimeInMillis());
        tvPlanDate.setText(planDate);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        tvPlanTime.setText(MyDate.timestampToTime(calendar.getTimeInMillis()));
        planTime = MyDate.timestampToDateTime(calendar.getTimeInMillis());
    }

    private void validate() {
        String title = etPlanTitle.getText().toString();
        if (MyString.isEmpty(title)) {
            Snackbar.make(etPlanTitle, R.string.warn_empty_plan_title, Snackbar.LENGTH_LONG).show();
            return;
        }
        plan.setTitle(etPlanTitle.getText().toString());
        plan.setDesc(plan.getTitle());
        switch (receivedIntent.getStringExtra(MyConst.AC_PLAN)) {
            case MyConst.AC_ADD_PLAN: {
                if (!tvPlanPlace.getText().toString().equals(travel.getPlaceName())) {
                    plan.setPlaceId(place.getId());
                    plan.setPlaceName((String) place.getName());
                    plan.setPlaceAddr((String) place.getAddress());
                    plan.setPlaceLat(place.getLatLng().latitude);
                    plan.setPlaceLng(place.getLatLng().longitude);
                } else {
                    plan.setPlaceId(travel.getPlaceId());
                    plan.setPlaceName(travel.getPlaceName());
                    plan.setPlaceAddr(travel.getPlaceAddr());
                    plan.setPlaceLat(travel.getPlaceLat());
                    plan.setPlaceLng(travel.getPlaceLng());
                }
                if (!travel.getStartDt().equals(planDate)) {
                    plan.setStartDt(planDate);
                }
                else{
                    plan.setStartDt(travel.getStartDt());
                }
                plan.setTime(planTime);
                if (!MyString.isEmpty(etPlanDesc.getText().toString())) {
                    plan.setDesc(etPlanDesc.getText().toString());
                }
                Log.d(TAG, "validate: plan="+plan.toString());
                planViewModel.insert(plan);
            }
            break;
            case MyConst.AC_EDIT_PLAN: {
                Log.d(TAG, "validate before update: plan=" + plan.getTitle() + " " + plan.getPlaceName() + " " + plan.getStartDt()
                        + " " + plan.getTime());
                if (!tvPlanPlace.getText().toString().equals(plan.getPlaceName())&&place!=null) {
                    plan.setPlaceId(place.getId());
                    plan.setPlaceName((String) place.getName());
                    plan.setPlaceAddr((String) place.getAddress());
                    plan.setPlaceLat(place.getLatLng().latitude);
                    plan.setPlaceLng(place.getLatLng().longitude);
                }
                if (!plan.getStartDt().equals(planDate)) {
                    plan.setStartDt(planDate);
                }
                if (!plan.getTime().equals(planTime)) {
                    plan.setTime(planTime);
                }

                if (!etPlanDesc.getText().equals(plan.getDesc())||etPlanDesc.getText().toString().trim()!="") {
                    plan.setDesc(etPlanDesc.getText().toString());
                }
                planViewModel.update(plan);
            }
            break;
        }
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case MyConst.REQCD_PLACE_PICKER: {
                place = PlacePicker.getPlace(this, data);
                Log.d(TAG, "onActivityResult: place=" + place);
                plan.setPlaceId(place.getId());
                plan.setPlaceName(place.getName().toString());
                plan.setPlaceAddr(place.getAddress().toString());
                plan.setPlaceLat(place.getLatLng().latitude);
                plan.setPlaceLng(place.getLatLng().longitude);
                tvPlanPlace.setText(place.getName());
                tvPlanPlace.setTextColor(getResources().getColor(R.color.colorSecondary));
            }
            break;
        }
    }
}
