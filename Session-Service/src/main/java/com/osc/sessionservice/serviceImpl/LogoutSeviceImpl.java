package com.osc.sessionservice.serviceImpl;

import com.osc.sessionservice.constants.StatusCodes;
import com.osc.sessionservice.dto.Logoutdto;
import com.osc.sessionservice.mapper.SessionMapper;
import com.osc.sessionservice.response.Response;
import com.osc.sessionservice.service.LogoutSevice;
import com.osc.sessionservice.service.VerifyUserCredentialsService;
import com.session.LogoutRequest;
import com.session.LogoutResponse;
import com.session.SessionServiceGrpc;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class LogoutSeviceImpl implements LogoutSevice {
    private SessionServiceGrpc.SessionServiceBlockingStub stub;

    public LogoutSeviceImpl(@Qualifier("sessionServiceBlockingStub") SessionServiceGrpc.SessionServiceBlockingStub stub) {
        this.stub = stub;

    }
    public Response logoutUser(Logoutdto logoutdto) {
        LogoutRequest logoutRequest = SessionMapper.mapToLogoutRequest(logoutdto);
        LogoutResponse logoutResponse = stub.sessionLogout(logoutRequest);

        return new Response(logoutResponse.getIsSessionLogout(), StatusCodes.LOGOUT_SUCCESS);
    }

}
