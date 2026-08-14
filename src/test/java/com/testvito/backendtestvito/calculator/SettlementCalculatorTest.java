package com.testvito.backendtestvito.calculator;

import com.testvito.backendtestvito.dto.Settlement;
import com.testvito.backendtestvito.entity.BillGroup;
import com.testvito.backendtestvito.entity.Expense;
import com.testvito.backendtestvito.entity.Participant;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SettlementCalculatorTest {

    @Test
    void shouldCalculateSimpleSettlement() {

        BillGroup group = new BillGroup("Bali Trip");

        Participant alice = new Participant("Alice", group);
        Participant bob = new Participant("Bob", group);
        Participant charlie = new Participant("Charlie", group);

        alice.setId(1L);
        bob.setId(2L);
        charlie.setId(3L);

        Expense hotel = new Expense();

        hotel.setDescription("Hotel");
        hotel.setAmount(new BigDecimal("300.00"));
        hotel.setGroup(group);
        hotel.setPaidBy(alice);
        hotel.setSplitAmong(
                Set.of(alice, bob, charlie)
        );

        Expense dinner = new Expense();

        dinner.setDescription("Dinner");
        dinner.setAmount(new BigDecimal("90.00"));
        dinner.setGroup(group);
        dinner.setPaidBy(bob);
        dinner.setSplitAmong(
                Set.of(alice, bob, charlie)
        );

        SettlementCalculator calculator =
                new SettlementCalculator();

        System.out.println("Alice ID: " + alice.getId());
        System.out.println("Bob ID: " + bob.getId());
        System.out.println("Charlie ID: " + charlie.getId());

        System.out.println("Hotel payer: " + hotel.getPaidBy().getId());
        System.out.println("Hotel participants: " +
        hotel.getSplitAmong().stream()
                .map(Participant::getId)
                .toList());

        List<Settlement> settlements =
                calculator.calculate(List.of(hotel, dinner));

        assertEquals(2, settlements.size());

        Settlement first = settlements.get(0);
        Settlement second = settlements.get(1);

        assertEquals(
                new BigDecimal("40.00"),
                first.amount()
        );

        assertEquals(
                new BigDecimal("130.00"),
                second.amount()
        );
    }
}