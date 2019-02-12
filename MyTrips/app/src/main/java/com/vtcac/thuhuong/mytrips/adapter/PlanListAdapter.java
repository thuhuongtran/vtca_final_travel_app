package com.vtcac.thuhuong.mytrips.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vtcac.thuhuong.mytrips.R;
import com.vtcac.thuhuong.mytrips.base.ListItemClickListener;
import com.vtcac.thuhuong.mytrips.base.ViewItemListClickListener;
import com.vtcac.thuhuong.mytrips.entity.Plan;
import com.vtcac.thuhuong.mytrips.utils.MyString;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PlanListAdapter extends RecyclerView.Adapter<PlanListAdapter.PlanViewHolder> {

    private List<Plan> planList;
    private final LayoutInflater layoutInflater;
    private ListItemClickListener listItemClickListener;
    private ViewItemListClickListener viewItemListClickListener;

    public void setViewItemListClickListener(ViewItemListClickListener viewItemListClickListener) {
        this.viewItemListClickListener = viewItemListClickListener;
    }

    public void setListItemClickListener(ListItemClickListener listItemClickListener) {
        this.listItemClickListener = listItemClickListener;
    }

    public PlanListAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setPlanList(List<Plan> planList) {
        this.planList = planList;
        notifyDataSetChanged();
    }

    private Plan getItem(int position) {
        if (getItemCount() == 0) return null;
        return planList.get(position);
    }

    @NonNull
    @Override
    public PlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.item_plan, parent, false);
        return new PlanViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanViewHolder holder, int position) {
        Plan item = getItem(position);
        if (item == null) {
            return;
        }
        holder.tvPlanTitle.setText(item.getTitle());
        holder.tvPlanDateTime.setText(item.getStartDt());
        holder.tvPlanDesc.setText(item.getDesc());
        if (!MyString.isEmpty(item.getPlaceName())) {
            holder.tvPlanPlace.setText(item.getPlaceName());
        } else {
            holder.tvPlanPlace.setText(R.string.place_item_plan);
        }
    }

    @Override
    public int getItemCount() {
        if (planList == null) return 0;
        return planList.size();
    }

    class PlanViewHolder extends RecyclerView.ViewHolder {
        private TextView tvPlanTitle;
        private TextView tvPlanDateTime;
        private TextView tvPlanDesc;
        private TextView tvPlanPlace;
        private ImageView ivDelPlan;

        public PlanViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listItemClickListener.onListItemClick(v, getAdapterPosition()
                            , getItem(getAdapterPosition()));
                }
            });
            tvPlanTitle = itemView.findViewById(R.id.tvPlanTitle);
            tvPlanDateTime = itemView.findViewById(R.id.tvPlanDateTime);
            tvPlanDesc = itemView.findViewById(R.id.tvPlanDesc);
            tvPlanPlace = itemView.findViewById(R.id.tvPlanPlace);
            ivDelPlan = itemView.findViewById(R.id.ivDelPlan);
            ivDelPlan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewItemListClickListener.onViewItemListClick(v,getAdapterPosition()
                    ,getItem(getAdapterPosition()));
                }
            });
            tvPlanPlace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewItemListClickListener.onViewItemListClick(v,getAdapterPosition()
                            ,getItem(getAdapterPosition()));
                }
            });
        }
    }
}
