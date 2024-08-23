package com.finance.tracker.finance_tracker.repo;

import com.finance.tracker.finance_tracker.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Integer> {

}
