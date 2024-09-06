package com.osc.userservice.service;

import com.osc.userservice.dto.ValidateOTPDto;
import com.osc.userservice.dto.ValidateOTPForForgotPasswordDto;
import com.osc.userservice.response.Response;

public interface OtpValidationService {

      Response otpValidation(ValidateOTPDto otpDto);
     Response otpValidationforForgotPassword(ValidateOTPForForgotPasswordDto otpDto);
}
