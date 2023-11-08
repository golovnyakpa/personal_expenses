package com.github.golovnyakpa.personal_expenses.services;

import com.github.golovnyakpa.personal_expenses.converters.ExpenseConverter;
import com.github.golovnyakpa.personal_expenses.dao.entities.Expense;
import com.github.golovnyakpa.personal_expenses.dao.entities.ExpensesSumByCategory;
import com.github.golovnyakpa.personal_expenses.dao.repositories.ExpensesRepository;
import com.github.golovnyakpa.personal_expenses.dtos.request.ExpenseDtoRq;
import com.github.golovnyakpa.personal_expenses.exceptions.ResourceForbiddenException;
import com.github.golovnyakpa.personal_expenses.exceptions.ResourceNotFoundException;
import com.github.golovnyakpa.personal_expenses.utils.auth.AuthUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class ExpensesServiceImpl implements ExpensesService {

    private final ExpensesRepository expenseRepository;
    private final ExpenseConverter expenseConverter;
    private final AuthUtils authUtils;

    @Override
    public List<Expense> getAll(int realPageIndex, int pageSize) {
        var user = authUtils.getCurrentUsername();
        return expenseRepository
                .findAllByUsername(user, PageRequest.of(realPageIndex - 1, pageSize))
                .getContent();
    }

    @Override
    public Expense getById(Long id) {
        Expense res = expenseRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Product with id %s not found", id))
        );
        if (!Objects.equals(res.getUsername(), authUtils.getCurrentUsername())) {
            throw new ResourceForbiddenException(
                    String.format("Resource with id %s is forbidden for current user", id)
            );
        }
        return res;
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        var user = authUtils.getCurrentUsername();
        expenseRepository.deleteByIdAndUsername(id, user);
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
        var exp = getById(newExpense.getId());
        var user = authUtils.getCurrentUsername();
        if (!Objects.equals(exp.getUsername(), user)) {
            throw new ResourceForbiddenException(
                    String.format("Resource with id %s not belongs to current user", exp.getId())
            );
        }
        exp.setTitle(newExpense.getTitle());
        exp.setAmount(newExpense.getAmount());
        exp.setCategory(newExpense.getCategory());
        exp.setDescription(newExpense.getDescription());
        exp.setDateTime(newExpense.getDateTime());
        return save(exp);
    }

    @Override
    public List<Expense> getSortedExpenses(
            LocalDateTime startDate,
            LocalDateTime endDate,
            String sortDirection,
            int realPageIndex,
            int pageSize
    ) {
        Sort.Direction sortDir = getSortDirection(sortDirection);
        PageRequest pr = PageRequest.of(realPageIndex - 1, pageSize, sortDir, "amount");
        var user = authUtils.getCurrentUsername();
        return expenseRepository
                .findByUsernameAndDateTimeBetween(startDate, endDate, user, pr)
                .getContent();
    }

    @Override
    public List<ExpensesSumByCategory> getSortedExpensesByCategory(
            LocalDateTime startDate,
            LocalDateTime endDate,
            String sortDirection,
            int realPageIndex,
            int pageSize
    ) {
        PageRequest pr = PageRequest.of(realPageIndex - 1, pageSize);
        var user = authUtils.getCurrentUsername();
        return expenseRepository.getExpenseSumByCategory(startDate, endDate, user, pr).getContent();
    }

    private Sort.Direction getSortDirection(String sortDirection) {
        return (Objects.equals(sortDirection, "asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
    }
}
