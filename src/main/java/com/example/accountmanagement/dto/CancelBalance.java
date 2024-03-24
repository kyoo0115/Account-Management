package com.example.accountmanagement.dto;

import com.example.accountmanagement.type.TransactionResultType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

public class CancelBalance {
    /**
     * {
     *  transactionId: "a3b5awea5e3rgaergaer",
     *  accountNumber: "1000000001",
     *  amount: 1000
     * }
     */
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Request {
        @NotBlank
        private String transactionId;

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
    public static class Response {
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
