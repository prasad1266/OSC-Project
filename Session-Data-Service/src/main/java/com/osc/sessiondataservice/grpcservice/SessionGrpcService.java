package com.osc.sessiondataservice.grpcservice;

import com.osc.sessiondataservice.service.CheckSessionStatusService;
import com.osc.sessiondataservice.service.CreateSessionService;
import com.osc.sessiondataservice.service.LogoutUserService;
import com.session.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class SessionGrpcService extends SessionServiceGrpc.SessionServiceImplBase {

    private CheckSessionStatusService checkSessionStatusService;
    private  LogoutUserService logoutUserService;
private CreateSessionService createSessionService;
    private CreateSessionResponse createSessionResponse;
    private SessionStatusResponse sessionStatusResponse;

    public SessionGrpcService(LogoutUserService logoutUserService,
                              CheckSessionStatusService checkSessionStatusService,
                              CreateSessionService createSessionService) {
        this.logoutUserService = logoutUserService;
        this.checkSessionStatusService =checkSessionStatusService;
        this.createSessionService =createSessionService;
    }

    @Override
    public void checkSessionStatus(SessionStatusRequest request, StreamObserver<SessionStatusResponse> responseObserver) {
        sessionStatusResponse = checkSessionStatusService.checkSessionSattusinKtable(request);
        responseObserver.onNext(sessionStatusResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void createSession(CreateSessionRequest request, StreamObserver<CreateSessionResponse> responseObserver) {
        createSessionResponse = createSessionService.CreateSession(request);
        responseObserver.onNext(createSessionResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void sessionLogout(LogoutRequest request, StreamObserver<LogoutResponse> responseObserver) {
        LogoutResponse logoutResponse =  logoutUserService.logoutUser(request);
        responseObserver.onNext(logoutResponse);
        responseObserver.onCompleted();
    }
}
