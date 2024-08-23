package com.finance.tracker.finance_tracker.controllers;


import com.finance.tracker.finance_tracker.entities.Category;
import com.finance.tracker.finance_tracker.repo.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {
    // Injecting the TransactionRepo to interact with the database.
    @Autowired
    private CategoryRepo categoryRepo;

    @CrossOrigin(origins = "*")
    @GetMapping("/categories")
    public ResponseEntity<?> getAllCategories() {
        List<Category> categories = categoryRepo.findAll();
        return ResponseEntity.ok(categories);
    }
}
