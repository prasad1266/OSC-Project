package com.osc.sessiondataservice.kafka;


import com.osc.sessiondataservice.avro.SessionAvro;
import com.osc.sessiondataservice.constants.Constants;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaManagerService {

    private ReadOnlyKeyValueStore<String, SessionAvro> sessionStore;

    private KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaManagerService(@Qualifier("sessionStore") ReadOnlyKeyValueStore<String,SessionAvro > sessionStore,
                               @Qualifier("KafkaTemplate")KafkaTemplate<String, Object> kafkaTemplate) {
        this.sessionStore = sessionStore;
        this.kafkaTemplate = kafkaTemplate;
    }




    public void newUserSession(String sessionId) {
        SessionAvro updateSessionAvro = SessionAvro.newBuilder().setSessionStatus(false).build();
        kafkaTemplate.send(Constants.SESSION_Topic,sessionId,updateSessionAvro);
    }

    public void logoutSession(String sessionId) {
        SessionAvro updateSessionAvro = SessionAvro.newBuilder().setSessionStatus(false).build();
        kafkaTemplate.send(Constants.SESSION_Topic,sessionId,updateSessionAvro);
    }
    public SessionAvro getSession(String Key){
            return sessionStore.get(Key);
    }


    public void createSession(String sessionId) {
        SessionAvro updateSessionAvro = SessionAvro.newBuilder().setSessionStatus(true).build();
        kafkaTemplate.send(Constants.SESSION_Topic,sessionId,updateSessionAvro);
    }
}
