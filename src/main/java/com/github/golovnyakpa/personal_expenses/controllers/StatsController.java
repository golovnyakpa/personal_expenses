package com.github.golovnyakpa.personal_expenses.controllers;

import com.github.golovnyakpa.personal_expenses.converters.ExpenseConverter;
import com.github.golovnyakpa.personal_expenses.dtos.response.ExpenseDtoRs;
import com.github.golovnyakpa.personal_expenses.dtos.response.ExpensesSumByCategoryRs;
import com.github.golovnyakpa.personal_expenses.services.ExpensesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/expenses/stats")
public class StatsController {

    private final ExpensesService expensesService;
    private final ExpenseConverter expenseConverter;

    // todo not return json array
    // todo params to post request body
    @Operation(summary = "Get sorted expenses for time period", security = {@SecurityRequirement(name = "bearer-key")})
    @GetMapping("top")
    List<ExpenseDtoRs> getSortedExpenses(
            @RequestParam(name = "start_period") @DateTimeFormat(pattern = "yyyyMMddHHmmss") LocalDateTime startPeriod,
            @RequestParam(name = "end_period") @DateTimeFormat(pattern = "yyyyMMddHHmmss") LocalDateTime endPeriod,
            @RequestParam(defaultValue = "desc") String sort,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(name = "page_size", defaultValue = "10") int pageSize
    ) {
        return expensesService
                .getSortedExpenses(startPeriod, endPeriod, sort, page, pageSize)
                .stream().map(expenseConverter::entityToDto).collect(Collectors.toList());
    }

    @Operation(summary = "Get top expenses categories", security = {@SecurityRequirement(name = "bearer-key")})
    @GetMapping("top_categories")
    List<ExpensesSumByCategoryRs> getBiggestExpensesCategories(
            @RequestParam(name = "start_period") @DateTimeFormat(pattern = "yyyyMMddHHmmss") LocalDateTime startPeriod,
            @RequestParam(name = "end_period") @DateTimeFormat(pattern = "yyyyMMddHHmmss") LocalDateTime endPeriod,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(name = "page_size", defaultValue = "10") int pageSize
    ) {
        return expensesService
                .getSortedExpensesByCategory(startPeriod, endPeriod, page, pageSize)
                .stream().map(expenseConverter::entityExpensesSumByCategoryToRs)
                .collect(Collectors.toList());
    }

}
