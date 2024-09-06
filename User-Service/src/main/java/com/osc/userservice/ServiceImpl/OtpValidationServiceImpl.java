package com.osc.userservice.ServiceImpl;

import avro.OtpMessage;
import com.osc.userservice.constatnts.Constants;
import com.osc.userservice.constatnts.StatusCodes;
import com.osc.userservice.dto.ValidateOTPDto;
import com.osc.userservice.dto.ValidateOTPForForgotPasswordDto;
import com.osc.userservice.exception.EmailNotFoundException;
import com.osc.userservice.exception.InvalidOTPException;
import com.osc.userservice.exception.MaxOtpAttemptsReachedException;
import com.osc.userservice.exception.UserIdNotFoundException;
import com.osc.userservice.kafka.KafkaManagerService;
import com.osc.userservice.response.Response;
import com.osc.userservice.service.OTPUpdaterService;
import com.osc.userservice.service.OtpValidationService;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OtpValidationServiceImpl implements OtpValidationService {
    private static final Logger logger = LogManager.getLogger(OtpValidationServiceImpl.class);

    private OTPUpdaterService otpUpdaterService;
    private KafkaManagerService kafkaManagerService;

    public OtpValidationServiceImpl(OTPUpdaterService otpUpdaterService,
                                    KafkaManagerService kafkaManagerService) {

        this.otpUpdaterService = otpUpdaterService;
        this.kafkaManagerService =kafkaManagerService;
    }

    public  Response otpValidation(ValidateOTPDto otpDto) {

        OtpMessage otpMessage = kafkaManagerService.getOTP(otpDto.getUserId());

       if( otpMessage != null ){                                                // No UserId
         if(!otpMessage.getOtp().toString().equals(otpDto.getOtp())){         //  if Wrong OTP
            int failedAttempts =    otpMessage.getFailedAttempts()+1;
               otpMessage.setFailedAttempts(failedAttempts);

             otpUpdaterService.updateFailedOtpAttempts(otpDto.getUserId(),otpMessage );

             if (failedAttempts >= 3) {
                 logger.info("OTP Max Attempt Reached Limit");
                 kafkaManagerService.produceTopicToNull(otpDto.getUserId(),Constants.User_Topic);      //NULL
                 throw new MaxOtpAttemptsReachedException();
             }
             throw new InvalidOTPException();
           }
           logger.info("Valid OTP ....!!!!");
           kafkaManagerService.produceTopicToNull(otpDto.getUserId(),Constants.OTP_Topic);         //NUll
           return new Response(" Valid OTP",StatusCodes.OTP_VALID);
       }
        throw new UserIdNotFoundException();
    }

    public  Response otpValidationforForgotPassword(ValidateOTPForForgotPasswordDto otpDto) {

        OtpMessage otpMessage =  kafkaManagerService.getOTP(otpDto.getEmail());

        if( otpMessage != null ){                                                // No UserId
            if(!otpMessage.getOtp().toString().equals(otpDto.getOtp())){         //  if Wrong OTP
                int failedAttempts =    otpMessage.getFailedAttempts()+1;

                otpMessage.setFailedAttempts(failedAttempts);
                otpUpdaterService.updateFailedOtpAttempts(otpDto.getEmail(),otpMessage );

                if (failedAttempts >= 3)
                    throw new InvalidOTPException();
            }
            logger.info("  Valid OTP  ");
            kafkaManagerService.produceTopicToNull(otpDto.getEmail(),Constants.OTP_Topic);
            return new Response(" Valid OTP",500);
        }
      throw new EmailNotFoundException();
    }
}