package com.osc.dashboardservice.serviceImpl;

import com.Product.GetCartProductDetailsRequest;
import com.Product.GetCartProductDetailsResponse;
import com.Product.ProductDataServiceGrpc;
import com.cart.CartDetailsRequest;
import com.cart.CartDetailsResponse;
import com.cart.CartServiceGrpc;
import com.osc.dashboardservice.dto.ApiResponse;
import com.osc.dashboardservice.dto.CartDataRequestDto;
import com.osc.dashboardservice.mapper.MapToCartDetailDataObject;
import com.osc.dashboardservice.mapper.MapToProductDetailDataObject;
import com.osc.dashboardservice.mapper.RequestMapper;
import com.osc.dashboardservice.service.GetCartDetailesService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GetCartDetailesServiceImpl implements GetCartDetailesService {

    private CartServiceGrpc.CartServiceBlockingStub cartServiceBlockingStub;
    private ProductDataServiceGrpc.ProductDataServiceBlockingStub productDataServiceBlockingStub;
    private MapToCartDetailDataObject mapToCartDetailDataObject;

    public GetCartDetailesServiceImpl(@Qualifier("cartProductServiceBlockingStub")CartServiceGrpc.CartServiceBlockingStub cartServiceBlockingStub,
                                      @Qualifier("productDataServiceBlockingStub")ProductDataServiceGrpc.ProductDataServiceBlockingStub productDataServiceBlockingStub, MapToCartDetailDataObject mapToCartDetailDataObject) {
        this.cartServiceBlockingStub = cartServiceBlockingStub;
        this.productDataServiceBlockingStub = productDataServiceBlockingStub;
        this.mapToCartDetailDataObject = mapToCartDetailDataObject;
    }

    @Override
    public ResponseEntity<ApiResponse> getCartDetails(CartDataRequestDto cartDataRequestDto) {

        CartDetailsRequest request = CartDetailsRequest.newBuilder().setUserId(cartDataRequestDto.getUserId()).build();
        CartDetailsResponse response = cartServiceBlockingStub.fetchCartDetails(request);

        GetCartProductDetailsRequest request1 = RequestMapper.maptoGetCartProductDetailsRequest(response);

        GetCartProductDetailsResponse getCartProductDetailsResponse = productDataServiceBlockingStub.getCartProductDetails(request1);

        ApiResponse map = mapToCartDetailDataObject.map(getCartProductDetailsResponse, response, cartDataRequestDto.getUserId());

        return ResponseEntity.ok(map);
    }
}
