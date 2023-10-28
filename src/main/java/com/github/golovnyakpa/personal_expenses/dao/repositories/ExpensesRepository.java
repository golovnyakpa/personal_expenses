package com.github.golovnyakpa.personal_expenses.dao.repositories;

import com.github.golovnyakpa.personal_expenses.dao.entities.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpensesRepository extends JpaRepository<Expense, Long> {
}
