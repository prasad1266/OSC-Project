package com.osc.sessiondataservice.serviceImpl;

import com.osc.sessiondataservice.avro.SessionAvro;
import com.osc.sessiondataservice.avro.SessionKey;
import com.osc.sessiondataservice.constants.Constants;
import com.osc.sessiondataservice.dto.LogoutRequestDto;
import com.osc.sessiondataservice.entity.Session;
import com.osc.sessiondataservice.kafka.KafkaManagerService;
import com.osc.sessiondataservice.mapper.SessionMapper;
import com.osc.sessiondataservice.repository.SessionRepository;
import com.osc.sessiondataservice.service.CreateSessionService;
import com.osc.sessiondataservice.service.LogoutUserService;
import com.osc.sessiondataservice.utility.ConcatUserIdAndDeviceName;
import com.session.CreateSessionRequest;
import com.session.CreateSessionResponse;
import com.session.LogoutRequest;
import com.session.LogoutResponse;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LogoutSessionServiceImpl implements LogoutUserService {

    private KafkaManagerService kafkaManagerService;
    private SessionRepository sessionRepository;

    public LogoutSessionServiceImpl(KafkaManagerService kafkaManagerService,
                                    SessionRepository sessionRepository
                                   ) {
        this.sessionRepository = sessionRepository;
        this.kafkaManagerService= kafkaManagerService;
    }

    @Override
    public LogoutResponse logoutUser(LogoutRequest request) {
        String deviceName;
        LogoutRequestDto logoutRequestDto= SessionMapper.mapToLogoutRequestDto(request);
        Session byUserIdAndSessionId = sessionRepository.findByUserIdAndSessionId(logoutRequestDto.getUserId(), logoutRequestDto.getSessionId());

        if(byUserIdAndSessionId !=null){
            deviceName = byUserIdAndSessionId.getDeviceName();
            byUserIdAndSessionId.setLogoutTime(LocalDateTime.now());
            System.out.println("Session Of User : "+byUserIdAndSessionId);

            sessionRepository.save(byUserIdAndSessionId);

//            String compositKey = ConcatUserIdAndDeviceName.concatUserIdAndDeviceName(logoutRequestDto.getUserId(),deviceName);
            SessionKey sessionKey  = SessionKey.newBuilder().setDeviceId(deviceName).setUserId(logoutRequestDto.getUserId()).build();
            SessionAvro sessionAvro =kafkaManagerService.getSession(sessionKey);
            System.out.println("Session Avro : "+sessionAvro);

            //Logout User produce status False
            kafkaManagerService.logoutSession(sessionKey);
        }
        return LogoutResponse.newBuilder()
                .setIsSessionLogout(true).build();
    }

}
