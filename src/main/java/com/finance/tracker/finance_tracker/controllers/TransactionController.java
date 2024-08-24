package com.finance.tracker.finance_tracker.controllers;

import com.finance.tracker.finance_tracker.dto.FinancialSummaryDTO;
import com.finance.tracker.finance_tracker.entities.Transaction;
import com.finance.tracker.finance_tracker.repo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing financial transactions.
 * <p>
 * This controller provides endpoints for CRUD operations (Create, Read, Update, Delete)
 * related to the {@link Transaction} entity. Each method corresponds to a specific HTTP
 * operation, making it possible to perform different actions on the transaction data.
 */

@RestController
public class TransactionController {

    // Injecting the TransactionRepo to interact with the database.
    @Autowired
    private TransactionRepo transactionRepo;

    /**
     * GET /transactions - Retrieves all transactions.
     *
     * @return A ResponseEntity containing a list of all transactions.
     */
    @CrossOrigin(origins = "*")

    @GetMapping("/transactions")
    public ResponseEntity<?> getAllTransactions() {
        List<Transaction> transactions = transactionRepo.findAll();
        return ResponseEntity.ok(transactions);
    }

    /**
     * GET /transactions/{id} - Retrieves a transaction by its ID.
     *
     * @param id The ID of the transaction to retrieve.
     * @return A ResponseEntity containing the transaction if found, or null if not found.
     */
    @GetMapping("/transactions/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable int id) {
        for (Transaction transaction : transactionRepo.findAll()) {
            if (transaction.getId() == id) {
                return ResponseEntity.ok(transaction);
            }
        }
        return null;
    }

    @GetMapping("/transactions/by-category")
    public ResponseEntity<List<Transaction>> getTransactionsByCategory(@RequestParam String categoryName) {
        List<Transaction> transactions = transactionRepo.findByCategoryName(categoryName);
        if (transactions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/transactions/by-date")
    public ResponseEntity<List<Transaction>> getTransactionsByDate(@RequestParam LocalDate startdate, @RequestParam LocalDate enddate) {
        List<Transaction> transactions = transactionRepo.findByDateBetween(startdate, enddate);
        if (transactions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/transactions/summary")
    public ResponseEntity<FinancialSummaryDTO> getTransactionsSummary(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        BigDecimal totalIncome = transactionRepo.findTotalIncome(startDate, endDate);
        BigDecimal totalExpense = transactionRepo.findTotalExpense(startDate, endDate);
        BigDecimal netBalance = totalIncome.subtract(totalExpense);

        FinancialSummaryDTO summary = new FinancialSummaryDTO(totalIncome, totalExpense, netBalance);

        return ResponseEntity.ok(summary);
    }

    /**
     * POST /transactions - Creates a new transaction.
     *
     * @param transaction The transaction data to be created.
     * @return A ResponseEntity containing the created transaction, with the Location header set.
     */
    @CrossOrigin(origins = "*")
    @PostMapping("/transactions")
    public ResponseEntity<?> createTransaction(@RequestBody Transaction transaction) {

        // Constructing the URI for the newly created resource.
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(transactionRepo.save(transaction).getId())
                .toUri();

        return ResponseEntity.created(location).body(transactionRepo.save(transaction));
    }

    /**
     * PUT /transactions/{id} - Updates an existing transaction.
     *
     * @param id The ID of the transaction to update.
     * @param transaction The updated transaction data.
     * @return A ResponseEntity containing the updated transaction, or null if not found.
     */
    @CrossOrigin(origins = "*")
    @PutMapping("/transactions/{id}")
    public ResponseEntity<?> updateTransaction(@PathVariable int id, @RequestBody Transaction transaction) {
        Optional<Transaction> transactionOptional = transactionRepo.findById(id);
        if (transactionOptional.isPresent()) {
            Transaction transactionToUpdate = transactionOptional.get();
            transactionToUpdate.setType(transaction.getType());
            transactionToUpdate.setAmount(transaction.getAmount());
            transactionToUpdate.setDate(transaction.getDate());
            transactionToUpdate.setDescription(transaction.getDescription());
            transactionToUpdate.setCategory(transaction.getCategory());
            return ResponseEntity.ok(transactionRepo.save(transactionToUpdate));
        }
        return null;
    }

    /**
     * DELETE /transactions/{id} - Deletes a transaction by its ID.
     *
     * @param id The ID of the transaction to delete.
     * @return A ResponseEntity indicating that the transaction has been deleted.
     */
    @CrossOrigin(origins = "*")
    @DeleteMapping("/transactions/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable int id) {
        transactionRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
