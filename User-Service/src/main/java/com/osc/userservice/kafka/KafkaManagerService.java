package com.osc.userservice.kafka;

import avro.OtpMessage;
import avro.User;
import com.osc.userservice.constatnts.Constants;
import com.osc.userservice.dto.UserDto;
import com.osc.userservice.mapper.UserMapper;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaManagerService {
    private ReadOnlyKeyValueStore<String, User> userstore;
    private  ReadOnlyKeyValueStore<String, OtpMessage> otpStore;

    private KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaManagerService(ReadOnlyKeyValueStore<String, User> userstore,
                               ReadOnlyKeyValueStore<String, OtpMessage> otpStore,
                               KafkaTemplate<String, Object> kafkaTemplate) {
        this.userstore = userstore;
        this.otpStore = otpStore;
        this.kafkaTemplate = kafkaTemplate;
    }

    public  void produceUserTopic(UserDto userDTO, String userId ){
        User userAvro= UserMapper.toUserAvro(userDTO);
        kafkaTemplate.send(Constants.User_Topic, userId, userAvro);
    }

    public void produceTopicToNull( String userId ,String topicName){
        kafkaTemplate.send(topicName, userId, null);
    }

//    public  void updateFailedAttemptsInOTPTopic( String userId,String topicName){
//        kafkaTemplate.send(Constants.OTP_Topic, userId, null);
//    }

    public  void produceOTPTopicForRegistration(String email, String otp, String userId){
        OtpMessage otpMessage = UserMapper.toOTPAvro(email,otp,Constants.REGISTRATION_PURPOSE);
        kafkaTemplate.send(Constants.OTP_Topic, userId, otpMessage);
    }

    public  void produceOTPForForgotPassword( String otp, String email){
        OtpMessage otpMessage = UserMapper.toOTPAvro(email,otp,Constants.FOGOTPASSWORD_PURPOSE);
        kafkaTemplate.send(Constants.OTP_Topic, email, otpMessage);
    }

    public  void updateFailedAttemptsInOTPTopic(String userId, OtpMessage otpMessage){
        kafkaTemplate.send(Constants.OTP_Topic, userId, otpMessage);
    }

    public User getUser(String Key){
            return userstore.get(Key);
    }

    public OtpMessage getOTP(String Key){
        return otpStore.get(Key);
    }


}
