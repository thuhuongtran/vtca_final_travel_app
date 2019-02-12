package com.vtcac.thuhuong.mytrips.base;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.material.snackbar.Snackbar;
import com.vtcac.thuhuong.mytrips.BuildConfig;
import com.vtcac.thuhuong.mytrips.R;
import com.vtcac.thuhuong.mytrips.entity.TravelBaseEntity;
import com.vtcac.thuhuong.mytrips.utils.ImageViewerDialog;
import com.vtcac.thuhuong.mytrips.utils.MyConst;
import com.vtcac.thuhuong.mytrips.utils.MyString;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentTransaction;

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();
    private String mCurrentImgPath;
    private long travelId;

    public String getImgPath() {
        return mCurrentImgPath;
    }

    public void setTravelId(long travelId) {
        this.travelId = travelId;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showPlacePicker() {
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

    // image
    private void createCurrentImgPath() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMG_" + timeStamp;
        File storageDir = new File(Environment.getExternalStorageDirectory(), "mytrips");
        if (!storageDir.exists()) storageDir.mkdirs();
        mCurrentImgPath = storageDir + "/" + imageFileName + ".jpg";
    }

    /**
     * Take a photo with a camera app
     */
    protected void takePhotoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        createCurrentImgPath();
        try {
            Log.d(TAG, "takePhotoFromCamera: mCurrentImgPath=" + mCurrentImgPath);
            File photoFile = new File(mCurrentImgPath);
            photoFile.createNewFile();
            // use a FileProvider
            Uri photoURI = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", photoFile);
            Log.d(TAG, "takePhotoFromCamera: photoURI=" + photoURI);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            if (intent.resolveActivity(getPackageManager()) == null) {
                Snackbar.make(getWindow().getDecorView().getRootView(), "Your device does not have a camera.", Snackbar.LENGTH_LONG).show();
                return;
            }
            // grant read/write permissions to other apps.
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            startActivityForResult(intent, MyConst.REQCD_IMAGE_CAMERA);
        } catch (IOException e) {
            // Error occurred while creating the File
            Log.e(TAG, e.getMessage(), e);
            Snackbar.make(getWindow().getDecorView().getRootView(), "Cannot create an image file.", Snackbar.LENGTH_LONG).show();
            return;
        }
    }

    /**
     * Take a photo with a gallery app
     */
    protected void takePhotoFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        if (intent.resolveActivity(getPackageManager()) == null) {
            Snackbar.make(getWindow().getDecorView().getRootView(), "Your device does not have a gallery.", Snackbar.LENGTH_LONG).show();
            return;
        }
        createCurrentImgPath();
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("return-data", true);
        startActivityForResult(intent, MyConst.REQCD_IMAGE_GALLERY);
    }

    private void copyFile(File sourceFile, File destFile) throws IOException {
        if (!sourceFile.exists()) {
            return;
        }
        FileChannel source = null;
        FileChannel destination = null;
        source = new FileInputStream(sourceFile).getChannel();
        destination = new FileOutputStream(destFile).getChannel();
        if (destination != null && source != null) {
            destination.transferFrom(source, 0, source.size());
        }
        if (source != null) {
            source.close();
        }
        if (destination != null) {
            destination.close();
        }
    }

    private String getRealPathFromURI(Uri contentUri) {

        String[] proj = {MediaStore.Video.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: requestCode=" + requestCode);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case MyConst.REQCD_IMAGE_GALLERY: {
                Log.d(TAG, "onActivityResult: getData=" + data.getData());
                if (data.getData() == null) return;
                File f = new File(mCurrentImgPath);
                if (!f.exists()) {
                    try {
                        f.createNewFile();
                        copyFile(new File(getRealPathFromURI(data.getData())), f);
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage(), e);
                        Snackbar.make(getWindow().getDecorView().getRootView(), "Failed to load an image.", Snackbar.LENGTH_LONG).show();
                    }
                }
            }
            break;
            case MyConst.REQCD_IMAGE_CAMERA: {
                Log.d(TAG, "onActivityResult: mCurrentImgPath="+mCurrentImgPath);
            }
            break;
        }

    }

    protected void postRequestPermissionsResult(final int reqCd, final boolean result) {
        Log.d(TAG, "postRequestPermissionsResult: reqCd=" + reqCd + ", result=" + result);
    }

    /**
     * Displays a common alert dialog with Ok and Cancel buttons.
     *
     * @param titleId             title string resource id
     * @param messageId           message string resource id
     * @param okClickListener     the callback when the ok button is clicked
     * @param cancelClickListener the callback when the cancel button is clicked
     */
    protected void showAlertOkCancel(@StringRes int titleId
            , @StringRes int messageId
            , final DialogInterface.OnClickListener okClickListener
            , final DialogInterface.OnClickListener cancelClickListener) {
        new AlertDialog.Builder(this)
                .setTitle(titleId)
                .setMessage(messageId)
                .setPositiveButton(android.R.string.ok, okClickListener)
                .setNegativeButton(android.R.string.cancel, cancelClickListener)
                .show();
    }

    /**
     * Prompts the user for permission to use APIs.
     */
    protected void requestPermissions(final int reqCd) {
        switch (reqCd) {
            case MyConst.REQCD_ACCESS_GALLERY:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        + ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    postRequestPermissionsResult(reqCd, true);
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        // If we should give explanation of requested permissions
                        // Show an alert dialog here with request explanation
                        showAlertOkCancel(R.string.permission_dialog_title, R.string.permission_camera_gallery, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(BaseActivity.this,
                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        reqCd);
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                postRequestPermissionsResult(reqCd, false);
                            }
                        });
                    } else {
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                reqCd);
                    }

                }
                break;
            case MyConst.REQCD_ACCESS_CAMERA:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        + ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        + ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED) {
                    postRequestPermissionsResult(reqCd, true);
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            this, Manifest.permission.CAMERA)
                            || ActivityCompat.shouldShowRequestPermissionRationale(
                            this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        // If we should give explanation of requested permissions
                        // Show an alert dialog here with request explanation
                        showAlertOkCancel(R.string.permission_dialog_title, R.string.permission_camera_msg, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(BaseActivity.this,
                                        new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        reqCd);
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                postRequestPermissionsResult(reqCd, false);
                            }
                        });
                    } else {
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                reqCd);
                    }
                }
                break;
            case MyConst.REQCD_ACCESS_FINE_LOCATION:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    postRequestPermissionsResult(reqCd, true);
                } else {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                        // If we should give explanation of requested permissions
                        // Show an alert dialog here with request explanation
                        showAlertOkCancel(R.string.permission_dialog_title, R.string.permission_camera_msg, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(BaseActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        reqCd);
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                postRequestPermissionsResult(reqCd, false);
                            }
                        });
                    } else {
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                reqCd);
                    }
                }
                break;
        }

    }

    /**
     * Handles the result of the request for permissions.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    postRequestPermissionsResult(requestCode, false);
                    return;
                }
            }
            postRequestPermissionsResult(requestCode, true);
            return;
        }

        postRequestPermissionsResult(requestCode, false);
    }
    /**
     * Opens the full screen image viewer dialog.
     *
     * @param imgUri
     * @param title
     * @param subtitle
     * @param desc
     */
    public void showImageViewer(String imgUri, String title, String subtitle, String desc, TravelBaseEntity entity) {
        Bundle b = new Bundle();
        b.putString(MyConst.KEY_ID, imgUri);
        b.putString(MyConst.KEY_TITLE, title);
        b.putString(MyConst.KEY_SUBTITLE, subtitle);
        b.putString(MyConst.KEY_DESC, desc);
        if (entity != null) {
            b.putSerializable(MyConst.REQKEY_TRAVEL, entity);
        }

        ImageViewerDialog dialog = new ImageViewerDialog();
        dialog.setArguments(b);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        dialog.show(ft, ImageViewerDialog.TAG);
    }
}
