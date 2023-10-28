package com.github.golovnyakpa.personal_expenses.services;

import com.github.golovnyakpa.personal_expenses.dao.entities.Expense;
import com.github.golovnyakpa.personal_expenses.dtos.request.ExpenseDtoRq;

import java.util.List;
import java.util.Optional;

public interface ExpensesService {
    List<Expense> getAll();
    Optional<Expense> getById(Long id);
    void deleteById(Long id);
    Expense save(ExpenseDtoRq expense);
    Expense update(ExpenseDtoRq expense);
}
