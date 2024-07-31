package com.apibanking;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Test;

import com.apibanking.accountopening.savings.dto.AccountType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import static org.hamcrest.Matchers.*;

@QuarkusTest
class PaymentResourceTest {

    private static String sampleRequestBody;

    private String readAndReplacePlaceholders(String fileName, Map<String, String> replacements) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            if (inputStream == null) {
                throw new IllegalArgumentException("File not found: " + fileName);
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            // Replace placeholders
            String content = sb.toString();
            for (Map.Entry<String, String> entry : replacements.entrySet()) {
                content = content.replace(entry.getKey(), entry.getValue());
            }
            return content;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to read or process the JSON file", e);
        }
    }

    private void openAccount(String requestFileName, String panNo, String accountNo, String debitAccountNo,
            String customerId, String accountType) {
        if (accountType == AccountType.FixedDeposit.name()) {
            Map<String, String> replacements = new HashMap<String, String>();
            replacements.put("${debitAccountNo}", debitAccountNo);
            replacements.put("${customerId}", customerId);
            sampleRequestBody = readAndReplacePlaceholders(requestFileName, replacements);
        } else {
            Map<String, String> replacements = new HashMap<String, String>();
            replacements.put("${panNo}", panNo);
            sampleRequestBody = readAndReplacePlaceholders(requestFileName, replacements);
        }
        String applicationNo = given()
                .body(sampleRequestBody)
                .contentType(ContentType.JSON)
                .when().post("/api/v1/accountservice/" + accountType.toLowerCase())
                .then()
                .statusCode(200)
                .body("applicationNo", is(notNullValue()))
                .body("productCode", is(notNullValue()))
                .body("status", is(notNullValue()))
                .body("type", is(notNullValue()))
                .body("type", is(accountType))
                .body("status", is("UnderInvestigation")).extract().path("applicationNo");
        Map<String, String> replacements = new HashMap<String, String>();
        replacements.put("${applicationNo}", applicationNo);
        replacements.put("${accountNo}", accountNo);
        replacements.put("${customerId}", customerId);
        replacements.put("${accountType}", accountType);
        String data = readAndReplacePlaceholders("update-account-opening-request-Active.json", replacements);
        given()
                .body(data)
                .contentType(ContentType.JSON)
                .when().patch("/api/v1/accountservice/webhook-update")
                .then()
                .statusCode(202);

        given()
                .when().get("/api/v1/accountservice/" + applicationNo)
                .then()
                .statusCode(200)
                .body("customerId", is(notNullValue()))
                .body("status", is("Active"))
                .body("type", is(accountType));

    }

    private String getCustomerId() {
        return UUID.randomUUID().toString().replace("-", "").toString();
    }

    private String getAccountNo() {
        return String.valueOf(1000000 + new Random().nextInt(9000000))
                + String.valueOf(100000000 + new Random().nextInt(900000000));
    }

    private String getPanNo() {
        return "BHJKM" + String.valueOf(1000 + new Random().nextInt(9000)) + "P";
    }

    @Test
    public void testPaymentSuccess() {
        String accountNo = getAccountNo();
        String customerId = getCustomerId();
        openAccount("saving-request.json", getPanNo(), accountNo, null, customerId,
                AccountType.Saving.name());

        Map<String, String> replacements = new HashMap<String, String>();
        replacements.put("${accountNo}", accountNo);
        replacements.put("${customerId}", customerId);
        replacements.put("${amount}", "1000");
        sampleRequestBody = readAndReplacePlaceholders("payment.json", replacements);

        given()
                .body(sampleRequestBody)
                .contentType(ContentType.JSON)
                .when().post("/api/v1/accountservice/payment")
                .then()
                .statusCode(200)
                .body("remarks", is("Payment processed successfully"))
                .body("status", is("Success"))
                .body("transactionReferenceNo", is(notNullValue()));

    }

    @Test
    public void testPaymentFailure() {
        String accountNo = getAccountNo();
        String customerId = getCustomerId();
        openAccount("saving-request.json", getPanNo(), accountNo, null, customerId,
                AccountType.Saving.name());

        Map<String, String> replacements = new HashMap<String, String>();
        replacements.put("${accountNo}", accountNo);
        replacements.put("${customerId}", customerId);
        replacements.put("${amount}", "-1000");
        sampleRequestBody = readAndReplacePlaceholders("payment.json", replacements);

        given()
                .body(sampleRequestBody)
                .contentType(ContentType.JSON)
                .when().post("/api/v1/accountservice/payment")
                .then()
                .statusCode(400)
                .body("remarks", is("Payment processing failed"))
                .body("status", is("Failed"))
                .body("transactionReferenceNo", is(notNullValue()));

    }

    @Test
    public void testPaymentWithBadAccountNo() {
        String accountNo = getAccountNo();
        String customerId = getCustomerId();
        openAccount("saving-request.json", getPanNo(), accountNo, null, customerId,
                AccountType.Saving.name());
        accountNo = getAccountNo();
        Map<String, String> replacements = new HashMap<String, String>();
        replacements.put("${accountNo}", accountNo);
        replacements.put("${customerId}", customerId);
        replacements.put("${amount}", "1000");
        sampleRequestBody = readAndReplacePlaceholders("payment.json", replacements);

        given()
                .body(sampleRequestBody)
                .contentType(ContentType.JSON)
                .when().post("/api/v1/accountservice/payment")
                .then()
                .statusCode(404)
                .body("detail", is("No Record Found for customerId " + customerId + " and accountNo " + accountNo))
                .body("message", is("NOT_FOUND"));

    }
}