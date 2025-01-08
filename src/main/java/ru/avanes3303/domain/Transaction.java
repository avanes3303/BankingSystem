package ru.avanes3303.domain;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(nullable = false)
    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.0", message = "Amount must be positive")
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Transaction type cannot be null")
    private TransactionType type;

    @Column(nullable = false)
    @NotNull(message = "Timestamp cannot be null")
    private LocalDateTime timestamp;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Account getAccount() { return account; }

    public void setAccount(Account account) { this.account = account; }

    public BigDecimal getAmount() { return amount; }

    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public TransactionType getType() { return type; }

    public void setType(TransactionType type) { this.type = type; }

    public LocalDateTime getTimestamp() { return timestamp; }

    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
