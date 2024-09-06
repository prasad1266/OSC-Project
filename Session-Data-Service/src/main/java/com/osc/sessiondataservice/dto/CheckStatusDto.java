package com.osc.sessiondataservice.dto;

import lombok.Data;

@Data
public class CheckStatusDto {
    private String userId;
    private String sessionId;
    private String deviceName;

}
