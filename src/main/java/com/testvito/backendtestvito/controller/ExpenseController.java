package com.testvito.backendtestvito.controller;

import com.testvito.backendtestvito.dto.AddExpenseRequest;
import com.testvito.backendtestvito.dto.ExpenseResponse;
import com.testvito.backendtestvito.service.ExpenseService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/groups/{groupId}/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public ResponseEntity<ExpenseResponse> addExpense(
            @PathVariable Long groupId,
            @Valid @RequestBody AddExpenseRequest request) {

        ExpenseResponse response =
                expenseService.addExpense(groupId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}