package com.osc.userservice.ServiceImpl;

import avro.OtpMessage;
import com.osc.userservice.constatnts.Constants;
import com.osc.userservice.kafka.KafkaManagerService;
import com.osc.userservice.service.OTPUpdaterService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OTPUpdaterServiceImpl implements OTPUpdaterService {

    private static final Logger logger = LogManager.getLogger(OTPUpdaterServiceImpl.class);
    private KafkaManagerService kafkaManagerService;

    public OTPUpdaterServiceImpl(KafkaManagerService kafkaManagerService) {
        this.kafkaManagerService = kafkaManagerService;
    }

    public  void updateFailedOtpAttempts(String userId, OtpMessage otpData) {
        if (otpData != null) {
            OtpMessage updatedOtpMessage = OtpMessage.newBuilder(otpData).setFailedAttempts(otpData.getFailedAttempts())
                    .build();
            logger.info("Updated Failed Attempt By One");
            kafkaManagerService.updateFailedAttemptsInOTPTopic(userId,updatedOtpMessage);
        }else{
            logger.info("************** Error Error *******************");
            //System.out.println("************** Error Error *******************");
        }
    }
}
