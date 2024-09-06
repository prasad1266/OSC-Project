package com.osc.userservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ValidateOTPDto {
    private String userId;
    @JsonProperty("OTP")
    private String otp;
}
