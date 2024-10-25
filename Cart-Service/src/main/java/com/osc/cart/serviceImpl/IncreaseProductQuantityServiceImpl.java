package com.osc.cart.serviceImpl;

import com.cart.ProductQuantityRequest;
import com.osc.avro.files.CartList;
import com.osc.avro.files.CartProduct;
import com.osc.cart.Constants.AppConstants;
import com.osc.cart.mapper.CartMapper;
import com.osc.cart.service.IncreaseProductQuantityService;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class IncreaseProductQuantityServiceImpl implements IncreaseProductQuantityService {
    private ReadOnlyKeyValueStore<String, CartList> cartProductsStore;
    private KafkaTemplate<String, CartList> kafkaTemplate;

    public IncreaseProductQuantityServiceImpl(@Qualifier("Cart-Products-Store") ReadOnlyKeyValueStore<String, CartList> cartProductsStore,
                                              @Qualifier("kafkaTemaplate") KafkaTemplate<String, CartList> kafkaTemplate) {
        this.cartProductsStore = cartProductsStore;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void increaseproductQuantity(ProductQuantityRequest request) {

        String userId = request.getUserId();
        CartList cartData = cartProductsStore.get(request.getUserId());

        //for New User having no cart Data
        if (cartData == null) {
            cartData = CartMapper.addNewProduct(request);
            kafkaTemplate.send(AppConstants.CART_PRODUCT_Topic,userId,cartData);

        }
        else {
            List<CartProduct> cartProductList = new ArrayList<>(cartData.getProducts());
            boolean productFound = false;

            for (CartProduct product : cartProductList) {
                if (product.getProductId().toString().equals(request.getProductId())) {
                    int newQuantity = product.getProductQuantity() + 1;
                    product.setProductQuantity(newQuantity);
                    productFound = true;
                    break;  // Break the loop once the product is found and updated
                }
            }

            // If the product is not found in the cart, add it
            if (!productFound) {
                CartProduct cartProduct = CartProduct.newBuilder()
                        .setProductId(request.getProductId())
                        .setProductQuantity(request.getQuantity())
                        .build();
                cartProductList.add(cartProduct);
            }

            cartData = CartList.newBuilder().setProducts(cartProductList).build();
            kafkaTemplate.send(AppConstants.CART_PRODUCT_Topic, userId, cartData);
        }
//        else {
//            List<CartProduct> cartProductList = new ArrayList<>(cartData.getProducts());
//
//            for (CartProduct product : cartProductList) {
//                if (product.getProductId().equals(request.getProductId())) {
//                    int newQuantity = product.getProductQuantity() + 1;
//                    product.setProductQuantity(newQuantity);
//                } else {
//                    // Add new Product and Quantity for Exising Cart
//                    CartProduct cartProduct = CartProduct.newBuilder().setProductId(request.getProductId()).setProductQuantity(request.getQuantity()).build();
//                    cartProductList.add(cartProduct);
//                }
//
//            }
//            cartData =  CartList.newBuilder().setProducts(cartProductList).build();
//            kafkaTemplate.send(AppConstants.CART_PRODUCT_Topic,userId,cartData);
//        }
    }
}
