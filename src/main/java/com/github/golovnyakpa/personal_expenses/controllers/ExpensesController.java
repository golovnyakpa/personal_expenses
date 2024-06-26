package com.github.golovnyakpa.personal_expenses.controllers;

import com.github.golovnyakpa.personal_expenses.converters.ExpenseConverter;
import com.github.golovnyakpa.personal_expenses.dao.entities.Expense;
import com.github.golovnyakpa.personal_expenses.dtos.request.ExpenseDtoRq;
import com.github.golovnyakpa.personal_expenses.dtos.response.ExpenseDtoRs;
import com.github.golovnyakpa.personal_expenses.services.ExpensesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/expenses")
public class ExpensesController {

    private final ExpensesService expensesService;
    private final ExpenseConverter expenseConverter;

    @Operation(summary = "Create new expense", security = {@SecurityRequirement(name = "bearer-key")})
    @PostMapping
    public ExpenseDtoRs saveNewExpense(@Valid @RequestBody ExpenseDtoRq expenseDtoRq) {
        Expense res = expensesService.save(expenseDtoRq);
        return expenseConverter.entityToDto(res);
    }

    @Operation(summary = "Get all expenses", security = {@SecurityRequirement(name = "bearer-key")})
    @GetMapping
    public List<ExpenseDtoRs> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(name = "page_size", defaultValue = "5") int pageSize
    ) {
        return expensesService.getAll(page, pageSize).stream().map(expenseConverter::entityToDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get an expense by its id", security = {@SecurityRequirement(name = "bearer-key")})
    @GetMapping("/{id}")
    public ExpenseDtoRs getById(@PathVariable Long id) {
        Expense res = expensesService.getById(id);
        return expenseConverter.entityToDto(res);
    }

    // todo remove id from query
    @Operation(summary = "Modify existing expense", security = {@SecurityRequirement(name = "bearer-key")})
    @PutMapping("/{id}")
    public ExpenseDtoRs updateById(@PathVariable Long id, @Valid @RequestBody ExpenseDtoRq expenseDtoRq) {
        expenseDtoRq.setId(id);
        Expense res = expensesService.update(expenseDtoRq);
        return expenseConverter.entityToDto(res);
    }

    // todo maybe wanna know if was deleted
    @Operation(summary = "Delete an expense by its id", security = {@SecurityRequirement(name = "bearer-key")})
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        expensesService.deleteById(id);
    }
}
