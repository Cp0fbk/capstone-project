package com.project.evgo.payment.internal.gateway;

import com.project.evgo.payment.internal.config.MoMoConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class MoMoService implements PaymentGatewayService {

    private final MoMoConfig moMoConfig;

    public String createPaymentUrl(String orderId, String orderInfo, BigDecimal amount) {
        // TODO: Implement actual MoMo API call with signature (HMAC SHA256)
        // For TDD Green Phase, we return a constructed URL to verify the flow.

        log.info("Creating MoMo payment for Order: {}, Amount: {}", orderId, amount);

        // Mock URL structure
        return String.format("https://test-payment.momo.vn/v2/gateway/api/create?partnerCode=%s&orderId=%s&amount=%s",
                moMoConfig.getPartnerCode(), orderId, amount);
    }
}
