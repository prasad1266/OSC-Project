package com.osc.userdataservice.serviceImpl;

import com.osc.userdataservice.entity.User;
import com.osc.userdataservice.mapper.Mapper;
import com.osc.userdataservice.repository.UserRepository;
import com.osc.userdataservice.service.ResetUserPasswordService;
import com.user.ResetPasswordRequest;
import com.user.ResetPasswordResponse;
import org.springframework.stereotype.Service;

@Service
public class ResetUserPasswordServiceImpl implements ResetUserPasswordService {
    private UserRepository userRepository;

    public ResetUserPasswordServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResetPasswordResponse resetPassword(ResetPasswordRequest resetPasswordRequest) {

        String email = resetPasswordRequest.getEmail().toString();
        User user = userRepository.findByEmail(email);                   // Check if email exists in DB
        user.setPassword( resetPasswordRequest.getPassword());
        userRepository.save(user);
        ResetPasswordResponse resetPasswordResponse = Mapper.toResetPasswordResponse();

        return resetPasswordResponse;

    }
}
