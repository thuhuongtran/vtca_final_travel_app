package com.vtcac.thuhuong.mytrips.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vtcac.thuhuong.mytrips.R;
import com.vtcac.thuhuong.mytrips.base.ListItemClickListener;
import com.vtcac.thuhuong.mytrips.base.ViewItemListClickListener;
import com.vtcac.thuhuong.mytrips.entity.Expense;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ExpenseListAdapter extends RecyclerView.Adapter<ExpenseListAdapter.ExpenseViewHolder>{
    private List<Expense> expenseList;
    private final LayoutInflater layoutInflater;
    private ListItemClickListener listItemClickListener;
    private ViewItemListClickListener viewItemListClickListener;

    public void setViewItemListClickListener(ViewItemListClickListener viewItemListClickListener) {
        this.viewItemListClickListener = viewItemListClickListener;
    }

    public void setListItemClickListener(ListItemClickListener listItemClickListener) {
        this.listItemClickListener = listItemClickListener;
    }

    public ExpenseListAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setExpenseList(List<Expense> expenseList) {
        this.expenseList = expenseList;
        notifyDataSetChanged();
    }

    private Expense getItem(int position) {
        if (getItemCount() == 0) return null;
        return expenseList.get(position);
    }

    @NonNull
    @Override
    public ExpenseListAdapter.ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.item_expense, parent, false);
        return new ExpenseListAdapter.ExpenseViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ExpenseListAdapter.ExpenseViewHolder holder, int position) {
        Expense item = getItem(position);
        if (item == null) {
            return;
        }
        switch (item.getType()) {
            case "EXPENSE":
                holder.type.setTextColor(Color.parseColor("#d7690e"));
                break;
            case "BUDGET":
                holder.type.setTextColor(Color.parseColor("#11993c"));
                break;
        }
        holder.type.setText(item.getType());
        holder.title.setText(item.getTitle());
        holder.dateTime.setText(item.getStartDt()+" "+item.getTime());
        holder.desc.setText(item.getDesc());
        holder.place.setText(item.getPlaceName());
        holder.amount.setText(String.valueOf(item.getAmount()));
        holder.currency.setText(item.getCurrency());
    }

    @Override
    public int getItemCount() {
        if (expenseList == null) return 0;
        return expenseList.size();
    }

    class ExpenseViewHolder extends RecyclerView.ViewHolder {
        private TextView type;
        private TextView title;
        private TextView dateTime;
        private TextView desc;
        private TextView amount;
        private TextView currency;
        private TextView place;
        private ImageView expDel;

        public ExpenseViewHolder(@NonNull final View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.tvExpenseType);
            title = itemView.findViewById(R.id.tvExpenseTitle);
            dateTime = itemView.findViewById(R.id.tvExpenseDate);
            desc = itemView.findViewById(R.id.tvExpenseDesc);
            amount = itemView.findViewById(R.id.tvExpenseAmount);
            currency = itemView.findViewById(R.id.tvExpenseCurrency);
            place = itemView.findViewById(R.id.tvExpensePlace);
            expDel = itemView.findViewById(R.id.ivExpDel);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listItemClickListener.onListItemClick(v,getAdapterPosition(),
                            getItem(getAdapterPosition()));
                }
            });
            place.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewItemListClickListener.onViewItemListClick(v, getAdapterPosition(),
                            getItem(getAdapterPosition()));
                }
            });
            expDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewItemListClickListener.onViewItemListClick(v,getAdapterPosition()
                            ,getItem(getAdapterPosition()));
                }
            });

        }
    }
}
