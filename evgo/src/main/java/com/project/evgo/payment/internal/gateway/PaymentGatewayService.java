package com.project.evgo.payment.internal.gateway;

import java.math.BigDecimal;

public interface PaymentGatewayService {
    String createPaymentUrl(String orderId, String orderInfo, BigDecimal amount);
}
