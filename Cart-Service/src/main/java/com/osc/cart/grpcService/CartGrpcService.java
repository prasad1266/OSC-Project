package com.osc.cart.grpcService;


import com.cart.CartDetailsRequest;
import com.cart.CartDetailsResponse;
import com.cart.CartServiceGrpc.CartServiceImplBase;
import com.cart.ProductQuantityRequest;
import com.google.protobuf.Empty;
import com.osc.cart.service.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class CartGrpcService extends CartServiceImplBase {
    private FetchCartDetailsServic fetchCartDetailsServic;
    private IncreaseProductQuantityService increaseProductQuantityService;
    private DecreaseProductQuantityService decreaseProductQuantityService;
    private RemoveCartProductService removeCartProductService;
    private UpdateProductQuantityService updateProductQuantityService;


    public CartGrpcService(FetchCartDetailsServic fetchCartDetailsServic, IncreaseProductQuantityService increaseProductQuantityService, DecreaseProductQuantityService decreaseProductQuantityService, RemoveCartProductService removeCartProductService, UpdateProductQuantityService updateProductQuantityService) {
        this.fetchCartDetailsServic = fetchCartDetailsServic;
        this.increaseProductQuantityService = increaseProductQuantityService;
        this.decreaseProductQuantityService = decreaseProductQuantityService;
        this.removeCartProductService = removeCartProductService;
        this.updateProductQuantityService = updateProductQuantityService;
    }

    @Override
    public void fetchCartDetails(CartDetailsRequest request, StreamObserver<CartDetailsResponse> responseObserver) {
         CartDetailsResponse response= fetchCartDetailsServic.getCartDetails(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
    @Override
    public void increaseProductQuantity(ProductQuantityRequest request, StreamObserver<Empty> responseObserver) {
        increaseProductQuantityService.increaseproductQuantity(request);
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void decreaseProductQuantity(ProductQuantityRequest request, StreamObserver<Empty> responseObserver) {
        decreaseProductQuantityService.decreaseproductQuantity(request);
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void updateProductQuantity(ProductQuantityRequest request, StreamObserver<Empty> responseObserver) {
        updateProductQuantityService.updateProductQuantityinDatabase(request);
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void removeProduct(ProductQuantityRequest request, StreamObserver<Empty> responseObserver) {
        removeCartProductService.removeProductFromCart(request);
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }
}
