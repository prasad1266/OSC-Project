package com.osc.sessiondataservice.dto;

import lombok.Data;

@Data
public class LogoutRequestDto {
    private String userId;
    private String sessionId;
}
