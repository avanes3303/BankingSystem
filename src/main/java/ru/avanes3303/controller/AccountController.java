package ru.avanes3303.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.avanes3303.dto.TransactionDTO;
import ru.avanes3303.service.AccountService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/1/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/balance")
    public ResponseEntity<BigDecimal> getBalance(Authentication authentication) {
        String username = authentication.getName();
        BigDecimal balance = accountService.getBalance(username);
        return ResponseEntity.ok(balance);
    }

    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(Authentication authentication, @RequestParam BigDecimal amount) {
        String username = authentication.getName();
        accountService.deposit(username, amount);
        return ResponseEntity.ok("Deposit successful");
    }

    @GetMapping("/history")
    public ResponseEntity<List<TransactionDTO>> getTransactionHistory(Authentication authentication) {
        String username = authentication.getName();
        List<TransactionDTO> transactions = accountService.getHistory(username);
        return ResponseEntity.ok(transactions);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(Authentication authentication, @RequestParam BigDecimal amount) {
        String username = authentication.getName();
        accountService.withdraw(username, amount);
        return ResponseEntity.ok("Withdrawal successful");
    }
}

