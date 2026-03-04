package com.project.evgo.payment.internal;

import com.project.evgo.payment.response.PaymentResponse;
import com.project.evgo.sharedkernel.enums.PaymentStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Converter for Transaction entity to DTO.
 */
@Component
public class PaymentDtoConverter {

    public PaymentResponse toResponse(Transaction transaction) {
        return PaymentResponse.builder()
                .id(transaction.getId())
                .invoiceId(transaction.getInvoice().getId())
                .userId(transaction.getInvoice().getUserId())
                .amount(transaction.getAmount())
                .transactionId(transaction.getGatewayTransactionId())
                .status(PaymentStatus.valueOf(transaction.getStatus().name()))
                .createdAt(transaction.getCreatedAt())
                .build();
    }

    public List<PaymentResponse> toResponseList(List<Transaction> transactions) {
        return transactions.stream()
                .map(this::toResponse)
                .toList();
    }

    public Optional<PaymentResponse> toResponse(Optional<Transaction> transaction) {
        return transaction.map(this::toResponse);
    }
}
