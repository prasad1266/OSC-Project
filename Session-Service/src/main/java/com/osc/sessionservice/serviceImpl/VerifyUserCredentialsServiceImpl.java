package com.osc.sessionservice.serviceImpl;

import com.osc.sessionservice.dto.Logindto;
import com.osc.sessionservice.dto.VerifyCredentialsResponseDto;
import com.osc.sessionservice.mapper.SessionMapper;
import com.osc.sessionservice.service.VerifyUserCredentialsService;
import com.session.SessionServiceGrpc;
import com.session.VerifyCredentialsRequest;
import com.session.VerifyCredentialsResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
//public class UserClientGrpcService {
public class VerifyUserCredentialsServiceImpl implements VerifyUserCredentialsService {
    private static VerifyCredentialsRequest verifyCredentialsRequest;
    private static VerifyCredentialsResponse verifyCredentialsResponse;
    private static VerifyCredentialsResponseDto verifyCredentialsResponseDto;

    private  SessionServiceGrpc.SessionServiceBlockingStub stubofUser;

    public VerifyUserCredentialsServiceImpl(@Qualifier("userServiceBlockingStub") SessionServiceGrpc.SessionServiceBlockingStub stubofUser) {
        this.stubofUser = stubofUser;
    }

    @Override
    public VerifyCredentialsResponseDto fetchUserCredentials(Logindto logindto) {

        verifyCredentialsRequest = SessionMapper.maptoVerifyCredentialsRequest(logindto);
        verifyCredentialsResponse  = stubofUser.verifyCredentials(verifyCredentialsRequest);
        verifyCredentialsResponseDto = SessionMapper.maptoVerifyCredentialsResponseDto(verifyCredentialsResponse);

        return verifyCredentialsResponseDto;

    }
}
