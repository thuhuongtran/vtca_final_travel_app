package com.vtcac.thuhuong.mytrips.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vtcac.thuhuong.mytrips.R;
import com.vtcac.thuhuong.mytrips.base.ListItemClickListener;
import com.vtcac.thuhuong.mytrips.entity.Travel;
import com.vtcac.thuhuong.mytrips.utils.MyImage;
import com.vtcac.thuhuong.mytrips.utils.MyString;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SearchTravelsListAdapter extends RecyclerView.Adapter<SearchTravelsListAdapter.SearchViewHolder>{
    private ListItemClickListener listItemClickListener;
    private List<Travel> travelList;
    private final LayoutInflater layoutInflater;

    public void setListItemClickListener(ListItemClickListener listItemClickListener) {
        this.listItemClickListener = listItemClickListener;
    }

    public SearchTravelsListAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setTravelList(List<Travel> travelList) {
        this.travelList = travelList;
        notifyDataSetChanged();
    }

    private Travel getItem(int position) {
        if (getItemCount() == 0) return null;
        return travelList.get(position);
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.search_result_item, parent, false);
        return new SearchViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        Travel item = getItem(position);
        if (item == null) return;
        if (MyString.isEmpty(item.getImgUri())) {
            holder.ivTravelImg.setImageResource(MyImage.getDefaultImgID(position));
        } else {
            holder.ivTravelImg.setImageURI(Uri.parse(item.getImgUri()));
        }
        holder.tvPlaceAddr.setText(item.getPlaceAddr());
        holder.tvDate.setText(item.getStartDt()+" ~ "+item.getEndDt());
    }

    @Override
    public int getItemCount() {
        if (travelList == null) return 0;
        return travelList.size();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {
        private TextView tvPlaceAddr;
        private TextView tvDate;
        private ImageView ivTravelImg;

        public SearchViewHolder(@NonNull final View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listItemClickListener.onListItemClick(itemView, getAdapterPosition()
                            , getItem(getAdapterPosition()));
                }
            });
            tvPlaceAddr = itemView.findViewById(R.id.tvPlaceAddr);
            tvDate = itemView.findViewById(R.id.tvDate);
            ivTravelImg = itemView.findViewById(R.id.ivTravelImg);
        }
    }
}
