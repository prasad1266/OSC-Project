package com.osc.userservice.ServiceImpl;

import avro.User;
import com.osc.userservice.constatnts.Constants;
import com.osc.userservice.constatnts.StatusCodes;
import com.osc.userservice.dto.PasswordDto;
import com.osc.userservice.dto.UpdatePasswordDto;
import com.osc.userservice.exception.UserIdNotFoundException;
import com.osc.userservice.kafka.KafkaManagerService;
import com.osc.userservice.mapper.UserMapper;
import com.osc.userservice.response.RegisterResponseDTO;
import com.osc.userservice.response.Response;
import com.osc.userservice.service.UserPasswordService;
import com.user.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class UserPasswordServiceImpl implements UserPasswordService {

    private static final Logger logger = LogManager.getLogger(UserPasswordServiceImpl.class);

    private KafkaManagerService kafkaManagerService;
    private UserDataServiceGrpc.UserDataServiceBlockingStub userDataServiceBlockingStub;

    public UserPasswordServiceImpl(UserDataServiceGrpc.UserDataServiceBlockingStub stub,
                                   KafkaManagerService kafkaManagerService) {

        this.userDataServiceBlockingStub = stub;
        this.kafkaManagerService = kafkaManagerService;
    }

    @Override
    public Response createPassword(PasswordDto passwordDto) {
        User user = kafkaManagerService.getUser(passwordDto.getUserId());       //Get Data From Store

        if(user != null) {
            RegisterUserRequest registerUserRequest = UserMapper.toCreatePassword(user, passwordDto);
            logger.info("Registering User  ");
            RegisterUserResponse registerUser = userDataServiceBlockingStub.registerUser(registerUserRequest);
            RegisterResponseDTO response = UserMapper.toRegisterResponse(registerUser);

            if (response.isSuccess()) {
                logger.info(" Making User NULL...!!! ");
                kafkaManagerService.produceTopicToNull(passwordDto.getUserId(), Constants.User_Topic);          //Make_User_null
                return new Response("", StatusCodes.PASSWORD_SAVED);
            }
        }
        throw new UserIdNotFoundException();
    }

    public Response updatePassword(UpdatePasswordDto updatePasswordDto) {

        ResetPasswordRequest registerUserRequest = UserMapper.toResetPasswordRequest(updatePasswordDto);
        logger.info("Change Password Request ...!!!");
        ResetPasswordResponse response1  =  userDataServiceBlockingStub.resetPassword(registerUserRequest);
        //Change return type from Object
        String  responsemsg= UserMapper.toResetPasswordResponse(response1);
        logger.info("Change Password Successfull...!!!");
        return new Response(responsemsg, StatusCodes.PASSWORD_SAVED);
    }
}
