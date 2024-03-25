package com.example.accountmanagement.service;

import com.example.accountmanagement.exception.AccountException;
import com.example.accountmanagement.type.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class LockService {
    private final RedissonClient redissonClient;

    public void lock(String accountNumber) {
        RLock lock = redissonClient.getLock(getLockKey(accountNumber));
        log.debug("Trying lock for accountNumber : {} ", accountNumber);

        try{
            boolean isLock = lock.tryLock(1, 15, TimeUnit.SECONDS);
            if(!isLock) {
                log.error("==========Lock Acquisition Failed=========");
                throw new AccountException(ErrorCode.ACCOUNT_TRANSACTION_LOCK);
            }
        } catch (AccountException e) {
            throw e;
        } catch (Exception e) {
            log.error("Redis Lock Failed", e);
        }
    }

    public void unlock(String accountNumber) {
        log.debug("Unlock for accountNumber : {} ", accountNumber);
        redissonClient.getLock(getLockKey(accountNumber));
    }

    private String getLockKey(String accountNumber) {
        return "ACLK:" + accountNumber;
    }
}
