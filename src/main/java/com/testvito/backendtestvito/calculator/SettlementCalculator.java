package com.testvito.backendtestvito.calculator;

import com.testvito.backendtestvito.dto.Settlement;
import com.testvito.backendtestvito.entity.Expense;
import com.testvito.backendtestvito.entity.Participant;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class SettlementCalculator {

    public List<Settlement> calculate(List<Expense> expenses) {

        Map<Long, BigDecimal> balances = new HashMap<>();

        for (Expense expense : expenses) {

            BigDecimal share = expense.getAmount()
                    .divide(
                            BigDecimal.valueOf(
                                    expense.getSplitAmong().size()
                            ),
                            2,
                            RoundingMode.HALF_UP
                    );

            Long payerId = expense.getPaidBy().getId();

            balances.merge(
                    payerId,
                    expense.getAmount(),
                    BigDecimal::add
            );

            for (Participant participant : expense.getSplitAmong()) {

                balances.merge(
                        participant.getId(),
                        share.negate(),
                        BigDecimal::add
                );
            }
        }

        return settleBalances(balances);
    }

    private List<Settlement> settleBalances(
            Map<Long, BigDecimal> balances) {

        List<Balance> creditors = new ArrayList<>();
        List<Balance> debtors = new ArrayList<>();

        for (Map.Entry<Long, BigDecimal> entry : balances.entrySet()) {

            if (entry.getValue().compareTo(BigDecimal.ZERO) > 0) {
                creditors.add(
                        new Balance(entry.getKey(), entry.getValue())
                );
            } else if (entry.getValue().compareTo(BigDecimal.ZERO) < 0) {
                debtors.add(
                        new Balance(
                                entry.getKey(),
                                entry.getValue().negate()
                        )
                );
            }
        }

        List<Settlement> settlements = new ArrayList<>();

        int creditorIndex = 0;
        int debtorIndex = 0;

        while (
                creditorIndex < creditors.size()
                        && debtorIndex < debtors.size()
        ) {

            Balance creditor = creditors.get(creditorIndex);
            Balance debtor = debtors.get(debtorIndex);

            BigDecimal amount = creditor.amount()
                    .min(debtor.amount());

            settlements.add(
                    new Settlement(
                            debtor.participantId(),
                            creditor.participantId(),
                            amount
                    )
            );

            creditor = new Balance(
                    creditor.participantId(),
                    creditor.amount().subtract(amount)
            );

            debtor = new Balance(
                    debtor.participantId(),
                    debtor.amount().subtract(amount)
            );

            creditors.set(creditorIndex, creditor);
            debtors.set(debtorIndex, debtor);

            if (creditor.amount().compareTo(BigDecimal.ZERO) == 0) {
                creditorIndex++;
            }

            if (debtor.amount().compareTo(BigDecimal.ZERO) == 0) {
                debtorIndex++;
            }
        }

        return settlements;
    }

    private record Balance(
            Long participantId,
            BigDecimal amount
    ) {
    }
}