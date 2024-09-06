package com.osc.userservice.ServiceImpl;

import com.osc.userservice.constatnts.StatusCodes;
import com.osc.userservice.dto.UserDto;
import com.osc.userservice.exception.EmailAlreadyExistException;
import com.osc.userservice.kafka.KafkaManagerService;
import com.osc.userservice.response.Response;
import com.osc.userservice.service.UserRegisterService;
import com.osc.userservice.utility.OTPGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import com.osc.userservice.service.verifyUniqueEmailService;

import java.security.SecureRandom;

@Service
public class UserRegisterServiceImpl implements UserRegisterService {

    private static final Logger logger = LogManager.getLogger(UserRegisterServiceImpl.class);

    private static final SecureRandom random = new SecureRandom();
    private static final int NUMBER_LENGTH = 4;
    private verifyUniqueEmailService verifyUniqueEmailService;
    private KafkaManagerService kafkaManagerService;

    public UserRegisterServiceImpl(verifyUniqueEmailService verifyUniqueEmailService,
                                   KafkaManagerService kafkaManagerService) {
       this.kafkaManagerService =kafkaManagerService;
        this.verifyUniqueEmailService = verifyUniqueEmailService;
    }

    @Override
    public Response registerUser(UserDto userDTO) {
        String email = userDTO.getEmail();
        //Unique Email Verification
        boolean isUnique = verifyUniqueEmailService.verifyUniqueEmail(email);
        if (!isUnique) {
            logger.info(" Unique Email : {}",userDTO.getEmail());
            //Create Username And Otp
            String userId = generateUsername(userDTO.getName());
            String otp = OTPGenerator.generateOTP();

            // Produce User details to "Prasad-user-topic"
            logger.info("Produce into UserTopic while Registering {}",userId);
            kafkaManagerService.produceUserTopic(userDTO,userId);

            // Produce OTP to "Prasad-otp-topic"
            logger.info("Produce into OTPTopic while Registering {}",userId);
            kafkaManagerService.produceOTPTopicForRegistration(userDTO.getEmail(),otp,userId);

            return new Response(userId, StatusCodes.REGISTRATION_SUCCESS);
        }
        throw new EmailAlreadyExistException();
    }

    private String generateUsername(String name) {

        String prefix = name.length() >= 3 ? name.substring(0, 3).toLowerCase() : name.toLowerCase();
        int number = random.nextInt(9000) + 1000;
        return prefix + number;
    }
}
