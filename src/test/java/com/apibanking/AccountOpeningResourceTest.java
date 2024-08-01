package com.apibanking;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Test;

import com.apibanking.accountopening.savings.dto.AccountType;
import com.apibanking.accountopening.service.AccontHelper;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;

@QuarkusTest
class AccountOpeningResourceTest {

    private static String sampleRequestBody;

    private String readAndReplacePlaceholders(String fileName, Map<String, String> replacements) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
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
    
    public static void readFile(String fileName) {
        // Load the sample request file from the classpath
        try (InputStream inputStream = AccountOpeningResourceTest.class.getClassLoader().getResourceAsStream(fileName);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            if (inputStream == null) {
                throw new IllegalArgumentException("File not found in the classpath");
            }
            // Read the file content into a String
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            sampleRequestBody = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to read sample request file", e);
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

    @Test
    public void testSavingAccountSuccess() {
        openAccount("saving-request.json", AccontHelper.getPanNo(), AccontHelper.getAccountNo(), null,AccontHelper.getCustomerId(), AccountType.Saving.name());
    }

    @Test
    public void testCurrentAccountSuccess() {
        openAccount("current-request.json", AccontHelper.getPanNo(),AccontHelper.getAccountNo(),  null, AccontHelper.getCustomerId(), AccountType.Current.name());
    }

    @Test
    public void testFixedDepositAccountFailedWithBadAccountNo() {
        readFile("fixeddeposit-request.json");
        given()
                .body(sampleRequestBody)
                .contentType(ContentType.JSON)
                .when().post("/api/v1/accountservice/fixeddeposit")
                .then()
                .statusCode(404)
                .body("detail", is(notNullValue()))
                .body("message", is(notNullValue()))
                .body("message", is("NOT_FOUND"));
    }

    @Test
    public void testFixedDepositAccountWithValidAccountNoAndCustomerId() {
        String accountNo = AccontHelper.getAccountNo();
        String customerId = AccontHelper.getCustomerId();
        openAccount("saving-request.json", AccontHelper.getPanNo(), accountNo, null, customerId, AccountType.Saving.name());
        openAccount("fixeddeposit-request.json", null, AccontHelper.getAccountNo(), accountNo, customerId, AccountType.FixedDeposit.name());
    }

    @Test
    public void testGetAccountWithBadCustomerId() {
        given()
                .queryParam("customerId", "456456")
                .when().get("/api/v1/accountservice")
                .then()
                .statusCode(200)
                .body(is("[]"));
    }

    @Test
    public void testGetAccountWithGoodCustomerId() {
        String customerId = AccontHelper.getCustomerId();
        openAccount("saving-request.json", AccontHelper.getPanNo(), AccontHelper.getAccountNo(), null,customerId, AccountType.Saving.name());

        given()
                .queryParam("customerId", customerId)
                .when().get("/api/v1/accountservice")
                .then()
                .statusCode(200)
                .body("customerId", is(notNullValue()))
                .body("productCode", is(notNullValue()))
                .body("status", is(notNullValue()))
                .body("type", is(notNullValue()));
    }

    @Test
    public void testGetAccountWithGoodCustomerIdAndGoodAccountNo() {
        String customerId = AccontHelper.getCustomerId();
        String accountNo = AccontHelper.getAccountNo();

        openAccount("saving-request.json", AccontHelper.getPanNo(), accountNo, null,customerId, AccountType.Saving.name());

        given()
                .queryParam("customerId", customerId)
                .queryParam("accountNo", accountNo)
                .when().get("/api/v1/accountservice")
                .then()
                .statusCode(200)
                .body("customerId", is(notNullValue()))
                .body("productCode", is(notNullValue()))
                .body("status", is(notNullValue()))
                .body("type", is(notNullValue()));
    }
    @Test
    public void testUpdateAccountStatus() {
        String customerId = AccontHelper.getCustomerId();
        String accountNo = AccontHelper.getAccountNo();

        openAccount("saving-request.json", AccontHelper.getPanNo(), accountNo, null,customerId, AccountType.Saving.name());
        Map<String, String> replacements = new HashMap<String, String>();
        replacements.put("${accountNo}", accountNo);
        replacements.put("${accountType}", AccountType.Saving.name());

        replacements.put("${customerId}", customerId);
        replacements.put("${email}", "abc@gmail.com");
        replacements.put("${mobileNo}", "9999999990");

        sampleRequestBody = readAndReplacePlaceholders("update-account.json", replacements);

        given()
                .body(sampleRequestBody)
                .contentType(ContentType.JSON)
                .when().patch("/api/v1/accountservice")
                .then()
                .statusCode(200);

                given()
                .queryParam("customerId", customerId)
                .queryParam("accountNo", accountNo)
                .when().get("/api/v1/accountservice")
                .then()
                .statusCode(200)
                .body(containsString("abc@gmail.com"))
                .body(containsString("9999999990"));
    }

    @Test
    public void testGetAccountWithGoodCustomerIdAndBadAccountNo() {
        String customerId = AccontHelper.getCustomerId();
        openAccount("saving-request.json", AccontHelper.getPanNo(), AccontHelper.getAccountNo(), null,customerId, AccountType.Saving.name());
        given()
                .queryParam("customerId", customerId)
                .queryParam("accountNo", AccontHelper.getAccountNo())
                .when().get("/api/v1/accountservice")
                .then()
                .statusCode(404);
    }
}