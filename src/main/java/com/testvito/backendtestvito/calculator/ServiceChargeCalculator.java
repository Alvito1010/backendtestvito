package com.testvito.backendtestvito.calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ServiceChargeCalculator {

    private final String githubUsername;

    public ServiceChargeCalculator(String githubUsername) {
        this.githubUsername = githubUsername.toLowerCase();
    }

    public int calculatePercentage() {

        int sum = githubUsername
                .chars()
                .sum();

        return sum % 10;
    }

    public BigDecimal calculateAmount(BigDecimal totalExpenses) {

        BigDecimal percentage = BigDecimal.valueOf(
                calculatePercentage()
        );

        return totalExpenses
                .multiply(percentage)
                .divide(
                        BigDecimal.valueOf(100),
                        2,
                        RoundingMode.HALF_UP
                );
    }
}