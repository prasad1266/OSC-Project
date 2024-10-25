package com.osc.sessionservice.configuration;


import com.cart.CartServiceGrpc;
import com.recentHistory.RecentlyViewedServiceGrpc;
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

    @Bean(name = "recentlyViewdManagedChanel")
    public ManagedChannel recentlyViewdmanagedChannel() {
        return ManagedChannelBuilder.forAddress("localhost", 8093)
                .usePlaintext()
                .build();
    }
    @Bean(name = "recentlyViewdServiceBlockingStub")
    public RecentlyViewedServiceGrpc.RecentlyViewedServiceBlockingStub recentlyViewedServiceBlockingStub(){
        return RecentlyViewedServiceGrpc.newBlockingStub(recentlyViewdmanagedChannel());
    }
    @Bean(name = "cartProductManagedChannel")
    public ManagedChannel cartProductsManagedChannel() {
        return ManagedChannelBuilder.forAddress("localhost", 8099)
                .usePlaintext()
                .build();
    }
    @Bean(name = "cartProductServiceBlockingStub")
    public CartServiceGrpc.CartServiceBlockingStub CartProductServiceBlockingStub(){
        return CartServiceGrpc.newBlockingStub(cartProductsManagedChannel());
    }
}
