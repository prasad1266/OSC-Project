package com.osc.userservice.ServiceImpl;

import com.osc.userservice.constatnts.StatusCodes;
import com.osc.userservice.exception.EmailNotFoundException;
import com.osc.userservice.kafka.KafkaManagerService;
import com.osc.userservice.response.Response;
import com.osc.userservice.service.EmailVerificationService;
import com.osc.userservice.service.verifyUniqueEmailService;
import com.osc.userservice.utility.OTPGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class EmailVerificationServiceImpl implements EmailVerificationService {

    private static final Logger logger = LogManager.getLogger(EmailVerificationServiceImpl.class);

    private verifyUniqueEmailService verifyUniqueEmailService;
    private KafkaManagerService kafkaManagerService;

    public EmailVerificationServiceImpl(verifyUniqueEmailService verifyUniqueEmailService,
                                        KafkaManagerService kafkaManagerService) {
        this.verifyUniqueEmailService = verifyUniqueEmailService;
        this.kafkaManagerService = kafkaManagerService;
    }

    @Override
    public Response isEmailExist(String email){
        boolean isUnique = verifyUniqueEmailService.verifyUniqueEmail(email);

        if(!isUnique) { throw new EmailNotFoundException();}
        else{
            String otp = OTPGenerator.generateOTP();
            // Produce OTP to "Prasad-otp-topic"
            logger.info("produceOTPForForgotPassword Into Otp Topic");
            kafkaManagerService.produceOTPForForgotPassword(otp,email);
        }
        return new Response("", StatusCodes.VALID_EMAIL);
    }
}
