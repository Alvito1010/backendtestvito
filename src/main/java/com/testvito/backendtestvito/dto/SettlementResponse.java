package com.testvito.backendtestvito.dto;

import java.math.BigDecimal;
import java.util.List;

public record SettlementResponse(
        Long groupId,
        BigDecimal totalExpenses,
        List<Settlement> settlements,
        int service_charge_pct,
        BigDecimal service_charge_amount
) {
}