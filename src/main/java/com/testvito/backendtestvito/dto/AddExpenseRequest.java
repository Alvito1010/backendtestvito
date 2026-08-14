package com.testvito.backendtestvito.dto;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AddExpenseRequest(
        String description,
        
        @NotNull
        @DecimalMin(value = "0.01")
        BigDecimal amount,

        @NotNull
        Long paidBy,

        @NotEmpty
        List<Long> splitAmong
) {
}