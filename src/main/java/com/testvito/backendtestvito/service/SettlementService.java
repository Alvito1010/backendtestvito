package com.testvito.backendtestvito.service;

import com.testvito.backendtestvito.calculator.ServiceChargeCalculator;
import com.testvito.backendtestvito.calculator.SettlementCalculator;
import com.testvito.backendtestvito.dto.Settlement;
import com.testvito.backendtestvito.dto.SettlementResponse;
import com.testvito.backendtestvito.entity.Expense;
import com.testvito.backendtestvito.exception.GroupNotFoundException;
import com.testvito.backendtestvito.repository.BillGroupRepository;
import com.testvito.backendtestvito.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SettlementService {

    private final BillGroupRepository billGroupRepository;
    private final ExpenseRepository expenseRepository;

    private final SettlementCalculator settlementCalculator =
            new SettlementCalculator();

    private final ServiceChargeCalculator serviceChargeCalculator =
        new ServiceChargeCalculator("Alvito1010");

    public SettlementService(
            BillGroupRepository billGroupRepository,
            ExpenseRepository expenseRepository) {

        this.billGroupRepository = billGroupRepository;
        this.expenseRepository = expenseRepository;
    }

    public SettlementResponse getSettlement(Long groupId) {

        billGroupRepository.findById(groupId)
                .orElseThrow(() ->
                        new GroupNotFoundException(groupId));

        List<Expense> expenses =
                expenseRepository.findByGroupId(groupId);

        BigDecimal totalExpenses = expenses.stream()
                .map(Expense::getAmount)
                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add
                );

        List<Settlement> settlements =
                settlementCalculator.calculate(expenses);

        BigDecimal serviceChargeAmount =
        serviceChargeCalculator.calculateAmount(totalExpenses);

        return new SettlementResponse(
                groupId,
                totalExpenses,
                settlements,
                serviceChargeCalculator.calculatePercentage(),
                serviceChargeAmount
        );
    }
}