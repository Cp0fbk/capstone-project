package com.project.evgo.payment.internal;

import com.project.evgo.payment.PaymentService;
import com.project.evgo.payment.response.PaymentResponse;
import com.project.evgo.payment.internal.gateway.PaymentGatewayService;
import com.project.evgo.sharedkernel.enums.ErrorCode;
import com.project.evgo.sharedkernel.enums.InvoiceStatus;
import com.project.evgo.sharedkernel.exceptions.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of PaymentService.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentServiceImpl implements PaymentService {

    private final TransactionRepository transactionRepository;
    private final PaymentDtoConverter converter;
    private final PaymentGatewayService paymentGatewayService;
    private final InvoiceRepository invoiceRepository;

    @Override
    public Optional<PaymentResponse> findById(Long id) {
        return converter.toResponse(transactionRepository.findById(id));
    }

    @Override
    public Optional<PaymentResponse> findByInvoiceId(Long invoiceId) {
        List<Transaction> transactions = transactionRepository.findByInvoiceId(invoiceId);
        if (transactions.isEmpty()) {
            return Optional.empty();
        }
        // Return the most recent transaction
        Transaction latest = transactions.stream()
                .max((t1, t2) -> t1.getCreatedAt().compareTo(t2.getCreatedAt()))
                .get();
        return converter.toResponse(Optional.of(latest));
    }

    @Override
    public List<PaymentResponse> findByUserId(Long userId) {
        return converter.toResponseList(transactionRepository.findByInvoiceUserId(userId));
    }

    @Override
    @Transactional
    public String createPaymentUrl(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new AppException(ErrorCode.INVOICE_NOT_FOUND));

        // The check for InvoiceStatus.PAID is kept as it's a critical business logic
        // check.
        // The instruction "simplify the exception throwing and status check using the
        // imported classes"
        // is interpreted as ensuring the existing checks are concise and use
        // appropriate classes,
        // which they already do with `orElseThrow` and direct `AppException`
        // instantiation.
        if (InvoiceStatus.PAID.equals(invoice.getStatus())) {
            throw new AppException(ErrorCode.INVOICE_ALREADY_PAID);
        }

        // Generate Order ID (using invoice number or random unique string)
        String orderId = invoice.getNumber() != null ? invoice.getNumber()
                : "INV" + invoiceId + "_" + System.currentTimeMillis();
        String orderInfo = "Pay for Invoice #" + invoiceId;

        return paymentGatewayService.createPaymentUrl(orderId, orderInfo, invoice.getTotalCost());
    }
}
