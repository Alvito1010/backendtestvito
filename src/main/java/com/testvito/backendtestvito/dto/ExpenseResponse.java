package com.testvito.backendtestvito.dto;

import java.math.BigDecimal;
import java.util.List;

public record ExpenseResponse(
        Long id,
        String description,
        BigDecimal amount,
        Long paidBy,
        List<Long> splitAmong
) {
}