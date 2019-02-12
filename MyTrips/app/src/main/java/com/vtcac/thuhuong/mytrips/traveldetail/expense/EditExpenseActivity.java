package com.vtcac.thuhuong.mytrips.traveldetail.expense;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.vtcac.thuhuong.mytrips.R;
import com.vtcac.thuhuong.mytrips.base.BaseActivity;
import com.vtcac.thuhuong.mytrips.entity.Expense;
import com.vtcac.thuhuong.mytrips.entity.Travel;
import com.vtcac.thuhuong.mytrips.model.ExpenseViewModel;
import com.vtcac.thuhuong.mytrips.utils.MyConst;
import com.vtcac.thuhuong.mytrips.utils.MyDate;
import com.vtcac.thuhuong.mytrips.utils.MyString;

import java.util.Calendar;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

public class EditExpenseActivity extends BaseActivity implements View.OnClickListener
        , DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private static final String TAG = EditExpenseActivity.class.getSimpleName();
    private TextInputEditText etExpenseTitle;
    private TextView tvExpenseDate;
    private TextView tvExpenseTime;
    private TextView tvExpPlace;
    private EditText etExpAmount;
    private Spinner spExpCurrency;
    private Spinner spExpType;
    private TextInputEditText etExpDesc;
    private Button btnAddEdit;

    private Intent receivedIntent;
    private Calendar calendar;
    private String expenseDate;
    private String expenseTime;
    private Expense expense;
    private Place place;
    private Travel travel;
    private ExpenseViewModel expenseViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense);
        Toolbar toolbar = findViewById(R.id.tbToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // get view by id
        etExpenseTitle = findViewById(R.id.etExpenseTitle);
        tvExpenseDate = findViewById(R.id.tvExpenseDate);
        tvExpenseTime = findViewById(R.id.tvExpenseTime);
        tvExpPlace = findViewById(R.id.tvExpPlace);
        etExpAmount = findViewById(R.id.etExpAmount);
        spExpCurrency = findViewById(R.id.spExpCurrency);
        spExpType = findViewById(R.id.spExpType);
        etExpDesc = findViewById(R.id.etExpDesc);
        btnAddEdit = findViewById(R.id.btnAddEdit);

        // init object
        expenseViewModel = ViewModelProviders.of(this).get(ExpenseViewModel.class);
        expense = new Expense();

        // get intent
        receivedIntent = getIntent();
        switch (receivedIntent.getStringExtra(MyConst.AC_EXPENSE)) {
            case MyConst.AC_ADD_EXPENSE:
                toolbar.setTitle(R.string.title_toolbar_add_expense);
                btnAddEdit.setText(R.string.button_txt_add);
                travel = (Travel) receivedIntent.getSerializableExtra(MyConst.OBJ_TRAVEL);
                if (travel == null) return;
                expense.setTravelId(travel.getId());
                calendar = Calendar.getInstance();
                expenseDate = MyDate.timestampToDate(calendar.getTimeInMillis()); // init plan date time
                expenseTime = MyDate.timestampToTime(calendar.getTimeInMillis());

                // set value on view - current date -current time - travel place name- currency-type
                tvExpenseDate.setText(expenseDate);
                tvExpenseTime.setText(expenseTime);
                tvExpPlace.setTextColor(getResources().getColor(R.color.colorSecondary));
                tvExpPlace.setText(travel.getPlaceName());
                expense.setType(getResources().getStringArray(R.array.expense_type_label)[0]);
                expense.setCurrency(getResources().getStringArray(R.array.currency_key)[0]);
                break;
            case MyConst.AC_EDIT_EXPENSE:
                toolbar.setTitle(R.string.title_toolbar_edit_expense);
                btnAddEdit.setText(R.string.button_txt_edit);
                // set value to view
                expense = (Expense) receivedIntent.getSerializableExtra(MyConst.OBJ_EXPENSE);
                if (expense == null) return;
                calendar = Calendar.getInstance();
                expenseDate = expense.getStartDt(); // init plan date time
                expenseTime = expense.getTime();
                if (!expense.getTitle().equals("") && expense.getTitle() != null)
                    etExpenseTitle.setText(expense.getTitle());
                //desc
                if (!expense.getDesc().equals("") && expense.getDesc() != null)
                    etExpDesc.setText(expense.getDesc());
                else etExpDesc.setText(expense.getTitle());
                //date
                if (!expense.getStartDt().equals("") && expense.getStartDt() != null) {
                    expenseDate = expense.getStartDt();
                    tvExpenseDate.setText(expense.getStartDt());
                }
                //time
                if (!expense.getTime().equals("") && expense.getTime() != null) {
                    expenseTime = expense.getTime();
                    tvExpenseTime.setText(expense.getTime());
                }
                // place
                if (!expense.getPlaceName().equals("") && expense.getPlaceName() != null) {
                    tvExpPlace.setTextColor(getResources().getColor(R.color.colorSecondary));
                    tvExpPlace.setText(expense.getPlaceName());
                }
                // amount
                if (expense.getAmount() > 0) {
                    etExpAmount.setText(String.valueOf(expense.getAmount()));
                }
                // currency
                if (!MyString.isEmpty(expense.getCurrency())) {
                    spExpCurrency.setSelection(MyConst.getCurrencyCode(expense.getCurrency()).id);
                }
                // type
                if (!MyString.isEmpty(expense.getType())) {
                    spExpType.setSelection(MyConst.getExpTypeCode(expense.getType()).id);
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
            case R.id.tvExpenseDate: {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dpd = new DatePickerDialog(this, this
                        , calendar.get(Calendar.YEAR)
                        , calendar.get(Calendar.MONTH)
                        , calendar.get(Calendar.DAY_OF_MONTH));
                dpd.getDatePicker().setTag(v.getId());
                dpd.show();
            }
            break;
            case R.id.tvExpenseTime: {
                Calendar calendar = Calendar.getInstance();
                TimePickerDialog tpd = new TimePickerDialog(this, this
                        , calendar.get(Calendar.HOUR_OF_DAY)
                        , calendar.get(Calendar.MINUTE), true);
                tpd.show();
            }
            break;
            case R.id.tvExpensePlace:
                showPlacePicker();
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(year, month, dayOfMonth);
        expenseDate = MyDate.timestampToDate(calendar.getTimeInMillis());
        tvExpenseDate.setText(expenseDate);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        tvExpenseTime.setText(MyDate.timestampToTime(calendar.getTimeInMillis()));
        expenseTime = MyDate.timestampToDateTime(calendar.getTimeInMillis());
    }
    private void validate() {
        String title = etExpenseTitle.getText().toString();
        if (MyString.isEmpty(title)) {
            Snackbar.make(etExpenseTitle, R.string.warn_empty_exp_title, Snackbar.LENGTH_LONG).show();
            return;
        }
        expense.setTitle(etExpenseTitle.getText().toString());
        expense.setDesc(expense.getTitle());
        switch (receivedIntent.getStringExtra(MyConst.AC_EXPENSE)) {
            case MyConst.AC_ADD_EXPENSE: {
                if (!tvExpPlace.getText().toString().equals(travel.getPlaceName())) {
                    expense.setPlaceId(place.getId());
                    expense.setPlaceName((String) place.getName());
                    expense.setPlaceAddr((String) place.getAddress());
                    expense.setPlaceLat(place.getLatLng().latitude);
                    expense.setPlaceLng(place.getLatLng().longitude);
                } else {
                    expense.setPlaceId(travel.getPlaceId());
                    expense.setPlaceName(travel.getPlaceName());
                    expense.setPlaceAddr(travel.getPlaceAddr());
                    expense.setPlaceLat(travel.getPlaceLat());
                    expense.setPlaceLng(travel.getPlaceLng());
                }
                if (!travel.getStartDt().equals(expenseDate)) {
                    expense.setStartDt(expenseDate);
                }
                else{
                    expense.setStartDt(travel.getStartDt());
                }
                expense.setTime(expenseTime);
                if (!MyString.isEmpty(etExpDesc.getText().toString())) {
                    expense.setDesc(etExpDesc.getText().toString());
                }
                // set amount
                if (Double.parseDouble(etExpAmount.getText().toString())>0) {
                    expense.setAmount(Double.parseDouble(etExpAmount.getText().toString()));
                }
                // currency
                String editCurrency = getResources().getStringArray(R.array.currency_key)[spExpCurrency.getSelectedItemPosition()];
                if(!editCurrency.equals(expense.getCurrency())){
                    expense.setCurrency(editCurrency);
                    Log.d(TAG, "validate: currency="+expense.getCurrency());
                }
                // type
                String editType = getResources().getStringArray(R.array.expense_type_label)[spExpType.getSelectedItemPosition()];
                if(!editType.equals(expense.getType())){
                    expense.setType(editType);
                    Log.d(TAG, "validate: type="+expense.getType());
                }
                Log.d(TAG, "validate: expense="+expense.toString());
                expenseViewModel.insert(expense);
            }
            break;
            case MyConst.AC_EDIT_EXPENSE: {
                Log.d(TAG, "validate before update: expense=" + expense.getTitle() + " " + expense.getPlaceName() + " " + expense.getStartDt()
                        + " " + expense.getTime());
                if (!tvExpPlace.getText().toString().equals(expense.getPlaceName())&&place!=null) {
                    expense.setPlaceId(place.getId());
                    expense.setPlaceName((String) place.getName());
                    expense.setPlaceAddr((String) place.getAddress());
                    expense.setPlaceLat(place.getLatLng().latitude);
                    expense.setPlaceLng(place.getLatLng().longitude);
                }
                // date
                if (!expense.getStartDt().equals(expenseDate)) {
                    expense.setStartDt(expenseDate);
                }
                // time
                if (!expense.getTime().equals(expenseTime)) {
                    expense.setTime(expenseTime);
                }
                // desc
                if (!etExpDesc.getText().equals(expense.getDesc())||etExpDesc.getText().toString().trim()!="") {
                    expense.setDesc(etExpDesc.getText().toString());
                }
                // amount
                if (!etExpAmount.getText().equals(expense.getAmount())||etExpAmount.getText().toString().trim()!="") {
                    expense.setAmount(Double.parseDouble(etExpAmount.getText().toString()));
                }
                // currency
                String editCurrency = getResources().getStringArray(R.array.currency_key)[spExpCurrency.getSelectedItemPosition()];
                if(!editCurrency.equals(expense.getCurrency())){
                    expense.setCurrency(editCurrency);
                    Log.d(TAG, "validate: currency="+expense.getCurrency());
                }
                // type
                String editType = getResources().getStringArray(R.array.expense_type_label)[spExpType.getSelectedItemPosition()];
                if(!spExpType.getSelectedItem().toString().equals(expense.getType())){
                    expense.setType(editType);
                    Log.d(TAG, "validate: type="+expense.getType());
                }
                expenseViewModel.update(expense);
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
                expense.setPlaceId(place.getId());
                expense.setPlaceName(place.getName().toString());
                expense.setPlaceAddr(place.getAddress().toString());
                expense.setPlaceLat(place.getLatLng().latitude);
                expense.setPlaceLng(place.getLatLng().longitude);
                tvExpPlace.setText(place.getName());
                tvExpPlace.setTextColor(getResources().getColor(R.color.colorSecondary));
            }
            break;
        }
    }
}
