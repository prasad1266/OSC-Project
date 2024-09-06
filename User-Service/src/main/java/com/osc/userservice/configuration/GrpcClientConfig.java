package com.osc.userservice.configuration;


import com.user.UserDataServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClientConfig {
    //@GrpcClient("userService")
    private ManagedChannel managedChannel;


    @Bean
    public ManagedChannel managedChannel() {
        return ManagedChannelBuilder.forAddress("localhost", 8090)
                .usePlaintext()
                .build();
    }
    @Bean
    public UserDataServiceGrpc.UserDataServiceBlockingStub userServiceBlockingStub(){
        return UserDataServiceGrpc.newBlockingStub(managedChannel());
    }

}
