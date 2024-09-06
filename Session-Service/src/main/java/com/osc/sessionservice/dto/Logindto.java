package com.osc.sessionservice.dto;

import lombok.Data;

@Data
public class Logindto {

    private String userId;
    private String password;
    private String deviceName;
}
