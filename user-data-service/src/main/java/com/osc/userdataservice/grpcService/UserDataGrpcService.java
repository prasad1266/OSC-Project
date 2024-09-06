package com.osc.userdataservice.grpcService;

import com.osc.userdataservice.service.ResetUserPasswordService;
import com.osc.userdataservice.service.RegisterUserService;

import com.osc.userdataservice.service.VerifyEmailAddressService;
import com.user.*;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class UserDataGrpcService extends UserDataServiceGrpc.UserDataServiceImplBase {

        public UserDataGrpcService(RegisterUserService registerUserService,
                                   ResetUserPasswordService resetUserPasswordService,
                                   VerifyEmailAddressService verifyEmailAddressService) {

        this.registerUserService = registerUserService;
        this.resetUserPasswordService = resetUserPasswordService;
        this.verifyEmailAddressService = verifyEmailAddressService;

    }


    private RegisterUserService registerUserService;
    private ResetUserPasswordService resetUserPasswordService;
    private VerifyEmailAddressService verifyEmailAddressService;
    private RegisterUserResponse registerUserResponse;

    private UniqueEmailResponse uniqueResponse;


    @Override
    public void registerUser(RegisterUserRequest request, StreamObserver<RegisterUserResponse> responseObserver) {
        registerUserResponse = registerUserService.registerUser(request);
        responseObserver.onNext(registerUserResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void verifyEmailAddressIsUnique(UniqueEmailRequest request, StreamObserver<UniqueEmailResponse> responseObserver) {

       UniqueEmailResponse response = verifyEmailAddressService.verifyEmailAddressIsUnique(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void resetPassword(ResetPasswordRequest request, StreamObserver<ResetPasswordResponse> responseObserver) {
        ResetPasswordResponse resetPasswordResponse = resetUserPasswordService.resetPassword(request);
        responseObserver.onNext(resetPasswordResponse);
        responseObserver.onCompleted();
    }

}
