package com.osc.sessiondataservice.serviceImpl;

import com.osc.sessiondataservice.avro.SessionAvro;
import com.osc.sessiondataservice.constants.Constants;
import com.osc.sessiondataservice.dto.CheckStatusDto;
import com.osc.sessiondataservice.dto.LogoutRequestDto;
import com.osc.sessiondataservice.entity.Session;
import com.osc.sessiondataservice.kafka.KafkaManagerService;
import com.osc.sessiondataservice.mapper.SessionMapper;
import com.osc.sessiondataservice.repository.SessionRepository;
import com.osc.sessiondataservice.service.CreateSessionService;
import com.osc.sessiondataservice.service.LogoutUserService;
import com.osc.sessiondataservice.utility.ConcatUserIdAndDeviceName;
import com.session.*;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CreateSessionServiceImpl implements CreateSessionService {


    private KafkaManagerService kafkaManagerService;

 private SessionRepository sessionRepository;
 private Session session;
 private CreateSessionResponse createSessionResponse;

    public CreateSessionServiceImpl(SessionRepository sessionRepository,
                                    KafkaManagerService kafkaManagerService) {
        this.sessionRepository = sessionRepository;
        this.kafkaManagerService =kafkaManagerService;
    }

    @Override
    public CreateSessionResponse CreateSession(CreateSessionRequest createSessionRequest) {

        session = SessionMapper.mapToSession(createSessionRequest);
        String sessionId =  ConcatUserIdAndDeviceName.concatUserIdAndDeviceName(session.getUserId(),session.getDeviceName());
     //Session Produce True
      kafkaManagerService.createSession(sessionId);
         sessionRepository.save(session);
        createSessionResponse= CreateSessionResponse.newBuilder().setStatus(true).build();
        return createSessionResponse;
    }

}
