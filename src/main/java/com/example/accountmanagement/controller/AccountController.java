package com.example.accountmanagement.controller;

import com.example.accountmanagement.domain.Account;
import com.example.accountmanagement.dto.AccountInfo;
import com.example.accountmanagement.dto.CreateAccount;
import com.example.accountmanagement.dto.DeleteAccount;
import com.example.accountmanagement.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @DeleteMapping("/account")
    public DeleteAccount.Response deleteAccount(
            @RequestBody @Valid DeleteAccount.Request request
    ) {

        return DeleteAccount.Response.from(
                accountService.deleteAccount(
                        request.getUserId(),
                        request.getAccountNumber()
                )
        );
    }

    @GetMapping("/account")
    public List<AccountInfo> getAccountsByUserId(
        @RequestParam("user_id") Long userId
    ) {
        return accountService.getAccountsByUserId(userId)
                .stream().map(accountDTO ->
                        AccountInfo.builder()
                        .accountNumber(accountDTO.getAccountNumber())
                        .balance(accountDTO.getBalance())
                        .build())
                .collect(Collectors.toList());
    }

    @GetMapping("/account/{id}")
    public Account getAccount(
            @PathVariable Long id) {
        return accountService.getAccount(id);
    }
}
