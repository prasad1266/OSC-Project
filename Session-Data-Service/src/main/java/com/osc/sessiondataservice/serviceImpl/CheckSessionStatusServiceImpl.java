package com.osc.sessiondataservice.serviceImpl;

import com.osc.sessiondataservice.avro.SessionAvro;
import com.osc.sessiondataservice.constants.Constants;
import com.osc.sessiondataservice.dto.CheckStatusDto;
import com.osc.sessiondataservice.dto.LogoutRequestDto;
import com.osc.sessiondataservice.entity.Session;
import com.osc.sessiondataservice.kafka.KafkaManagerService;
import com.osc.sessiondataservice.mapper.SessionMapper;
import com.osc.sessiondataservice.repository.SessionRepository;
import com.osc.sessiondataservice.service.CheckSessionStatusService;
import com.osc.sessiondataservice.utility.ConcatUserIdAndDeviceName;
import com.session.*;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CheckSessionStatusServiceImpl implements CheckSessionStatusService {

    private KafkaManagerService kafkaManagerService;
 private SessionStatusResponse sessionStatusResponse;

    public CheckSessionStatusServiceImpl(KafkaManagerService kafkaManagerService
                                        ) {
        this.kafkaManagerService = kafkaManagerService;
    }


    @Override
    public SessionStatusResponse checkSessionSattusinKtable(SessionStatusRequest sessionStatusRequest) {

        CheckStatusDto dto = SessionMapper.toCheckStatusDto(sessionStatusRequest);
        String sessionId = ConcatUserIdAndDeviceName.concatUserIdAndDeviceName(dto.getUserId(), dto.getDeviceName());
        SessionAvro sessionAvro = kafkaManagerService.getSession(sessionId);

        if (sessionAvro == null){
            //Produce new User With False Status
            kafkaManagerService.newUserSession(sessionId);
        }else{
        sessionStatusResponse = SessionMapper.mapToSessionStatusResponse(sessionAvro.getSessionStatus());
        return sessionStatusResponse;
        }
        return sessionStatusResponse;
    }

}
