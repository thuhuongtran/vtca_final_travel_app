package com.vtcac.thuhuong.mytrips.traveldetail.expense;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vtcac.thuhuong.mytrips.R;
import com.vtcac.thuhuong.mytrips.base.ListItemClickListener;
import com.vtcac.thuhuong.mytrips.entity.Expense;
import com.vtcac.thuhuong.mytrips.utils.MyConst;
import com.vtcac.thuhuong.mytrips.utils.MyString;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BudgetStatusListAdapter extends RecyclerView.Adapter<BudgetStatusListAdapter.TravelViewHolder> {
    private final String TAG = BudgetStatusListAdapter.class.getSimpleName();
    private final LayoutInflater mLayoutInflater;
    private List<Expense> expenseList;
    private ListItemClickListener listItemClickListener;

    public BudgetStatusListAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setListItemClickListener(ListItemClickListener listItemClickListener) {
        listItemClickListener = listItemClickListener;
    }

    @Override
    public int getItemCount() {
        if (expenseList == null) return 0;
        return expenseList.size();
    }

    public void setList(List<Expense> list) {
        this.expenseList = list;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        if (getItemCount() == 0) return RecyclerView.NO_ID;
        return expenseList.get(position).getId();
    }

    private Expense getItem(int position) {
        if (getItemCount() == 0) return null;
        return expenseList.get(position);
    }

    @Override
    public void onBindViewHolder(@NonNull TravelViewHolder holder, int position) {
        Expense item = getItem(position);
        if (item == null) {
            return;
        }
        Log.d(TAG, "onBindViewHolder: getCurrency="+item.getCurrency());
        holder.currency.setText(item.getCurrency());
        holder.budget.setText(MyString.getMoneyText(item.getPlaceLat()));
        holder.expenses.setText(item.getAmountText());
        holder.balance.setText(MyString.getMoneyText(item.getPlaceLat() - item.getAmount()));
    }

    @NonNull
    @Override
    public TravelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.fragment_budget_status_item, parent, false);
        return new TravelViewHolder(v);
    }

    class TravelViewHolder extends RecyclerView.ViewHolder {
        private final TextView currency;
        private final TextView budget;
        private final TextView expenses;
        private final TextView balance;

        private TravelViewHolder(View v) {
            super(v);
            if (listItemClickListener != null) {
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listItemClickListener.onListItemClick(v
                                , getAdapterPosition()
                                , getItem(getAdapterPosition()));
                    }
                });
            }
            currency = v.findViewById(R.id.currency);
            budget = v.findViewById(R.id.budget);
            expenses = v.findViewById(R.id.expenses);
            balance = v.findViewById(R.id.balance);
        }
    }
}
