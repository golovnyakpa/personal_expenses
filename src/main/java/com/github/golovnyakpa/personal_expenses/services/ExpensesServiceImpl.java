package com.github.golovnyakpa.personal_expenses.services;

import com.github.golovnyakpa.personal_expenses.converters.ExpenseConverter;
import com.github.golovnyakpa.personal_expenses.dao.entities.Expense;
import com.github.golovnyakpa.personal_expenses.dao.repositories.ExpensesRepository;
import com.github.golovnyakpa.personal_expenses.dtos.request.ExpenseDtoRq;
import com.github.golovnyakpa.personal_expenses.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ExpensesServiceImpl implements ExpensesService {

    private final ExpensesRepository expenseRepository;
    private final ExpenseConverter expenseConverter;

    @Override
    public List<Expense> getAll(int realPageIndex, int pageSize) {
        return expenseRepository.findAll(PageRequest.of(realPageIndex - 1, pageSize)).getContent();
    }

    @Override
    public Optional<Expense> getById(Long id) {
        return expenseRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        expenseRepository.deleteById(id);
    }

    @Override
    public Expense save(ExpenseDtoRq expense) {
        Expense newExpense = expenseConverter.dtoRqToEntity(expense);
        return expenseRepository.save(newExpense);
    }

    private Expense save(Expense expense) {
        return expenseRepository.save(expense);
    }

    @Transactional
    @Override
    public Expense update(ExpenseDtoRq newExpense) {
        return getById(newExpense.getId()).map(exp -> {
            exp.setTitle(newExpense.getTitle());
            exp.setAmount(newExpense.getAmount());
            exp.setCategory(newExpense.getCategory());
            exp.setDescription(newExpense.getDescription());
            exp.setDateTime(newExpense.getDateTime());
            return save(exp);
        }).orElseThrow(() ->
                new ResourceNotFoundException(
                        String.format(
                                "Resource can't be updated, because resource with %s not exists",
                                newExpense.getId()
                        )
                )
        );
    }

    @Override
    public List<Expense> getSortedExpenses(
            LocalDateTime startDate,
            LocalDateTime endDate,
            String sortDirection,
            int realPageIndex,
            int pageSize
    ) {
        Sort.Direction sortDir = (Objects.equals(sortDirection, "asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
        var pageRequest = PageRequest.of(realPageIndex, pageSize, sortDir, "amount");
        System.out.println("date time is: " + startDate);
        return expenseRepository
                .findByDateTimeBetween(startDate, endDate, pageRequest)
                .getContent();
    }

    @Override
    public List<Expense> getSortedCategories(
            LocalDateTime startDate,
            LocalDateTime endDate,
            String sortDirection,
            int realPageIndex,
            int pageSize
    ) {
        return null;
    }
}
