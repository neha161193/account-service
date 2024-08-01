
package com.apibanking.accountopening.service;

import java.util.Random;
import java.util.UUID;

public interface AccontHelper {
    static String getCustomerId(){
        return UUID.randomUUID().toString().replace("-", "").toString();
    }
    static String getAccountNo(){
        return String.valueOf(1000000 + new Random().nextInt(9000000)) + String.valueOf(100000000 + new Random().nextInt(900000000));
    }
    static String getPanNo(){
        return "BHJKM" + String.valueOf(1000 + new Random().nextInt(9000)) + "P";
    }
}
