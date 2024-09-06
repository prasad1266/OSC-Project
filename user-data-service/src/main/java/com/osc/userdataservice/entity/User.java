package com.osc.userdataservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "Users")
public class User {
    @Id
    private String userId;
    private String email;
    private String name;
    private String contactNumber;
    private LocalDate dateOfBirth;
    private String password;

}
