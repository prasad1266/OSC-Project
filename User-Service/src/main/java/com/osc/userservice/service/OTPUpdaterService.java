package com.osc.userservice.service;

import avro.OtpMessage;

public interface OTPUpdaterService {

      void updateFailedOtpAttempts(String userId, OtpMessage otpData);
}
