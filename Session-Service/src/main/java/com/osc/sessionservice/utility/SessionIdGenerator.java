package com.osc.sessionservice.utility;

import java.security.SecureRandom;

public class SessionIdGenerator {

    private static final SecureRandom random = new SecureRandom();
    private static final int ID_LENGTH = 6;

    public static String generateSessionId() {
        // Generate a random 6-digit number
        int pin = random.nextInt(900000) + 100000;

        return String.valueOf(pin);
    }
}
