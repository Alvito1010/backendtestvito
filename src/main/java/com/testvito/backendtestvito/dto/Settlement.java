package com.testvito.backendtestvito.dto;

import java.math.BigDecimal;

public record Settlement(
        Long fromParticipantId,
        Long toParticipantId,
        BigDecimal amount
) {
}