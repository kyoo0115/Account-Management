package com.example.accountmanagement.dto;

import com.example.accountmanagement.aop.AccountLockIdInterface;
import com.example.accountmanagement.type.TransactionResultType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

public class UseBalance {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Request {
        @NotNull
        @Min(1)
        private Long userId;

        @NotBlank
        @Size (min = 10, max = 10)
        private String accountNumber;

        @NotNull
        @Min(10)
        @Max(1000_000_000)
        private Long amount;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response implements AccountLockIdInterface {
        private String accountNumber;
        private TransactionResultType transactionResult;
        private String transactionId;
        private Long amount;
        private LocalDateTime transactedAt;

        public static Response from(TransactionDTO transactionDTO) {
            return Response.builder()
                    .accountNumber(transactionDTO.getAccountNumber())
                    .transactionResult(transactionDTO.getTransactionResultType())
                    .transactionId(transactionDTO.getTransactionId())
                    .amount(transactionDTO.getAmount())
                    .transactedAt(transactionDTO.getTransactedAt())
                    .build();
        }
    }
}
