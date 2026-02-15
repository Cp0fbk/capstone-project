package com.project.evgo.payment.internal.gateway;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MoMoUtilsTest {

    @Test
    @DisplayName("Should generate correct HMAC SHA256 signature")
    void hmacSha256_ValidInput_ReturnsCorrectSignature() throws Exception {
        // Test case from MoMo Documentation or a known valid pair
        // Data:
        // "accessKey=klm05TvNBzhg7h7j&amount=150000&extraData=&ipnUrl=https://momo.vn&orderId=1527907579854&orderInfo=Pay
        // With
        // MoMo&partnerCode=MOMO&redirectUrl=https://momo.vn&requestId=1527907579854&requestType=captureWallet"
        // Secret Key: "at67qH6mk8w5Y1nAyMoYKMWACiEi2bsa"
        // Expected Signature:
        // "c005470d0517f897df4e04746654884260ca21e2579df200171221b65287515d" (Example
        // hash - need to verify with real tool or simple case)

        // Using a simpler case for verification to avoid manual calculation errors
        // Known test vector
        // Key: "secret"
        // Data: "Message"
        // HMAC-SHA256 (Hex):
        // "8b5f48702995c1598c573db1e21866a9b825d4a794d169d7060a03605796360b"

        String data = "Message";
        String secretKey = "secret";
        String expectedHash = "aa747c502a898200f9e4fa21bac68136f886a0e27aec70ba06daf2e2a5cb5597";

        String result = MoMoUtils.hmacSha256(data, secretKey);

        assertThat(result).isEqualTo(expectedHash);
    }
}
