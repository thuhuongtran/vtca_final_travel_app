package com.vtcac.thuhuong.mytrips.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vtcac.thuhuong.mytrips.R;
import com.vtcac.thuhuong.mytrips.entity.Travel;
import com.vtcac.thuhuong.mytrips.utils.MyImage;
import com.vtcac.thuhuong.mytrips.utils.MyString;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TravelListAdapter extends RecyclerView.Adapter<TravelListAdapter.TravelViewHolder> {
    private static final String TAG = TravelListAdapter.class.getSimpleName();
    private List<Travel> travelList;
    private final LayoutInflater layoutInflater;

    public TravelListAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setTravelList(List<Travel> travelList) {
        this.travelList = travelList;
        notifyDataSetChanged();
    }


    private Travel getItem(int position) {
        if(getItemCount() == 0) return null;
        return travelList.get(position);
    }
    @NonNull
    @Override
    public TravelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.main_travel_item, parent, false);
        return new TravelViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TravelViewHolder holder, int position) {
        Travel item = getItem(position);
        if(item == null) return;
        if (MyString.isEmpty(item.getImgUri())) {
            holder.ivTravelImg.setImageResource(MyImage.getDefaultImgID(position));
        }
        else {
            holder.ivTravelImg.setImageURI(Uri.parse(item.getImgUri()));
        }
        holder.tvTravelTitle.setText(item.getTitle());
        holder.tvTravelPlaceAddr.setText(item.getPlaceAddr());
        holder.tvTravelDateTime.setText(item.getStartDt()+" ~ "+item.getEndDt());
        Log.d(TAG, "onBindViewHolder: place_addr="+item.getPlaceAddr());
    }

    @Override
    public int getItemCount() {
        if(travelList == null) return 0;
        return travelList.size();
    }

    class TravelViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTravelTitle;
        private TextView tvTravelPlaceAddr;
        private TextView tvTravelDateTime;
        private ImageView ivTravelImg;

        public TravelViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTravelTitle = itemView.findViewById(R.id.tvTravelTitle);
            tvTravelPlaceAddr = itemView.findViewById(R.id.tvTravelPlaceAddr);
            tvTravelDateTime = itemView.findViewById(R.id.tvTravelDateTime);
            ivTravelImg = itemView.findViewById(R.id.ivTravelImg);
        }
    }
}
