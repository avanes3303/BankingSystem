package ru.avanes3303.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import ru.avanes3303.domain.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionDTO(
        @NotNull(message = "Amount cannot be null")
        @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
        BigDecimal amount,

        @NotNull(message = "Transaction type cannot be null")
        TransactionType type,

        @NotNull(message = "Timestamp cannot be null")
        LocalDateTime timestamp
) { }