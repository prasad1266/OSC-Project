//package com.osc.productdataservice.configuration;
//
//
//import com.recentHistory.RecentlyViewedServiceGrpc;
//import com.user.UserDataServiceGrpc;
//import io.grpc.ManagedChannel;
//import io.grpc.ManagedChannelBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class GrpcClientConfig {
//
//    private ManagedChannel managedChannel;
//
//    @Bean
//    public ManagedChannel managedChannel() {
//        return ManagedChannelBuilder.forAddress("localhost", 8090)
//                .usePlaintext()
//                .build();
//    }
//    @Bean
//    public RecentlyViewedServiceGrpc.RecentlyViewedServiceBlockingStub userServiceBlockingStub(){
//        return RecentlyViewedServiceGrpc.newBlockingStub(managedChannel());
//    }
//
//}
