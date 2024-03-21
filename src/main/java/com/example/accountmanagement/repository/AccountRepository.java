package com.example.accountmanagement.repository;

import com.example.accountmanagement.domain.Account;
import com.example.accountmanagement.domain.AccountUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findFirstByOrderByIdDesc();

    Integer countByAccountUser(AccountUser accountUser);
}
