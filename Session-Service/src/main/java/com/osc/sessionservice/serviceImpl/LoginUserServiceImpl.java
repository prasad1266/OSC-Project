package com.osc.sessionservice.serviceImpl;

import com.osc.sessionservice.dto.Logindto;
import com.osc.sessionservice.exception.ActiveSessionException;
import com.osc.sessionservice.mapper.SessionMapper;
import com.osc.sessionservice.response.Response;
import com.osc.sessionservice.service.LoginUserService;
import com.osc.sessionservice.utility.CredentialVerfication;
import com.osc.sessionservice.utility.SessionIdGenerator;
import com.session.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LoginUserServiceImpl implements LoginUserService {

    private SessionMapper sessionMapper;
    private SessionServiceGrpc.SessionServiceBlockingStub sessionServiceBlockingStub;
    private  SessionServiceGrpc.SessionServiceBlockingStub stubofUser;


    private static final Logger logger = LogManager.getLogger(LoginUserServiceImpl.class);

    public LoginUserServiceImpl(SessionMapper sessionMapper,
                                @Qualifier("sessionServiceBlockingStub")SessionServiceGrpc.SessionServiceBlockingStub sessionServiceBlockingStub,
                                @Qualifier("userServiceBlockingStub") SessionServiceGrpc.SessionServiceBlockingStub stubofUser) {

      this.stubofUser = stubofUser;
      this.sessionMapper = sessionMapper;
        this.sessionServiceBlockingStub = sessionServiceBlockingStub;
    }

    @Override
    public Response loginUser(Logindto logindto) {
        logger.info("fetchUserCredentials From DataBase");
        VerifyCredentialsResponse verifyCredentialsResponse = fetchUserCredentials(logindto);

        String userName = verifyCredentialsResponse.getName();
        boolean response = CredentialVerfication.verifyCredentials(logindto, verifyCredentialsResponse);

        String sessionId = null;
        if (response) {
            logger.info("CredentialVerfication Successfull !!!");
            SessionStatusRequest sessionStatusRequest = sessionMapper.maptoSessionStatusRequest(logindto);
            SessionStatusResponse response1 = sessionServiceBlockingStub.checkSessionStatus(sessionStatusRequest);

            if (response1.getStatus()) {
                logger.info("Session For User AlreadyExist");
                throw new ActiveSessionException();
            }
            sessionId = SessionIdGenerator.generateSessionId();
            CreateSessionRequest createSessionRequest = sessionMapper.maptoCreateSessionRequest(logindto, sessionId);
            logger.info("Create Session Request To Server");
            sessionServiceBlockingStub.createSession(createSessionRequest);
        }
        return new Response(Map.of("sessionId", sessionId, "name", userName), 200);
    }


    public VerifyCredentialsResponse fetchUserCredentials(Logindto logindto) {

        VerifyCredentialsRequest verifyCredentialsRequest = sessionMapper.maptoVerifyCredentialsRequest(logindto);
        VerifyCredentialsResponse verifyCredentialsResponse = stubofUser.verifyCredentials(verifyCredentialsRequest);
        return verifyCredentialsResponse;

    }
}
