package com.vtcac.thuhuong.mytrips.traveldetail.diary;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.material.snackbar.Snackbar;
import com.vtcac.thuhuong.mytrips.R;
import com.vtcac.thuhuong.mytrips.base.BaseActivity;
import com.vtcac.thuhuong.mytrips.entity.Diary;
import com.vtcac.thuhuong.mytrips.entity.Travel;
import com.vtcac.thuhuong.mytrips.model.DiaryViewModel;
import com.vtcac.thuhuong.mytrips.utils.MyConst;
import com.vtcac.thuhuong.mytrips.utils.MyDate;
import com.vtcac.thuhuong.mytrips.utils.MyImage;
import com.vtcac.thuhuong.mytrips.utils.MyString;

import java.util.Calendar;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

public class EditDiaryActivity extends BaseActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private final String TAG = EditDiaryActivity.class.getSimpleName();
    private TextView tvDiaryDate;
    private TextView tvDiaryTime;
    private TextView tvDiaryPlace;
    private ImageView ivBack;
    private ImageView ivChangeCover;
    private ImageView ivDiaryImg;
    private EditText etDiaryDesc;
    private Button btnEditDiary;
    private TextView toolbar;

    private Calendar calendar;
    private String diaryDate;
    private String diaryTime;
    private Place place;
    private Diary diary;
    private Travel travel;
    private DiaryViewModel diaryViewModel;
    private Intent receivedIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_diary);

        tvDiaryDate = findViewById(R.id.tvDiaryDate);
        tvDiaryTime = findViewById(R.id.tvDiaryTime);
        tvDiaryPlace = findViewById(R.id.tvDiaryPlace);
        ivBack = findViewById(R.id.ivBack);
        ivChangeCover = findViewById(R.id.ivChangeCover);
        etDiaryDesc = findViewById(R.id.etDiaryDesc);
        btnEditDiary = findViewById(R.id.btnEditDiary);
        toolbar = findViewById(R.id.tvCommonTitle);
        ivDiaryImg = findViewById(R.id.ivDiaryImg);

        // init object
        diaryViewModel = ViewModelProviders.of(this).get(DiaryViewModel.class);
        diary = new Diary();

        // get intent
        receivedIntent = getIntent();
        switch (receivedIntent.getStringExtra(MyConst.AC_DIARY)) {
            case MyConst.AC_ADD_DIARY:
                toolbar.setText(R.string.title_toolbar_add_diary);
                btnEditDiary.setText(R.string.button_txt_add);
                travel = (Travel) receivedIntent.getSerializableExtra(MyConst.OBJ_TRAVEL);
                if (travel == null) return;
                diary.setTravelId(travel.getId());
                setTravelId(diary.getTravelId()); // for BaseActivity
                calendar = Calendar.getInstance();
                diaryDate = MyDate.timestampToDate(calendar.getTimeInMillis()); // init plan date time
                diaryTime = MyDate.timestampToTime(calendar.getTimeInMillis());

                // set value on view - current date -current time - travel place name
                tvDiaryDate.setText(diaryDate);
                tvDiaryTime.setText(diaryTime);
                tvDiaryPlace.setText(travel.getPlaceName());
                ivDiaryImg.setImageResource(MyImage.getDefaultImgID(MyImage.getRandomNumber()));
                break;
            case MyConst.AC_EDIT_DIARY:
                toolbar.setText(R.string.title_toolbar_edit_plan);
                btnEditDiary.setText(R.string.button_txt_edit);
                // set value to view
                diary = (Diary) receivedIntent.getSerializableExtra(MyConst.OBJ_DIARY);
                if (diary == null) return;
                calendar = Calendar.getInstance();
                diaryDate = diary.getStartDt(); // init plan date time
                diaryTime = diary.getTime();
                //desc
                if (!diary.getDesc().equals("") && diary.getDesc() != null)
                    etDiaryDesc.setText(diary.getDesc());
                //date
                if (!diary.getStartDt().equals("") && diary.getStartDt() != null) {
                    diaryDate = diary.getStartDt();
                    tvDiaryDate.setText(diary.getStartDt());
                }
                //time
                if (!diary.getTime().equals("") && diary.getTime() != null) {
                    diaryTime = diary.getTime();
                    tvDiaryTime.setText(diary.getTime());
                }
                // place
                if (!diary.getPlaceName().equals("") && diary.getPlaceName() != null) {
                    tvDiaryPlace.setText(diary.getPlaceName());
                }
                // image
                if (!MyString.isEmpty(diary.getImgUri())) {
                    ivDiaryImg.setImageURI(Uri.parse(diary.getImgUri()));
                }
                break;
        }
    }

    private void validate() {
        switch (receivedIntent.getStringExtra(MyConst.AC_DIARY)) {
            case MyConst.AC_ADD_DIARY: {
                if (!tvDiaryPlace.getText().toString().equals(travel.getPlaceName())) {
                    diary.setPlaceId(place.getId());
                    diary.setPlaceName((String) place.getName());
                    diary.setPlaceAddr((String) place.getAddress());
                    diary.setPlaceLat(place.getLatLng().latitude);
                    diary.setPlaceLng(place.getLatLng().longitude);
                } else {
                    diary.setPlaceId(travel.getPlaceId());
                    diary.setPlaceName(travel.getPlaceName());
                    diary.setPlaceAddr(travel.getPlaceAddr());
                    diary.setPlaceLat(travel.getPlaceLat());
                    diary.setPlaceLng(travel.getPlaceLng());
                }
                if (!travel.getStartDt().equals(diaryDate)) {
                    diary.setStartDt(diaryDate);
                } else {
                    diary.setStartDt(travel.getStartDt());
                }
                diary.setTime(diaryTime);
                diary.setDesc("");
                if (!MyString.isEmpty(etDiaryDesc.getText().toString())) {
                    diary.setDesc(etDiaryDesc.getText().toString());
                }
                Log.d(TAG, "validate: diary=" + diary.toString());
                diaryViewModel.insert(diary);
            }
            break;
            case MyConst.AC_EDIT_DIARY: {
                Log.d(TAG, "validate before update: diary=" + diary.getTitle() + " " + diary.getPlaceName() + " " + diary.getStartDt()
                        + " " + diary.getTime());
                if (!tvDiaryPlace.getText().toString().equals(diary.getPlaceName()) && place != null) {
                    diary.setPlaceId(place.getId());
                    diary.setPlaceName((String) place.getName());
                    diary.setPlaceAddr((String) place.getAddress());
                    diary.setPlaceLat(place.getLatLng().latitude);
                    diary.setPlaceLng(place.getLatLng().longitude);
                }
                if (!diary.getStartDt().equals(diaryDate)) {
                    diary.setStartDt(diaryDate);
                }
                if (!diary.getTime().equals(diaryTime)) {
                    diary.setTime(diaryTime);
                }
                if (!etDiaryDesc.getText().equals(diary.getDesc()) || etDiaryDesc.getText().toString().trim() != "") {
                    diary.setDesc(etDiaryDesc.getText().toString());
                }
                diaryViewModel.update(diary);
            }
            break;
        }
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.ivChangeCover:
                PopupMenu pm = new PopupMenu(this, v);
                pm.getMenuInflater().inflate(R.menu.menu_change_cover_travel_detail, pm.getMenu());
                pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.mniChoosePhoto:
                                requestPermissions(MyConst.REQCD_ACCESS_GALLERY);
                                break;
                            case R.id.mniTakePhoto:
                                requestPermissions(MyConst.REQCD_ACCESS_CAMERA);
                                break;
                        }
                        return true;
                    }
                });
                pm.show();
                break;
            case R.id.tvDiaryDate: {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dpd = new DatePickerDialog(this, this
                        , calendar.get(Calendar.YEAR)
                        , calendar.get(Calendar.MONTH)
                        , calendar.get(Calendar.DAY_OF_MONTH));
                dpd.getDatePicker().setTag(v.getId());
                dpd.show();
            }
            break;
            case R.id.tvDiaryTime: {
                Calendar calendar = Calendar.getInstance();
                TimePickerDialog tpd = new TimePickerDialog(this, this
                        , calendar.get(Calendar.HOUR_OF_DAY)
                        , calendar.get(Calendar.MINUTE), true);
                tpd.show();
            }
            break;
            case R.id.tvDiaryPlace:
                showPlacePicker();
                break;
            case R.id.btnEditDiary:
                validate();
                break;
        }

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(year, month, dayOfMonth);
        diaryDate = MyDate.timestampToDate(calendar.getTimeInMillis());
        tvDiaryDate.setText(diaryDate);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        tvDiaryTime.setText(MyDate.timestampToTime(calendar.getTimeInMillis()));
        diaryTime = MyDate.timestampToTime(calendar.getTimeInMillis());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        Log.d(TAG, "onActivityResult: requestCode=" + requestCode);
        switch (requestCode) {
            case MyConst.REQCD_PLACE_PICKER: {
                place = PlacePicker.getPlace(this, data);
                Log.d(TAG, "onActivityResult: place=" + place);
                diary.setPlaceId(place.getId());
                diary.setPlaceName(place.getName().toString());
                diary.setPlaceAddr(place.getAddress().toString());
                diary.setPlaceLat(place.getLatLng().latitude);
                diary.setPlaceLng(place.getLatLng().longitude);
                tvDiaryPlace.setText(place.getName());
            }
            break;
            case MyConst.REQCD_IMAGE_GALLERY:
            case MyConst.REQCD_IMAGE_CAMERA:
                if(MyString.isEmpty(getImgPath())) return;
                Log.d(TAG, "onActivityResult: img-path=" + getImgPath());
                diary.setImgUri(getImgPath());
                ivDiaryImg.setImageURI(Uri.parse(getImgPath()));
                break;
        }
    }
    @Override
    protected void postRequestPermissionsResult(int reqCd, boolean result) {
        if (!result) {
            Snackbar.make(btnEditDiary, R.string.permission_not_granted, Snackbar.LENGTH_LONG).show();
            return;
        }
        switch (reqCd) {
            case MyConst.REQCD_ACCESS_CAMERA:
                takePhotoFromCamera();
                break;
            case MyConst.REQCD_ACCESS_GALLERY:
                takePhotoFromGallery();
                break;
        }
    }
}

