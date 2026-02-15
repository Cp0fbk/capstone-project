package com.project.evgo.payment.request;

import com.project.evgo.sharedkernel.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentRequest {
    private Long invoiceId;
    private PaymentMethod paymentMethod;
    private String description;
}
