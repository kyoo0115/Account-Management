package com.example.accountmanagement.dto;

import com.example.accountmanagement.type.TransactionResultType;
import com.example.accountmanagement.type.TransactionType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QueryTransactionResponse {
    private String accountNumber;
    private TransactionType transactionType;
    private TransactionResultType transactionResult;
    private String transactionId;
    private Long amount;
    private LocalDateTime transactedAt;

    public static QueryTransactionResponse from(TransactionDTO transactionDTO) {
        return QueryTransactionResponse.builder()
                .accountNumber(transactionDTO.getAccountNumber())
                .transactionType(transactionDTO.getTransactionType())
                .transactionResult(transactionDTO.getTransactionResultType())
                .transactionId(transactionDTO.getTransactionId())
                .amount(transactionDTO.getAmount())
                .transactedAt(transactionDTO.getTransactedAt())
                .build();
    }
}
