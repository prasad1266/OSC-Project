package com.osc.userdataservice.serviceImpl;

import com.osc.userdataservice.entity.User;
import com.osc.userdataservice.mapper.Mapper;
import com.osc.userdataservice.repository.UserRepository;
import com.osc.userdataservice.service.RegisterUserService;
import com.user.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import static com.osc.userdataservice.mapper.Mapper.mapToUser;

@Service
public class RegisterUserServiceImpl implements RegisterUserService {
    private static final Logger logger = LogManager.getLogger(RegisterUserServiceImpl.class);
    private UserRepository userRepository;
    private User user = new User();

    public RegisterUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public RegisterUserResponse registerUser(RegisterUserRequest request) {
        user = mapToUser(request);
        userRepository.save(user);
        logger.info("User Saved In Database Successfully...!!  {}",user.getUserId());
        RegisterUserResponse userResponse = Mapper.mapToRegisterUserResponse(user);
        return userResponse;
    }

}
