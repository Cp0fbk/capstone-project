package com.project.evgo.payment.internal.web;

import com.project.evgo.payment.PaymentService;
import com.project.evgo.payment.response.PaymentResponse;
import com.project.evgo.sharedkernel.dto.ApiResponse;
import com.project.evgo.sharedkernel.enums.ErrorCode;
import com.project.evgo.sharedkernel.exceptions.AppException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for payment management.
 */
@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Tag(name = "Payments", description = "Payment management APIs")
public class PaymentController {

        private final PaymentService paymentService;

        @GetMapping("/{id}")
        @Operation(summary = "Get payment by ID")
        public ResponseEntity<ApiResponse<PaymentResponse>> getById(@PathVariable Long id) {
                var result = paymentService.findById(id)
                                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
                return ResponseEntity.ok(ApiResponse.<PaymentResponse>builder()
                                .status(HttpStatus.OK.value())
                                .message("Success")
                                .data(result)
                                .build());
        }

        @GetMapping("/invoice/{invoiceId}")
        @Operation(summary = "Get payment by invoice ID")
        public ResponseEntity<ApiResponse<PaymentResponse>> getByInvoiceId(
                        @PathVariable Long invoiceId) {
                var result = paymentService.findByInvoiceId(invoiceId)
                                .orElseThrow(() -> new AppException(ErrorCode.INVOICE_NOT_FOUND));
                return ResponseEntity.ok(ApiResponse.<PaymentResponse>builder()
                                .status(HttpStatus.OK.value())
                                .message("Success")
                                .data(result)
                                .build());
        }

        @GetMapping("/user/{userId}")
        @Operation(summary = "Get payments by user ID")
        public ResponseEntity<ApiResponse<List<PaymentResponse>>> getByUserId(
                        @PathVariable Long userId) {
                var result = paymentService.findByUserId(userId);
                return ResponseEntity.ok(ApiResponse.<List<PaymentResponse>>builder()
                                .status(HttpStatus.OK.value())
                                .message("Success")
                                .data(result)
                                .build());
        }
}
