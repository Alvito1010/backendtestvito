package com.testvito.backendtestvito.controller;

import com.testvito.backendtestvito.dto.SettlementResponse;
import com.testvito.backendtestvito.service.SettlementService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/groups/{groupId}/settlement")
public class SettlementController {

    private final SettlementService settlementService;

    public SettlementController(
            SettlementService settlementService) {

        this.settlementService = settlementService;
    }

    @GetMapping
    public SettlementResponse getSettlement(
            @PathVariable Long groupId) {

        return settlementService.getSettlement(groupId);
    }
}