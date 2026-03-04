package com.project.evgo.payment.response;

public record MoMoPaymentResponse(
        String partnerCode,
        String invoiceId,
        String requestId,
        Long amount,
        Long transId,
        Integer resultCode,
        String message,
        String payUrl,
        String deeplink,
        String qrCodeUrl) {

}
