package ru.avanes3303.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.avanes3303.domain.Account;
import ru.avanes3303.dto.AccountDTO;
import ru.avanes3303.repository.AccountRepository;
import ru.avanes3303.security.JwtUtil;

import java.math.BigDecimal;

@Service
public class AuthenticationService {

    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(AccountRepository accountRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public void registerAccount(AccountDTO accountDTO) {
        if (accountRepository.findByName(accountDTO.name()) != null) {
            throw new IllegalArgumentException("User already exists");
        }

        Account account = new Account();
        account.setName(accountDTO.name());
        account.setPassword(passwordEncoder.encode(accountDTO.password()));
        account.setBalance(BigDecimal.ZERO);

        accountRepository.save(account);
    }

    public String login(AccountDTO accountDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(accountDTO.name(), accountDTO.password())
        );

        return jwtUtil.generateToken(authentication.getName());
    }
}
