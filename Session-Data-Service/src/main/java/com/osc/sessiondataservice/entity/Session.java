package com.osc.sessiondataservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "Sessions")
public class Session {

    @Id
    private String sessionId;
    private String userId;
    private String deviceName;
    private LocalDateTime loginTime;
    private LocalDateTime logoutTime;

}
