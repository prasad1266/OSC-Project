package com.osc.cart.serviceImpl;

import com.cart.ProductQuantityRequest;
import com.osc.avro.files.CartList;
import com.osc.avro.files.CartProduct;
import com.osc.cart.Constants.AppConstants;
import com.osc.cart.service.RemoveCartProductService;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RemoveCartProductServiceImpl implements RemoveCartProductService {

    private ReadOnlyKeyValueStore<String, CartList> cartProductsStore;
    private KafkaTemplate<String, CartList> kafkaTemplate;

    public RemoveCartProductServiceImpl(@Qualifier("Cart-Products-Store")ReadOnlyKeyValueStore<String, CartList> cartProductsStore,
                                        @Qualifier("kafkaTemaplate")KafkaTemplate<String, CartList> kafkaTemplate) {
        this.cartProductsStore = cartProductsStore;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void removeProductFromCart(ProductQuantityRequest request) {
        String userId = request.getUserId();
        CartList cartData = cartProductsStore.get(userId);
        List<CartProduct> cartProductList = new ArrayList<>(cartData.getProducts());

        cartProductList.removeIf(product -> product.getProductId().toString().equals(request.getProductId()));


        cartData = CartList.newBuilder().setProducts(cartProductList).build();
        kafkaTemplate.send(AppConstants.CART_PRODUCT_Topic, userId, cartData);
    }
}
