package com.osc.userservice.dto;

import lombok.Data;

@Data
public class UpdatePasswordDto {

    private String email;
    private String password;
}
