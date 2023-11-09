package com.github.golovnyakpa.personal_expenses.services;

import com.github.golovnyakpa.personal_expenses.dao.entities.Expense;
import com.github.golovnyakpa.personal_expenses.dao.entities.ExpensesSumByCategory;
import com.github.golovnyakpa.personal_expenses.dtos.request.ExpenseDtoRq;

import java.time.LocalDateTime;
import java.util.List;

public interface ExpensesService {
    List<Expense> getAll(int realPageIndex, int pageSize);

    Expense getById(Long id);

    void deleteById(Long id);

    Expense save(ExpenseDtoRq expense);

    Expense update(ExpenseDtoRq expense);

    List<Expense> getSortedExpenses(LocalDateTime startDate, LocalDateTime endDate, String sortDirection, int realPageIndex, int pageSize);

    List<ExpensesSumByCategory> getSortedExpensesByCategory(LocalDateTime startDate, LocalDateTime endDate, int realPageIndex, int pageSize);
}
