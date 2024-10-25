package com.osc.cart.mapper;

import com.cart.CartDetailsResponse;
import com.cart.ProductDetails;
import com.cart.ProductQuantityRequest;
import com.osc.avro.files.CartList;
import com.osc.avro.files.CartProduct;

import java.util.ArrayList;
import java.util.List;

public class CartMapper {

    public static CartDetailsResponse maptoCartDetailsResponse(CartList cartList) {
        CartDetailsResponse.Builder responseBuilder = CartDetailsResponse.newBuilder();

        for (CartProduct product : cartList.getProducts()) {
            ProductDetails productDetails = ProductDetails.newBuilder()
                    .setProductId(product.getProductId().toString())
                    .setQuantity(product.getProductQuantity())
                    .build();

            responseBuilder.addCartProducts(productDetails);
        }
        return responseBuilder.build();
    }

    public static CartList addNewProduct(ProductQuantityRequest request) {
        List<CartProduct> cartProductList = new ArrayList<>();
       CartProduct newProduct = CartProduct.newBuilder().setProductId(request.getProductId()).setProductQuantity(request.getQuantity()).build();
        cartProductList.add(newProduct);
        return CartList.newBuilder().setProducts(cartProductList).build();
    }
}
