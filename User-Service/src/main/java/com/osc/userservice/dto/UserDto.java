package com.osc.userservice.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDto {

    private String email;
    private String name;
    private String contact;
    @JsonProperty("DOB")
    private LocalDate dateOfBirth;

  }
