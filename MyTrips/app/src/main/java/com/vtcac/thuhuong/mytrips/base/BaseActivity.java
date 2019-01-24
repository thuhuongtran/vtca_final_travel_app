package com.vtcac.thuhuong.mytrips.base;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.material.snackbar.Snackbar;
import com.vtcac.thuhuong.mytrips.R;
import com.vtcac.thuhuong.mytrips.entity.TravelBaseEntity;
import com.vtcac.thuhuong.mytrips.utils.MyConst;
import com.vtcac.thuhuong.mytrips.utils.MyString;

import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    protected void showPlacePicker() {
        try {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            startActivityForResult(builder.build(this), MyConst.REQCD_PLACE_PICKER);
        } catch (GooglePlayServicesRepairableException e) {
            // Indicates that Google Play Services is either not installed
            // or not up to date.
            // Prompt the user to correct the issue.
            GoogleApiAvailability.getInstance().getErrorDialog(this,
                    e.getConnectionStatusCode(),
                    0 /* requestCode */).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            // Indicates that Google Play Services is not available
            // and the problem is not easily resolvable.
            String message = "Google Play Services is not available: " +
                    GoogleApiAvailability.getInstance().getErrorString(e.errorCode);
            Log.e(TAG, message, e);
            Snackbar.make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_LONG).show();
        }
    }
    /**
     * Launches Google Maps app
     *
     * @param v
     * @param item
     * @param query
     */
    public void openGoogleMap(View v, TravelBaseEntity item, String query) {
        final String appPackageName = "com.google.android.apps.maps";
        Uri gmmIntentUri;
        if (!MyString.isEmpty(query)) {
            gmmIntentUri = Uri.parse(String.format(Locale.ENGLISH, "geo:%f,%f?q=%s", item.getPlaceLat(), item.getPlaceLng(), Uri.encode(query)));
        } else {
            gmmIntentUri = Uri.parse(String.format(Locale.ENGLISH, "geo:0,0?q=%f,%f(%s)", item.getPlaceLat(), item.getPlaceLng(), Uri.encode(item.getPlaceName())));
        }
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage(appPackageName);
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            if (v != null) Snackbar.make(v, R.string.no_google_map, Snackbar.LENGTH_SHORT).show();
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        }
    }
    protected void showPlaceAutocomplete() {
        try {
            AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                    .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                    .build();
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                            .setFilter(typeFilter)
                            .build(this);
            startActivityForResult(intent, MyConst.REQCD_PLACE_AUTOCOMPLETE);
        } catch (GooglePlayServicesRepairableException e) {
            // Indicates that Google Play Services is either not installed
            // or not up to date.
            // Prompt the user to correct the issue.
            GoogleApiAvailability.getInstance().getErrorDialog(this,
                    e.getConnectionStatusCode(),
                    0 /* requestCode */).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            // Indicates that Google Play Services is not available
            // and the problem is not easily resolvable.
            String message = "Google Play Services is not available: " +
                    GoogleApiAvailability.getInstance().getErrorString(e.errorCode);
            Log.e(TAG, message, e);
            Snackbar.make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_LONG).show();
        }
    }
}
