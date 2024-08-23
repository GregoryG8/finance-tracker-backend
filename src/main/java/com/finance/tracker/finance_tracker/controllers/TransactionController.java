package com.finance.tracker.finance_tracker.controllers;

import com.finance.tracker.finance_tracker.entities.Transaction;
import com.finance.tracker.finance_tracker.repo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class TransactionController {

    @Autowired
    private TransactionRepo transactionRepo;

    @GetMapping("/transactions")
    public ResponseEntity<?> getAllTransactions() {
        List<Transaction> transactions = transactionRepo.findAll();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/transactions/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable int id) {
        for (Transaction transaction : transactionRepo.findAll()) {
            if (transaction.getId() == id) {
                return ResponseEntity.ok(transaction);
            }
        }
        return null;
    }

    @PostMapping("/transactions")
    public ResponseEntity<?> createTransaction(@RequestBody Transaction transaction) {

        //Obteniendo URL de servicio.
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(transactionRepo.save(transaction).getId())
                .toUri();

        return ResponseEntity.created(location).body(transactionRepo.save(transaction));
    }

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

    @DeleteMapping("/transactions/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable int id) {
        transactionRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
