package ru.avanes3303.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.avanes3303.dto.AccountDTO;
import ru.avanes3303.service.AuthenticationService;

@RestController
@RequestMapping("/api/1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid AccountDTO accountDto) {
        authenticationService.registerAccount(accountDto);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid AccountDTO accountDTO) {
        String token =  authenticationService.login(accountDTO);
        return ResponseEntity.ok(token);
    }
}
