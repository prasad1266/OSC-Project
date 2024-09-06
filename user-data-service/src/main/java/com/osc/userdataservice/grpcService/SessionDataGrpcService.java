package com.osc.userdataservice.grpcService;

import com.osc.userdataservice.service.VerifyUserCredentialsService;
import com.session.SessionServiceGrpc;
import com.session.VerifyCredentialsRequest;
import com.session.VerifyCredentialsResponse;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class SessionDataGrpcService extends SessionServiceGrpc.SessionServiceImplBase {
    private  VerifyUserCredentialsService verifyUserCredentialsService;

    public SessionDataGrpcService( VerifyUserCredentialsService verifyUserCredentialsService) {
        this.verifyUserCredentialsService =verifyUserCredentialsService;
    }

    @Override
    public void verifyCredentials(VerifyCredentialsRequest request, StreamObserver<VerifyCredentialsResponse> responseObserver) {

        VerifyCredentialsResponse verifyCredentialsResponse = verifyUserCredentialsService.verifyCredentialsFromDB(request);
        responseObserver.onNext(verifyCredentialsResponse);
        responseObserver.onCompleted();
    }
}
