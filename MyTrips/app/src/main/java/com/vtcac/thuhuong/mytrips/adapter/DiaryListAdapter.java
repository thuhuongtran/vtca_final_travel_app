package com.vtcac.thuhuong.mytrips.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.vtcac.thuhuong.mytrips.R;
import com.vtcac.thuhuong.mytrips.base.ListItemClickListener;
import com.vtcac.thuhuong.mytrips.base.ViewItemListClickListener;
import com.vtcac.thuhuong.mytrips.entity.Diary;
import com.vtcac.thuhuong.mytrips.utils.MyImage;
import com.vtcac.thuhuong.mytrips.utils.MyString;

import java.io.File;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DiaryListAdapter extends RecyclerView.Adapter<DiaryListAdapter.DiaryViewHolder> {
    private final String TAG = DiaryListAdapter.class.getSimpleName();
    private List<Diary> diaryList;
    private final LayoutInflater layoutInflater;
    private ListItemClickListener listItemClickListener;
    private ViewItemListClickListener viewItemListClickListener;

    public void setViewItemListClickListener(ViewItemListClickListener viewItemListClickListener) {
        this.viewItemListClickListener = viewItemListClickListener;
    }

    public void setListItemClickListener(ListItemClickListener listItemClickListener) {
        this.listItemClickListener = listItemClickListener;
    }

    public DiaryListAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setDiaryList(List<Diary> diaryList) {
        this.diaryList = diaryList;
        notifyDataSetChanged();
    }

    private Diary getItem(int position) {
        if (getItemCount() == 0) return null;
        return diaryList.get(position);
    }

    @NonNull
    @Override
    public DiaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.item_diary, parent, false);
        return new DiaryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryViewHolder holder, int position) {
        Diary item = getItem(position);
        if (item == null) {
            return;
        }
        Log.d(TAG, "onBindViewHolder: getImgUri="+item.getImgUri());
        if (MyString.isEmpty(item.getImgUri())) {
            holder.ivDiaryImg.setImageResource(MyImage.getDefaultImgID(MyImage.getRandomNumber()));
        } else {
            File f = new File(item.getImgUri()); // to check if user delete file_img in phone
            if(!f.exists()){
                Log.d(TAG, "onBindViewHolder: file_img_exist="+f.exists());
                holder.ivDiaryImg.setImageResource(MyImage.getDefaultImgID(MyImage.getRandomNumber()));
            }
            else holder.ivDiaryImg.setImageURI(Uri.parse(item.getImgUri()));
        }
        holder.tvDiaryDetail.setText(item.getStartDt() + " " + item.getTime());
    }

    @Override
    public int getItemCount() {
        if (diaryList == null) return 0;
        return diaryList.size();
    }

    class DiaryViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivDiaryImg;
        private TextView tvDiaryDetail;
        private ImageView ivDiaryMoreMenu;

        public DiaryViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ivDiaryImg = itemView.findViewById(R.id.ivDiaryImg);
            this.tvDiaryDetail = itemView.findViewById(R.id.tvDiaryDetail);
            this.ivDiaryMoreMenu = itemView.findViewById(R.id.ivDiaryMoreMenu);
            ivDiaryMoreMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(v.getContext(), ivDiaryMoreMenu);
                    popupMenu.getMenuInflater().inflate(R.menu.menu_more_vert_travel_item, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.mniDelete:
                                    listItemClickListener.onMoreVertMenuItemClick(
                                            R.id.mniDelete,
                                            getAdapterPosition()
                                            , getItem(getAdapterPosition()));
                                    break;
                                case R.id.mniEdit:
                                    listItemClickListener.onMoreVertMenuItemClick(
                                            R.id.mniEdit,
                                            getAdapterPosition()
                                            , getItem(getAdapterPosition()));
                                    break;
                            }
                            return true;
                        }
                    });
                    popupMenu.show();
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listItemClickListener.onListItemClick(v, getAdapterPosition()
                            , getItem(getAdapterPosition()));
                }
            });
        }
    }
}
