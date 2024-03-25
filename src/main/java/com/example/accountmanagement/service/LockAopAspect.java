package com.example.accountmanagement.service;

import com.example.accountmanagement.dto.UseBalance;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class LockAopAspect {
    private final LockService lockService;

    @Around("@annotation(com.example.accountmanagement.aop.AccountLock) && args(request)")
    public Object aroundMethod(
            ProceedingJoinPoint pjp,
            UseBalance.Request request
    ) throws Throwable {
        //try lock
        lockService.lock(request.getAccountNumber());
        try{
            return pjp.proceed();
        } finally {
            //unlock
            lockService.unlock(request.getAccountNumber());
        }
    }
}
