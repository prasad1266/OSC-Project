package com.osc.userservice.controller;

import com.osc.userservice.dto.*;
import com.osc.userservice.response.Response;
import com.osc.userservice.service.OtpValidationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin("*")
@RestController
public class OTPValidationController {
    private static final Logger logger = LogManager.getLogger(OTPValidationController.class);

    private OtpValidationService otpValidationService;

    public OTPValidationController(OtpValidationService otpValidationService) {
        this.otpValidationService = otpValidationService;
    }


    @PostMapping("/user/validateotp")
    public ResponseEntity<Response> validateOTPForRegistration(@RequestBody ValidateOTPDto validateOTPDto){
        logger.info("Received OTP validation request for registration for user: {}", validateOTPDto.getUserId());
        Response response = otpValidationService.otpValidation(validateOTPDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/user/validateOTPForForgotPassword")
    public ResponseEntity<Response> validateOTPOfForgotPassword(@RequestBody ValidateOTPForForgotPasswordDto validateOTPForForgotPasswordDto){
        logger.info("Received OTP validation request for forgot password for user: {}", validateOTPForForgotPasswordDto.getEmail());
        Response response = otpValidationService.otpValidationforForgotPassword(validateOTPForForgotPasswordDto);
        return ResponseEntity.ok(response);
    }
}
