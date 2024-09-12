package com.osc.userservice.mapper;

import avro.OtpMessage;
import avro.User;

import com.osc.userservice.dto.PasswordDto;
import com.osc.userservice.dto.UniqueEmailResponseDto;
import com.osc.userservice.dto.UpdatePasswordDto;
import com.osc.userservice.dto.UserDto;
import com.osc.userservice.response.RegisterResponseDTO;
import com.user.*;

import java.time.format.DateTimeFormatter;

public class UserMapper {

//   private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static RegisterResponseDTO toRegisterResponse(RegisterUserResponse registerUserResponse) {
        RegisterResponseDTO registerResponseDTO = new RegisterResponseDTO();
        registerResponseDTO.setSuccess(registerUserResponse.getSuccess());
        registerResponseDTO.setMessage(registerUserResponse.getMessage());
        return registerResponseDTO;
    }

    public static UniqueEmailRequest toUniqueEmailRequest(String email){
        return UniqueEmailRequest.newBuilder().setEmail(email).build();
    }
    public static UniqueEmailResponseDto toUniqueEmailDto(UniqueEmailResponse uniqueEmailResponse){
        UniqueEmailResponseDto uniqueEmailDto = new UniqueEmailResponseDto();
      uniqueEmailDto.setIsUnique(uniqueEmailResponse.getIsUnique());
        return uniqueEmailDto;
    }

    public static User toUserAvro(UserDto userDto){
        String dob = userDto.getDateOfBirth().format(DATE_FORMATTER);
        return  User.newBuilder()
                .setDob(dob)
                .setEmail(userDto.getEmail())
                .setName(userDto.getName())
                .setContactNo(userDto.getContact()).build();

    }
//    public static OtpMessage toOTPAvro(UserDto userDto,String otp,String purpose){
//        String dob = userDto.getDateOfBirth().format(DATE_FORMATTER);
//        System.out.println("toOTPAvro : "+otp);
//        return  OtpMessage.newBuilder()
//                .setEmail(userDto.getEmail())
//                .setOtp(otp)
//                .setPurpose("REGISTRATION")
//                .setFailedAttempts(0).build();
//
//    }
    public static OtpMessage toOTPAvro(String email,String otp,String purpose){

        System.out.println("toOTPAvro : "+otp);
        return  OtpMessage.newBuilder()
                .setEmail(email)
                .setOtp(otp)
                .setPurpose(purpose)
                .setFailedAttempts(0).build();

    }
    public static RegisterUserRequest toCreatePassword(User user, PasswordDto passwordDto){

       // System.out.println("User DTO "+userDTO);
        return RegisterUserRequest.newBuilder()
                .setUserId(passwordDto.getUserId())
                .setEmail(user.getEmail().toString())
                .setName(user.getName().toString())
                .setMobileNumber(user.getContactNo().toString())
                .setDob(user.getDob().toString())
                .setPassword(passwordDto.getPassword())
                .build();
    } 

    public static ResetPasswordRequest toResetPasswordRequest(UpdatePasswordDto updatePasswordDto) {
        return ResetPasswordRequest.newBuilder()
                .setEmail(updatePasswordDto.getEmail())
                .setPassword(updatePasswordDto.getPassword()).build();
    }

    public static String toResetPasswordResponse(ResetPasswordResponse response1) {
        String response = response1.getMessage().toString();
                return response;
    }
}
