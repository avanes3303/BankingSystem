package ru.avanes3303.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.avanes3303.domain.Account;
import ru.avanes3303.domain.Transaction;
import ru.avanes3303.domain.TransactionType;
import ru.avanes3303.dto.TransactionDTO;
import ru.avanes3303.repository.AccountRepository;
import ru.avanes3303.repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public BigDecimal getBalance(String username) {
        Account account = accountRepository.findByName(username);
        if (account == null) throw new IllegalArgumentException("Account not found");
        return account.getBalance();
    }

    @Transactional
    public void deposit(String username, BigDecimal amount) {
        Account account = accountRepository.findByName(username);
        if (account == null) throw new IllegalArgumentException("Account not found");

        account.deposit(amount);
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);
    }

    @Transactional
    public void withdraw(String username, BigDecimal amount) {
        Account account = accountRepository.findByName(username);
        if (account == null) throw new IllegalArgumentException("Account not found");
        account.withdraw(amount);
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setType(TransactionType.WITHDRAWAL);
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);
    }

    public List<TransactionDTO> getHistory(String username) {
        Account account = accountRepository.findByName(username);
        if (account == null) throw new IllegalArgumentException("Account not found");

        List<Transaction> history = transactionRepository.findByAccount(account);

        return history.stream()
                .map(transaction -> new TransactionDTO(
                        transaction.getAmount(),
                        transaction.getType(),
                        transaction.getTimestamp()
                ))
                .collect(Collectors.toList());
    }
}
