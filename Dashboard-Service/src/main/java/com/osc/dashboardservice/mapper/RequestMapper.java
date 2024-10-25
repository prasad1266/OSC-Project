package com.osc.dashboardservice.mapper;

import com.Product.FilterProductRequest;
import com.Product.GetCartProductDetailsRequest;
import com.Product.ProductDataRequest;
import com.cart.CartDetailsResponse;
import com.cart.ProductDetails;
import com.cart.ProductQuantityRequest;
import com.osc.dashboardservice.dto.FilterProductRequestDto;
import com.osc.dashboardservice.dto.GetProductDetailsDto;
import com.osc.dashboardservice.dto.UpdateCartDto;
import com.recentHistory.UpdateRecentViewedRequest;

import java.util.ArrayList;
import java.util.List;

public class RequestMapper {
    public static ProductDataRequest mapToProductDataRequest(GetProductDetailsDto getProductDetailsDto) {
        System.out.println("getProductDetailsDto"+getProductDetailsDto);
        return ProductDataRequest.newBuilder().setProductId(getProductDetailsDto.getProductId()).build();

    }


    public static UpdateRecentViewedRequest maptoUpdateRecentViewedRequest(GetProductDetailsDto getProductDetailsDto) {
        return UpdateRecentViewedRequest.newBuilder().
                setProductId(getProductDetailsDto.getProductId()).
                setUserId(getProductDetailsDto.getUserId()).build();
    }

    public static FilterProductRequest mapToFilterProductRequest(FilterProductRequestDto filterProductRequestDto) {
        return FilterProductRequest.newBuilder().setFilter(filterProductRequestDto.getFilter())
                .setCategoryId(filterProductRequestDto.getCatId())
                .setUserId(filterProductRequestDto.getUserId()).build();
    }

    public static GetCartProductDetailsRequest maptoGetCartProductDetailsRequest(CartDetailsResponse cartDetailsResponse) {
        List<ProductDetails> cartProductsList = cartDetailsResponse.getCartProductsList();
        List<String> productIds = new ArrayList<>();
        for(ProductDetails product:cartProductsList){
            productIds.add(product.getProductId());
        }
      return GetCartProductDetailsRequest.newBuilder().addAllProductIds(productIds).build();
    }

    public static ProductQuantityRequest mapToProductQuantityRequest(UpdateCartDto updateCartDto) {
        return ProductQuantityRequest.newBuilder().setUserId(updateCartDto.getUserId())
                .setProductId(updateCartDto.getProductId())
                .setQuantity(updateCartDto.getCount()).build();
    }
}
