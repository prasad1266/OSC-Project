package com.osc.sessiondataservice.kafka;


import com.osc.sessiondataservice.avro.SessionAvro;
import com.osc.sessiondataservice.avro.SessionKey;
import com.osc.sessiondataservice.constants.Constants;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaManagerService {

    private ReadOnlyKeyValueStore<SessionKey, SessionAvro> sessionStore;

    private KafkaTemplate<SessionKey, SessionAvro> kafkaTemplate;

    public KafkaManagerService(@Qualifier("sessionStore") ReadOnlyKeyValueStore<SessionKey,SessionAvro > sessionStore,
                               @Qualifier("KafkaTemplate")KafkaTemplate<SessionKey, SessionAvro> kafkaTemplate) {
        this.sessionStore = sessionStore;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void newUserSession(SessionKey sessionKey) {
        SessionAvro updateSessionAvro = SessionAvro.newBuilder().setSessionStatus(false).build();
        kafkaTemplate.send(Constants.SESSION_Topic,sessionKey,updateSessionAvro);
    }

    public void logoutSession(SessionKey sessionKey) {
        SessionAvro updateSessionAvro = SessionAvro.newBuilder().setSessionStatus(false).build();
        kafkaTemplate.send(Constants.SESSION_Topic,sessionKey,updateSessionAvro);
    }

    public SessionAvro getSession(SessionKey Key){
            return sessionStore.get(Key);
    }

    public void createSession(SessionKey sessionKey) {
        SessionAvro updateSessionAvro = SessionAvro.newBuilder().setSessionStatus(true).build();
        kafkaTemplate.send(Constants.SESSION_Topic,sessionKey,updateSessionAvro);
    }
}
