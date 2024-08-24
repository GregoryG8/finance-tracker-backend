package com.finance.tracker.finance_tracker.repo;

import com.finance.tracker.finance_tracker.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Integer> {

    List<Transaction> findByCategoryName(String categoryName);

    List<Transaction> findByDateBetween(LocalDate start, LocalDate end);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.type = 'INCOME' AND t.date BETWEEN :startDate AND :endDate")
    BigDecimal findTotalIncome(LocalDate startDate, LocalDate endDate);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.type = 'EXPENSE' AND t.date BETWEEN :startDate AND :endDate")
    BigDecimal findTotalExpense(LocalDate startDate, LocalDate endDate);
}
