package com.vtcac.thuhuong.mytrips.traveldetail.diary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.vtcac.thuhuong.mytrips.R;
import com.vtcac.thuhuong.mytrips.adapter.DiaryListAdapter;
import com.vtcac.thuhuong.mytrips.base.BaseActivity;
import com.vtcac.thuhuong.mytrips.base.ListItemClickListener;
import com.vtcac.thuhuong.mytrips.base.ViewItemListClickListener;
import com.vtcac.thuhuong.mytrips.entity.Diary;
import com.vtcac.thuhuong.mytrips.entity.Expense;
import com.vtcac.thuhuong.mytrips.entity.Travel;
import com.vtcac.thuhuong.mytrips.entity.TravelBaseEntity;
import com.vtcac.thuhuong.mytrips.model.DiaryViewModel;
import com.vtcac.thuhuong.mytrips.traveldetail.TravelDetailBaseFragment;
import com.vtcac.thuhuong.mytrips.utils.MyConst;
import com.vtcac.thuhuong.mytrips.utils.MyString;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class FragmentDiary extends TravelDetailBaseFragment implements ListItemClickListener
        , ViewItemListClickListener {
    private static final String TAG = FragmentDiary.class.getSimpleName();
    public static final int TITLE_ID = R.string.tab_diary;
    private DiaryListAdapter diaryListAdapter;
    private DiaryViewModel diaryViewModel;
    private static Diary diary;

    private final Observer<List<Diary>> diaryObserver = new Observer<List<Diary>>() {
        @Override
        public void onChanged(List<Diary> diaries) {
            diaryListAdapter.setDiaryList(diaries);
        }
    };

    public FragmentDiary() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static FragmentDiary newInstance(int sectionNumber) {
        Log.d(TAG, "newInstance: sectionNumber=" + sectionNumber);
        FragmentDiary fragment = new FragmentDiary();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        diaryListAdapter = new DiaryListAdapter(getContext());
        diaryListAdapter.setListItemClickListener(this);
        diaryListAdapter.setViewItemListClickListener(this);
        diaryViewModel = ViewModelProviders.of(this).get(DiaryViewModel.class);
        travelDetailViewModel.getDiaryList().observe(this, diaryObserver);
    }

    @Override
    public void requestAddItem(Travel travel) {
        Log.d(TAG, "requestAddItem: travel=" + travel.toString());
        if (travel == null) return;
        startActivityForResult(new Intent(getContext(), EditDiaryActivity.class)
                .putExtra(MyConst.AC_DIARY, MyConst.AC_ADD_DIARY)
                .putExtra(MyConst.OBJ_TRAVEL, travel), MyConst.REQCD_ADD_DIARY);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_diary, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.rvDiaries);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(diaryListAdapter);
        return rootView;
    }

    @Override
    public void onListItemClick(View v, int position, TravelBaseEntity entity) {
        Diary item = (Diary) entity;
        ((BaseActivity) getActivity()).showImageViewer(item.getImgUri(), item.getStartDt()+" "
                +item.getTime(), item.getPlaceAddr(), item.getDesc(), entity);
    }

    @Override
    public void onMoreVertMenuItemClick(int viewId, int position, final TravelBaseEntity entity) {
        switch (viewId) {
            case R.id.mniEdit:
                startActivityForResult(new Intent(getActivity(), EditDiaryActivity.class)
                                .putExtra(MyConst.AC_DIARY, MyConst.AC_EDIT_DIARY)
                                .putExtra(MyConst.OBJ_DIARY, entity)
                        , MyConst.REQCD_EDIT_DIARY);
                break;
            case R.id.mniDelete:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage(R.string.title_dialog_delete_item)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                diaryViewModel.delete((Diary) entity);
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
        // do nothing
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}
