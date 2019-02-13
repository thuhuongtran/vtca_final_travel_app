package com.vtcac.thuhuong.mytrips.traveldetail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.vtcac.thuhuong.mytrips.MainActivity;
import com.vtcac.thuhuong.mytrips.R;
import com.vtcac.thuhuong.mytrips.base.BaseActivity;
import com.vtcac.thuhuong.mytrips.entity.Travel;
import com.vtcac.thuhuong.mytrips.utils.MyConst;
import com.vtcac.thuhuong.mytrips.utils.MyImage;
import com.vtcac.thuhuong.mytrips.utils.MyString;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

public class TravelDetailActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = TravelDetailActivity.class.getSimpleName();

    private SectionsPagerAdapter sectionsPagerAdapter;
    private Intent receivedIntent;
    private TravelDetailViewModel travelDetailViewModel;
    private Travel mTravel;
    // view
    private TextView tvTravelDetailTitle;
    private ImageView ivMoreVertChangeCover;
    private ImageView ivBack;
    private ViewPager viewPager;
    private FloatingActionButton fab;
    private TextView tvTravelDetailDate;
    private ImageView ivTravelDetailCover;

    private final Observer<Travel> travelObserver = new Observer<Travel>() {
        @Override
        public void onChanged(Travel travel) {
            Log.d(TAG, "onChanged: travel=" + travel);
            if (travel == null) return;
            Log.d(TAG, "onCreate: traveldetailviewmodel=" + travel.getPlaceName()
                    + " title=" + travel.getTitle() + " date=" + travel.getStartDt());
            mTravel = travel;
            tvTravelDetailTitle.setText(travel.getPlaceName());
            tvTravelDetailDate.setText(travel.getStartDt());
            if (MyString.isEmpty(travel.getImgUri())) {
                ivTravelDetailCover.setImageResource(MyImage.getDefaultImgID(MyImage.getRandomNumber()));
            } else {
                ivTravelDetailCover.setImageURI(Uri.parse(travel.getImgUri()));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_detail);
        TextView tvCity = findViewById(R.id.tvTravelCity);
        tvCity.setText("city name");

        // view pager
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this);
        viewPager = findViewById(R.id.vp);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        // init view get by id
        ivMoreVertChangeCover = findViewById(R.id.ivChangeCover);
        ivBack = findViewById(R.id.ivBack);
        fab = findViewById(R.id.fab);
        tvTravelDetailTitle = findViewById(R.id.tvTravelCity);
        tvTravelDetailDate = findViewById(R.id.tvTravelDate);
        ivTravelDetailCover = findViewById(R.id.ivTravelImg);


        // set onlick
        ivMoreVertChangeCover.setOnClickListener(this);
        ivBack.setOnClickListener(this);

        // get received Intent
        receivedIntent = getIntent();
        final long travelId = receivedIntent.getLongExtra(MyConst.REQKEY_TRAVEL_ID, 0);
        Log.d(TAG, "onCreate: travel.id=" + travelId);

        travelDetailViewModel = ViewModelProviders.of(this).get(TravelDetailViewModel.class);
        travelDetailViewModel.setTravelId(travelId);
        travelDetailViewModel.getTravelById().observe(this, travelObserver);

        // floating action button
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TravelDetailBaseFragment fragment = (TravelDetailBaseFragment) sectionsPagerAdapter
                        .getRegisteredFragment(viewPager.getCurrentItem());
                fragment.requestAddItem(mTravel);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case R.id.ivChangeCover:
                PopupMenu popupMenu = new PopupMenu(v.getContext(), ivMoreVertChangeCover);
                popupMenu.getMenuInflater().inflate(R.menu.menu_change_cover_travel_detail, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.mniTakePhoto:
                                requestPermissions(MyConst.REQCD_ACCESS_CAMERA);
                                break;
                            case R.id.mniChoosePhoto:
                                requestPermissions(MyConst.REQCD_ACCESS_GALLERY);
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
                break;
        }
    }
    @Override
    protected void postRequestPermissionsResult(int reqCd, boolean result) {
        if (!result) {
            Snackbar.make(fab, R.string.permission_not_granted, Snackbar.LENGTH_LONG).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TravelDetailBaseFragment fragment = (TravelDetailBaseFragment) sectionsPagerAdapter
                .getRegisteredFragment(viewPager.getCurrentItem());
        fragment.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) return;
        Log.d(TAG, "onActivityResult: requestCode=" + requestCode);
        switch (requestCode) {
            case MyConst.REQCD_IMAGE_GALLERY:
            case MyConst.REQCD_IMAGE_CAMERA:
                if (MyString.isEmpty(getImgPath())) return;
                Log.d(TAG, "onActivityResult: img-path=" + getImgPath());
                mTravel.setImgUri(getImgPath());
                travelDetailViewModel.updateTravel(mTravel);
                ivTravelDetailCover.setImageURI(Uri.parse(getImgPath()));
                break;
        }
    }
}
