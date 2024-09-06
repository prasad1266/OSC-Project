package com.osc.userdataservice.serviceImpl;

import com.osc.userdataservice.entity.User;
import com.osc.userdataservice.mapper.Mapper;
import com.osc.userdataservice.repository.UserRepository;
import com.osc.userdataservice.service.VerifyEmailAddressService;
import com.user.UniqueEmailRequest;
import com.user.UniqueEmailResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class verifyEmailAddressServiceImpl implements VerifyEmailAddressService {
    private UserRepository userRepository;

    public verifyEmailAddressServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private static final Logger logger = LogManager.getLogger(verifyEmailAddressServiceImpl.class);
        @Override
        public UniqueEmailResponse verifyEmailAddressIsUnique(UniqueEmailRequest request) {
            String email = request.getEmail();
            logger.info("Verifying if email {} is unique", email);
            boolean isUnique = false;
            User user = userRepository.findByEmail(email); // Check if email exists in DB
            if(user!=null) {
                isUnique = true;
                logger.info("Email {} is unique", email);
            }
            UniqueEmailResponse response = Mapper.mapToUniqueEmailResponse(isUnique);

            logger.info("Returning response for email {}: isUnique={}", email, !isUnique);
            return response;
        }
    }

