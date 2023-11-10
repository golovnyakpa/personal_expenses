package com.github.golovnyakpa.personal_expenses.dao.repositories;

import com.github.golovnyakpa.personal_expenses.dao.entities.Expense;
import com.github.golovnyakpa.personal_expenses.dao.entities.ExpensesSumByCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ExpensesRepository extends JpaRepository<Expense, Long> {
    @Query("SELECT e FROM Expense e WHERE e.username = :username AND e.dateTime BETWEEN :startDate AND :endDate")
    Page<Expense> findByUsernameAndDateTimeBetween(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("username") String username,
            Pageable pageable
    );

    @Query("SELECT NEW com.github.golovnyakpa.personal_expenses.dao.entities.ExpensesSumByCategory(e.category, SUM(e.amount)) " +
            "FROM Expense e " +
            "WHERE e.username = :username AND e.dateTime BETWEEN :startDate AND :endDate " +
            "GROUP BY e.category " +
            "ORDER BY SUM(e.amount) DESC")
    Page<ExpensesSumByCategory> getExpenseSumByCategory(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("username") String username,
            Pageable pageable
    );

    @Query("SELECT e FROM Expense e WHERE e.username = :username")
    Page<Expense> findAllByUsername(@Param("username") String username, Pageable pageable);

    @Modifying
    @Query("DELETE FROM Expense e WHERE e.id = :id AND e.username = :username")
    void deleteByIdAndUsername(
            @Param("id") Long id,
            @Param("username") String username
    );
}
