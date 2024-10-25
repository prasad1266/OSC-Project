package com.osc.dashboardservice.config;

import com.Product.ProductDataServiceGrpc;
import com.cart.CartServiceGrpc;
import com.recentHistory.RecentlyViewedServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcConfiguration {
    private ManagedChannel managedChannel;


    @Bean(name = "recentlyViewdManagedChanel")
    public ManagedChannel recentlyViewdmanagedChannel() {
        return ManagedChannelBuilder.forAddress("localhost", 8093)
                .usePlaintext()
                .build();
    }

    @Bean(name = "productDataManagedChannel")
    public ManagedChannel productDataManagedChannel() {
        return ManagedChannelBuilder.forAddress("localhost", 8096)
                .usePlaintext()
                .build();
    }
    @Bean(name = "recentlyViewdServiceBlockingStub")
    public RecentlyViewedServiceGrpc.RecentlyViewedServiceBlockingStub recentlyViewedServiceBlockingStub(){
        return RecentlyViewedServiceGrpc.newBlockingStub(recentlyViewdmanagedChannel());
    }

    @Bean(name = "productDataServiceBlockingStub")
    public ProductDataServiceGrpc.ProductDataServiceBlockingStub productDataServiceBlockingStub(){
        return ProductDataServiceGrpc.newBlockingStub(productDataManagedChannel());
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
