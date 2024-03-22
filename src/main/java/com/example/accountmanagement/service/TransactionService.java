package com.example.accountmanagement.service;

import com.example.accountmanagement.domain.Account;
import com.example.accountmanagement.domain.AccountUser;
import com.example.accountmanagement.domain.Transaction;
import com.example.accountmanagement.dto.TransactionDTO;
import com.example.accountmanagement.exception.AccountException;
import com.example.accountmanagement.repository.AccountRepository;
import com.example.accountmanagement.repository.AccountUserRepository;
import com.example.accountmanagement.repository.TransactionRepository;
import com.example.accountmanagement.type.AccountStatus;

import com.example.accountmanagement.type.ErrorCode;
import com.example.accountmanagement.type.TransactionResultType;
import com.example.accountmanagement.type.TransactionType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static com.example.accountmanagement.type.ErrorCode.*;
import static com.example.accountmanagement.type.TransactionResultType.*;
import static com.example.accountmanagement.type.TransactionType.USE;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final AccountUserRepository accountUserRepository;

    @Transactional
    public TransactionDTO useBalance(Long userId, String accountNumber, Long amount) {
        AccountUser user = accountUserRepository.findById(userId)
                .orElseThrow(() -> new AccountException(USER_NOT_FOUND));

        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountException(ACCOUNT_NOT_FOUND));

        validateUseBalance(user, account, amount);

        account.useBalance(amount);

        return TransactionDTO.fromEntity(saveAndGetTransaction(SUCCESS, account, amount));
    }

    private void validateUseBalance(AccountUser user, Account account, Long amount) {
        if(!Objects.equals(user.getId(), account.getAccountUser().getId())) {
            throw new AccountException(USER_ACCOUNT_UN_MATCH);
        }
        if(account.getAccountStatus() != AccountStatus.IN_USE) {
            throw new AccountException(ACCOUNT_ALREADY_UNREGISTERED);
        }
        if(account.getBalance() < amount) {
            throw new AccountException(AMOUNT_EXCEED_BALANCE);
        }
    }

    @Transactional
    public void saveFailedUseTransaction(String accountNumber, Long amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountException(ACCOUNT_NOT_FOUND));

        saveAndGetTransaction(FAIL, account, amount);
    }

    private Transaction saveAndGetTransaction(TransactionResultType transactionResultType, Account account, Long amount) {
        return transactionRepository.save(
                Transaction.builder()
                        .transactionType(USE)
                        .transactionResultType(transactionResultType)
                        .account(account)
                        .amount(amount)
                        .balanceSnapshot(account.getBalance())
                        .transactionId(UUID.randomUUID().toString().replace("-", ""))
                        .transactedAt(LocalDateTime.now())
                        .build()
        );
    }
}
