package com.osc.sessionservice.configuration;


import com.session.SessionServiceGrpc;
import com.user.UserDataServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClientConfig {

    private ManagedChannel managedChannel;


    @Bean(name = "userManagedChannel")
    public ManagedChannel userManagedChannel() {
        return ManagedChannelBuilder.forAddress("localhost", 8090)
                .usePlaintext()
                .build();
    }

    @Bean(name = "sessionDataManagedChannel")
    public ManagedChannel sessionDataManagedChannel() {
        return ManagedChannelBuilder.forAddress("localhost", 8092)
                .usePlaintext()
                .build();
    }

    @Bean(name = "userServiceBlockingStub")
    public SessionServiceGrpc.SessionServiceBlockingStub userServiceBlockingStub(){
        return SessionServiceGrpc.newBlockingStub(userManagedChannel());
    }

    @Bean(name = "sessionServiceBlockingStub")
    public SessionServiceGrpc.SessionServiceBlockingStub sessionServiceBlockingStub(){
        return SessionServiceGrpc.newBlockingStub(sessionDataManagedChannel());
    }


}
