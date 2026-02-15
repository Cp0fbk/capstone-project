package com.project.evgo.payment;

import com.project.evgo.payment.response.PaymentResponse;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for payment management.
 * Public API - accessible by other modules.
 */
public interface PaymentService {

    Optional<PaymentResponse> findById(Long id);

    Optional<PaymentResponse> findByInvoiceId(Long invoiceId);

    String createPaymentUrl(Long invoiceId);

    List<PaymentResponse> findByUserId(Long userId);
}
