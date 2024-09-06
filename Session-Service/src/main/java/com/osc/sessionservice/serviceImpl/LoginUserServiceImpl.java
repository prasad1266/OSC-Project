package com.osc.sessionservice.serviceImpl;

import com.osc.sessionservice.dto.Logindto;
import com.osc.sessionservice.dto.VerifyCredentialsResponseDto;
import com.osc.sessionservice.exception.ActiveSessionException;
import com.osc.sessionservice.mapper.SessionMapper;
import com.osc.sessionservice.response.Response;
import com.osc.sessionservice.service.LoginUserService;
import com.osc.sessionservice.service.VerifyUserCredentialsService;
import com.osc.sessionservice.utility.CredentialVerfication;
import com.osc.sessionservice.utility.SessionIdGenerator;
import com.session.CreateSessionRequest;
import com.session.SessionServiceGrpc;
import com.session.SessionStatusRequest;
import com.session.SessionStatusResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class LoginUserServiceImpl implements LoginUserService {

    private VerifyUserCredentialsService verifyUserCredentialsService;
    private SessionServiceGrpc.SessionServiceBlockingStub stub;

    public LoginUserServiceImpl(@Qualifier("sessionServiceBlockingStub")SessionServiceGrpc.SessionServiceBlockingStub stub,
                                VerifyUserCredentialsService verifyUserCredentialsService) {
        this.stub = stub;
        this.verifyUserCredentialsService = verifyUserCredentialsService;
    }

    @Override
    public Response loginUser(Logindto logindto) {

        VerifyCredentialsResponseDto verifyCredentialsResponseDto = verifyUserCredentialsService.fetchUserCredentials(logindto);

        String userName = verifyCredentialsResponseDto.getName();
        boolean response = CredentialVerfication.verifyCredentials(logindto, verifyCredentialsResponseDto);
        String sessionId = null;
        if (response) {
            SessionStatusRequest sessionStatusRequest = SessionMapper.maptoSessionStatusRequest(logindto);
            SessionStatusResponse response1 = stub.checkSessionStatus(sessionStatusRequest);

            if (response1.getStatus()) {                                          // sessionStatus = true
                throw new ActiveSessionException();
            }
            sessionId = SessionIdGenerator.generateSessionId();
            CreateSessionRequest createSessionRequest = SessionMapper.maptoCreateSessionRequest(logindto, sessionId);
            stub.createSession(createSessionRequest);
        }
        List<String> responseData = Arrays.asList(sessionId, userName);
        return new Response(responseData, 200);
    }
}
