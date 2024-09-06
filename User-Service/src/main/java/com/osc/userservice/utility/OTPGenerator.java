package com.osc.userservice.utility;

import java.security.SecureRandom;

public class OTPGenerator {

    private static final SecureRandom random = new SecureRandom();
    private static final int PIN_LENGTH = 6;

    public static String generateOTP() {
        // Generate a random 6-digit number
        int pin = random.nextInt(900000) + 100000;
        // Convert it to String
        return String.valueOf(pin);
    }
}
