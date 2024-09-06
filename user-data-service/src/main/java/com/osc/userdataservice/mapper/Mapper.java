package com.osc.userdataservice.mapper;


import com.osc.userdataservice.entity.User;
import com.session.VerifyCredentialsResponse;
import com.user.RegisterUserRequest;
import com.user.RegisterUserResponse;
import com.user.ResetPasswordResponse;
import com.user.UniqueEmailResponse;


import java.time.LocalDate;

public class Mapper {
    // Convert RegisterUserRequest to User entity
    public static User mapToUser(RegisterUserRequest request) {
        User user = new User();
        user.setUserId(request.getUserId());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setContactNumber(request.getMobileNumber());
        user.setDateOfBirth(LocalDate.parse(request.getDob()));
        user.setPassword(request.getPassword());
        return user;
    }

    public static RegisterUserResponse mapToRegisterUserResponse(boolean success, String message) {
        return RegisterUserResponse.newBuilder()
                .setSuccess(success)
                .setMessage(message)
                .build();
    }
    public static RegisterUserResponse mapToRegisterUserResponse(User user) {
       RegisterUserResponse response =RegisterUserResponse.newBuilder()
                .setSuccess(true)
                .setMessage("User registered successfully with ID: " + user.getUserId())
                .build();
        return response;
    }
    public static VerifyCredentialsResponse mapToVerifyCredentials(User user) {
        VerifyCredentialsResponse response =VerifyCredentialsResponse.newBuilder()
                .setName(user.getName())
                .setPassword(user.getPassword())
                .build();
        return response;
    }

    public static UniqueEmailResponse mapToUniqueEmailResponse(Boolean value) {
        UniqueEmailResponse response =UniqueEmailResponse.newBuilder()
                .setIsUnique(value)
                .build();
        return response;
    }

    public static ResetPasswordResponse toResetPasswordResponse(){
      return  ResetPasswordResponse.newBuilder().setMessage("Password Updated Successfully....!!").build();
    }
}
