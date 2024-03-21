package com.example.accountmanagement.controller;

import com.example.accountmanagement.domain.Account;
import com.example.accountmanagement.dto.CreateAccount;
import com.example.accountmanagement.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/account")
    public CreateAccount.Response createAccount(
            @RequestBody @Valid CreateAccount.Request request
            ) {

        return CreateAccount.Response.from(
                accountService.createAccount(
                    request.getUserId(),
                    request.getInitialBalance()
                )
        );
    }

    @GetMapping("/account/{id}")
    public Account getAccount(
            @PathVariable Long id) {
        return accountService.getAccount(id);
    }
}
