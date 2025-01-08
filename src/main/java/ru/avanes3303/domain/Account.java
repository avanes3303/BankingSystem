package ru.avanes3303.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;
import java.util.List;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "Password cannot be empty")
    private String password;

    @Column(nullable = false)
    @NotNull(message = "Balance cannot be null")
    @DecimalMin(value = "0.0", message = "Balance must be positive")
    private BigDecimal balance;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public BigDecimal getBalance() { return balance; }

    public void setBalance(BigDecimal balance) { this.balance = balance; }

    public List<Transaction> getTransactions() { return transactions; }

    public void setTransactions(List<Transaction> transactions) { this.transactions = transactions; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        this.balance = this.balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        if (this.balance.compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        this.balance = this.balance.subtract(amount);
    }
}
