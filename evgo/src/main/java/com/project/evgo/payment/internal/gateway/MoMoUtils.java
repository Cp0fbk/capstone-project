package com.project.evgo.payment.internal.gateway;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class MoMoUtils {

    private static final String HMAC_SHA256 = "HmacSHA256";

    public static String hmacSha256(String data, String secretKey) throws Exception {
        Mac hmacSha256 = Mac.getInstance(HMAC_SHA256);
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), HMAC_SHA256);
        hmacSha256.init(secretKeySpec);
        byte[] bytes = hmacSha256.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return toHexString(bytes);
    }

    private static String toHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
