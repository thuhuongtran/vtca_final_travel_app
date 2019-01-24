package com.vtcac.thuhuong.mytrips.traveldetail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.vtcac.thuhuong.mytrips.MainActivity;
import com.vtcac.thuhuong.mytrips.R;
import com.vtcac.thuhuong.mytrips.base.BaseActivity;
import com.vtcac.thuhuong.mytrips.entity.Travel;
import com.vtcac.thuhuong.mytrips.utils.MyConst;
import com.vtcac.thuhuong.mytrips.utils.MyImage;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

public class TravelDetailActivity extends BaseActivity implements View.OnClickListener{
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
            Log.d(TAG, "onCreate: traveldetailviewmodel="+travel.getPlaceName()
                    +" title="+travel.getTitle()+" date="+travel.getStartDt());
            mTravel = travel;
            tvTravelDetailTitle.setText(travel.getPlaceName());
            tvTravelDetailDate.setText(travel.getStartDt());
            // todo check null image then set image from db
            ivTravelDetailCover.setImageResource(MyImage.getDefaultImgID(MyImage.getRandomNumber()));
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_detail);
        TextView tvCity = findViewById(R.id.tvTravelCity);
        tvCity.setText("city name");

        // view pager
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),this);
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
        Log.d(TAG, "onCreate: travel.id="+travelId);

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
                            case R.id.mniChangeCover:
                                // todo change image cover
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
                break;
        }
    }
}
