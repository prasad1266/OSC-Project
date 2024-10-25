package com.osc.dashboardservice.mapper;

import com.Product.GetCartProductDetailsResponse;
import com.Product.Product;
import com.cart.CartDetailsResponse;
import com.cart.ProductDetails;
import com.osc.dashboardservice.dto.ApiResponse;
import com.osc.dashboardservice.dto.CartViewResponseDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MapToCartDetailDataObject {

    public ApiResponse map(GetCartProductDetailsResponse getCartProductDetailsResponse,
                           CartDetailsResponse cartDetailsResponse,
                           String userId) {


        List<Product> ProductsList = getCartProductDetailsResponse.getCartProductsList();

        List<ProductDetails> cartProductsList = cartDetailsResponse.getCartProductsList();

        Map<String, Integer> productQuantityMap = mapProductIdToQuantity(cartProductsList);


        List<CartViewResponseDto> cartResponseList = new ArrayList<>();

        List<CartViewResponseDto> productList = new ArrayList<>();

        int productCount = ProductsList.size();
        double totalPrize = 0;

        for (Product product : ProductsList) {
            CartViewResponseDto cartViewResponseDto = new CartViewResponseDto();
            totalPrize += product.getPrice();
            cartViewResponseDto.setUserId(userId);
            cartViewResponseDto.setProductName(product.getName());
            cartViewResponseDto.setProductId(product.getProductId());
            cartViewResponseDto.setCategoryId(product.getCategoryId());
            cartViewResponseDto.setProductPrice(product.getPrice());
            cartViewResponseDto.setQuantity(productQuantityMap.get(product.getProductId()));

            cartResponseList.add(cartViewResponseDto);
        }


        Map<String, Object> dataObject = Map.of(
                "cartProducts", cartResponseList,
                "productsCartCount", productCount,
                "totalPrice", totalPrize

        );

        return new ApiResponse(200, dataObject);
    }


    public static Map<String, Integer> mapProductIdToQuantity(List<ProductDetails> cartProductsList) {
        Map<String, Integer> productQuantityMap = new HashMap<>();

        for (ProductDetails product : cartProductsList) {

            productQuantityMap.put(product.getProductId(), product.getQuantity());
        }
        return productQuantityMap;
    }
}
