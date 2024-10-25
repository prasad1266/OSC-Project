package com.osc.sessionservice.mapper;



import com.cart.ProductQuantityRequest;
import com.osc.sessionservice.dto.Logindto;
import com.osc.sessionservice.dto.Logoutdto;
import com.osc.sessionservice.dto.VerifyCredentialsResponseDto;
import com.recentHistory.RecentlyViewedRequest;
import com.session.*;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
@Component
public class SessionMapper {

private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public VerifyCredentialsRequest maptoVerifyCredentialsRequest(Logindto logindto){
                return VerifyCredentialsRequest.newBuilder()
                        .setUserId(logindto.getUserId())
                        .build();
        }
    public VerifyCredentialsResponseDto maptoVerifyCredentialsResponseDto(VerifyCredentialsResponse verifyCredentialsResponse){
        VerifyCredentialsResponseDto dto = new VerifyCredentialsResponseDto();
        dto.setName(verifyCredentialsResponse.getName());
        dto.setPassword(verifyCredentialsResponse.getPassword());
        return dto;
    }

    public CreateSessionRequest maptoCreateSessionRequest(Logindto logindto,String sessionId){
        return CreateSessionRequest.newBuilder()
                .setUserId(logindto.getUserId())
                .setDeviceId(logindto.getLoginDevice())
                .setSessionId(sessionId)
                .build();
    }

    public SessionStatusRequest maptoSessionStatusRequest(Logindto logindto) {
       return SessionStatusRequest.newBuilder()
                .setUserId(logindto.getUserId())
                .setDeviceId(logindto.getLoginDevice()).build();
    }

    public LogoutRequest mapToLogoutRequest(Logoutdto logoutdto) {
        return LogoutRequest.newBuilder()
                .setSessionId(logoutdto.getSessionId())
                .setUserId(logoutdto.getUserId()).build();
    }

    public RecentlyViewedRequest mapToRecentlyViewedRequest(Logoutdto logoutdto) {
        return RecentlyViewedRequest.newBuilder().setUserId(logoutdto.getUserId()).build();
    }

    public ProductQuantityRequest mapToProductQuantityRequest(Logoutdto logoutdto) {
        return ProductQuantityRequest.newBuilder().setUserId(logoutdto.getUserId()).build();
    }


//    public static RegisterUserRequest toRegisterUserRequest(UserDto userDTO) {
//        System.out.println("User DTO "+userDTO);
//        return RegisterUserRequest.newBuilder()
//                .setName(userDTO.getName())
//                .setEmail(userDTO.getEmail())
//                .setMobileNumber(userDTO.getContactNumber())
//                .setDob(String.valueOf(userDTO.getDateOfBirth()))
//                .build();
//    }

//    public static RegisterResponseDTO toRegisterResponse(RegisterUserResponse registerUserResponse) {
//        RegisterResponseDTO registerResponseDTO = new RegisterResponseDTO();
//        registerResponseDTO.setSuccess(registerUserResponse.getSuccess());
//        registerResponseDTO.setMessage(registerUserResponse.getMessage());
//        return registerResponseDTO;
//    }
//
//    public static UniqueEmailRequest toUniqueEmailRequest(String email){
//        return UniqueEmailRequest.newBuilder().setEmail(email).build();
//    }
//    public static UniqueEmailDto toUniqueEmailDto(UniqueEmailResponse uniqueEmailResponse){
//        UniqueEmailDto uniqueEmailDto = new UniqueEmailDto();
//      uniqueEmailDto.setIsUnique(uniqueEmailResponse.getIsUnique());
//        return uniqueEmailDto;
//    }
//
//    public static User toUserAvro(UserDto userDto){
//        String dob = userDto.getDateOfBirth().format(DATE_FORMATTER);
//        return  User.newBuilder()
//                .setDob(dob)
//                .setEmail(userDto.getEmail())
//                .setName(userDto.getName())
//                .setContactNo(userDto.getContactNumber()).build();
//
//    }
//    public static OtpMessage toOTPAvro(UserDto userDto,String otp){
//        String dob = userDto.getDateOfBirth().format(DATE_FORMATTER);
//        return  OtpMessage.newBuilder()
//                .setEmail(userDto.getEmail())
//                .setEmail(userDto.getEmail())
//                .setOtp(otp)
//                .setPurpose("REGISTRATION")
//                .setFailedAttempts(0).build();
//
//    }
//    public static RegisterUserRequest toCreatePassword(User user, PasswordDto passwordDto){
//
//       // System.out.println("User DTO "+userDTO);
//        return RegisterUserRequest.newBuilder()
//                .setUserId(passwordDto.getUserId())
//                .setEmail(user.getEmail().toString())
//                .setName(user.getName().toString())
//                .setMobileNumber(user.getContactNo().toString())
//                .setDob(user.getDob().toString())
//                .setPassword(passwordDto.getPassword())
//                .build();
//    }
}
