package com.finance.tracker.finance_tracker.repo;

import com.finance.tracker.finance_tracker.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {
}
