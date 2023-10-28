package com.github.golovnyakpa.personal_expenses.controllers;

import com.github.golovnyakpa.personal_expenses.converters.ExpenseConverter;
import com.github.golovnyakpa.personal_expenses.dao.entities.Expense;
import com.github.golovnyakpa.personal_expenses.dtos.request.ExpenseDtoRq;
import com.github.golovnyakpa.personal_expenses.dtos.response.ExpenseDtoRs;
import com.github.golovnyakpa.personal_expenses.exceptions.ResourceNotFoundException;
import com.github.golovnyakpa.personal_expenses.services.ExpensesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/expenses")
public class ExpensesController {

    private final ExpensesService expensesService;
    private final ExpenseConverter expenseConverter;

    @PostMapping
    public ExpenseDtoRs saveNewExpense(@Valid @RequestBody ExpenseDtoRq expenseDtoRq) {
        Expense res = expensesService.save(expenseDtoRq);
        return expenseConverter.entityToDto(res);
    }

    @GetMapping("/{id}")
    public ExpenseDtoRs getById(@PathVariable Long id) {
        Expense res = expensesService
                .getById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(String.format("Product with id %s not found", id))
                );
        return expenseConverter.entityToDto(res);
    }

    @PutMapping("/{id}")
    public ExpenseDtoRs updateById(@PathVariable Long id, @Valid @RequestBody ExpenseDtoRq expenseDtoRq) {
        expenseDtoRq.setId(id);
        Expense res = expensesService.update(expenseDtoRq);
        return expenseConverter.entityToDto(res);
    }

    // todo maybe wanna know if was deleted
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        expensesService.deleteById(id);
    }
}
