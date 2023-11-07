package com.github.golovnyakpa.personal_expenses.converters;

import com.github.golovnyakpa.personal_expenses.dao.entities.Expense;
import com.github.golovnyakpa.personal_expenses.dao.entities.ExpensesSumByCategory;
import com.github.golovnyakpa.personal_expenses.dtos.request.ExpenseDtoRq;
import com.github.golovnyakpa.personal_expenses.dtos.response.ExpenseDtoRs;
import com.github.golovnyakpa.personal_expenses.dtos.response.ExpensesSumByCategoryRs;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ExpenseConverter {
    public ExpenseDtoRs entityToDto(Expense expense) {
        return new ExpenseDtoRs(
                expense.getId(),
                expense.getTitle(),
                expense.getAmount(),
                expense.getCategory(),
                expense.getDescription(),
                expense.getDateTime()
        );
    }

    public Expense dtoRqToEntity(ExpenseDtoRq expenseDtoRq) {
        var newExpense = new Expense();
        newExpense.setTitle(expenseDtoRq.getTitle());
        newExpense.setAmount(expenseDtoRq.getAmount());
        newExpense.setCategory(expenseDtoRq.getCategory());
        newExpense.setDescription(expenseDtoRq.getDescription());
        if (expenseDtoRq.getDateTime() != null) {
            newExpense.setDateTime(expenseDtoRq.getDateTime());
        } else {
            newExpense.setDateTime(LocalDateTime.now());
        }
        return newExpense;
    }

    public ExpensesSumByCategoryRs entityExpensesSumByCategoryToRs(ExpensesSumByCategory es) {
        return new ExpensesSumByCategoryRs(es.category(), es.amount());
    }
}
