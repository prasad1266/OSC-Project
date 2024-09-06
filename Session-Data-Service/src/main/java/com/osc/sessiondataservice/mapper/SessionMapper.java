package com.osc.sessiondataservice.mapper;

import com.osc.sessiondataservice.dto.CheckStatusDto;
import com.osc.sessiondataservice.dto.LogoutRequestDto;
import com.osc.sessiondataservice.entity.Session;
import com.osc.userdataservice.entity.User;
import com.session.*;
import com.user.RegisterUserRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SessionMapper {

    public static Session mapToSession(CreateSessionRequest request) {
        Session session = new Session();
        session.setUserId(request.getUserId());
       session.setSessionId(request.getSessionId());
        session.setDeviceName(request.getDeviceId());
        session.setLoginTime(LocalDateTime.now());

        return session;
    }
    public static CheckStatusDto toCheckStatusDto(SessionStatusRequest sessionStatusRequest){
        CheckStatusDto dto = new CheckStatusDto();
        dto.setDeviceName(sessionStatusRequest.getDeviceId());
        dto.setUserId(sessionStatusRequest.getUserId());
        return dto;
    }

    public static SessionStatusResponse mapToSessionStatusResponse(Boolean value){
        return SessionStatusResponse.newBuilder().setStatus(value).build();
    }

    public static LogoutRequestDto mapToLogoutRequestDto(LogoutRequest request) {
        LogoutRequestDto dto = new LogoutRequestDto();
        dto.setSessionId(request.getSessionId());
        dto.setUserId(request.getUserId());
        return dto;
    }
}
