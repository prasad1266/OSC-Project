package com.osc.userdataservice.serviceImpl;

import com.osc.userdataservice.entity.User;
import com.osc.userdataservice.mapper.Mapper;
import com.osc.userdataservice.repository.UserRepository;
import com.osc.userdataservice.service.VerifyUserCredentialsService;
import com.session.VerifyCredentialsRequest;
import com.session.VerifyCredentialsResponse;
import org.springframework.stereotype.Service;

@Service
public class VerrifyCredentialsServiceImpl implements VerifyUserCredentialsService {

    private UserRepository userRepository;
    private VerifyCredentialsResponse verifyCredentialsResponse;

    public VerrifyCredentialsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public VerifyCredentialsResponse verifyCredentialsFromDB(VerifyCredentialsRequest verifyCredentialsRequest) {
        String userId = verifyCredentialsRequest.getUserId().toString();
        User user = userRepository.findUserByUserId(userId);

        if(user!=null){
            verifyCredentialsResponse = Mapper.mapToVerifyCredentials(user);
            return verifyCredentialsResponse;
        }
        verifyCredentialsResponse = null;
        return verifyCredentialsResponse;
    }

}
