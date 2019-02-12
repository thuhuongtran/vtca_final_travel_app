package com.vtcac.thuhuong.mytrips.model;

import android.app.Application;

import com.vtcac.thuhuong.mytrips.database.ExpenseRepository;
import com.vtcac.thuhuong.mytrips.entity.Expense;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class ExpenseViewModel extends AndroidViewModel {
    private final ExpenseRepository expenseRepo;

    public ExpenseViewModel(@NonNull Application application) {
        super(application);
        expenseRepo = ExpenseRepository.getInstance(application);
    }

    public LiveData<List<Expense>> getAllExpensesByTravelId(long travelId) {
        return expenseRepo.getAllExpensesByTravelId(travelId);
    }

    public void insert(Expense expense) {
        expenseRepo.insert(expense);
    }

    public void delete(Expense expense) { expenseRepo.delete(expense); }

    public void update(Expense expense) {
        expenseRepo.update(expense);
    }
}
