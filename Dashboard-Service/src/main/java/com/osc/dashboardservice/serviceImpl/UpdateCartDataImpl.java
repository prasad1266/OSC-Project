package com.osc.dashboardservice.serviceImpl;

import com.Product.ProductDataServiceGrpc;
import com.cart.CartServiceGrpc;
import com.cart.ProductQuantityRequest;
import com.osc.dashboardservice.dto.UpdateCartDto;
import com.osc.dashboardservice.mapper.RequestMapper;
import com.osc.dashboardservice.service.UpdateCartDataService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UpdateCartDataImpl implements UpdateCartDataService {

    private CartServiceGrpc.CartServiceBlockingStub cartServiceBlockingStub;

    public UpdateCartDataImpl(@Qualifier("cartProductServiceBlockingStub")CartServiceGrpc.CartServiceBlockingStub cartServiceBlockingStub) {
        this.cartServiceBlockingStub = cartServiceBlockingStub;
    }

    @Override
    public void addCartProduct(UpdateCartDto updateCartDto) {
        ProductQuantityRequest request = RequestMapper.mapToProductQuantityRequest(updateCartDto);
        cartServiceBlockingStub.increaseProductQuantity(request);
    }

    @Override
    public void decreaseCartProduct(UpdateCartDto updateCartDto) {
        ProductQuantityRequest request = RequestMapper.mapToProductQuantityRequest(updateCartDto);
        cartServiceBlockingStub.decreaseProductQuantity(request);
    }

    @Override
    public void removeCartProduct(UpdateCartDto updateCartDto) {
        ProductQuantityRequest request = RequestMapper.mapToProductQuantityRequest(updateCartDto);
        cartServiceBlockingStub.removeProduct(request);
    }
}
