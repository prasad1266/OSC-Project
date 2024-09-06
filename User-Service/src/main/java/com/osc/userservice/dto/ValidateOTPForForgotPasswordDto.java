package com.osc.userservice.dto;

import lombok.Data;
@Data
public class ValidateOTPForForgotPasswordDto {
        private String email;
        private String otp;
    }


