package com.project.evgo.payment.internal;

import com.project.evgo.sharedkernel.enums.InvoiceStatus;
import com.project.evgo.sharedkernel.exceptions.AppException;
import com.project.evgo.sharedkernel.enums.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private InvoiceRepository invoiceRepository; // We need to mock looking up the invoice

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private com.project.evgo.payment.internal.gateway.PaymentGatewayService paymentGatewayService;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    // ==========================================
    // 1. Test createPaymentUrl (Initiate Payment)
    // ==========================================

    @Test
    @DisplayName("Should create payment URL successfully when invoice exists")
    void createPaymentUrl_ValidInvoice_ReturnsUrl() {
        // Given
        Long invoiceId = 1L;
        Invoice invoice = new Invoice();
        invoice.setId(invoiceId);
        invoice.setTotalCost(BigDecimal.valueOf(50000));
        invoice.setStatus(InvoiceStatus.PENDING);

        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.of(invoice));

        // Mock gateway response
        when(paymentGatewayService.createPaymentUrl(any(), any(), any())).thenReturn(
                "https://test-payment.momo.vn/v2/gateway/api/create?partnerCode=MOMO&orderId=INV1_123&amount=50000");

        // When
        String paymentUrl = paymentService.createPaymentUrl(invoiceId);

        // Then
        assertThat(paymentUrl).startsWith("https://test-payment.momo.vn/");
    }

    @Test
    @DisplayName("Should throw exception when invoice not found")
    void createPaymentUrl_InvoiceNotFound_ThrowsException() {
        // Given
        Long invoiceId = 999L;
        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> paymentService.createPaymentUrl(invoiceId))
                .isInstanceOf(AppException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.INVOICE_NOT_FOUND);
    }

    @Test
    @DisplayName("Should throw exception when invoice is already paid")
    void createPaymentUrl_InvoicePaid_ThrowsException() {
        // Given
        Long invoiceId = 1L;
        Invoice invoice = new Invoice();
        invoice.setId(invoiceId);
        invoice.setStatus(InvoiceStatus.PAID);

        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.of(invoice));

        // When & Then
        assertThatThrownBy(() -> paymentService.createPaymentUrl(invoiceId))
                .isInstanceOf(AppException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.INVOICE_ALREADY_PAID);
    }

    // ==========================================
    // 2. Test handleCallback (IPN from Gateway)
    // ==========================================

    /*
     * @Test
     * 
     * @DisplayName("Should update payment status to SUCCESS when gateway callback is valid"
     * )
     * void handleCallback_Success_UpdatesStatus() {
     * // ...
     * }
     */
}
