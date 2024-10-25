package com.osc.cart.serviceImpl;

import com.cart.ProductQuantityRequest;
import com.osc.avro.files.CartList;
import com.osc.avro.files.CartProduct;
import com.osc.cart.entity.CartEntity;
import com.osc.cart.entity.CartProductRecord;
import com.osc.cart.repository.CartRepository;
import com.osc.cart.service.UpdateProductQuantityService;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UpdateProductQuantityServiceImpl implements UpdateProductQuantityService {
private ReadOnlyKeyValueStore<String, CartList> cartProductsStore;
private CartRepository cartRepository;

    public UpdateProductQuantityServiceImpl(ReadOnlyKeyValueStore<String, CartList> cartProductsStore, CartRepository cartRepository) {
        this.cartProductsStore = cartProductsStore;
        this.cartRepository = cartRepository;
    }

    @Override
    public void updateProductQuantityinDatabase(ProductQuantityRequest request) {
        String userId = request.getUserId();
        CartList cartList = cartProductsStore.get(userId);

        if(cartList ==null){
            cartList.setProducts(new ArrayList<>());
        }
        List<CartProduct> cartListProducts = cartList.getProducts();
        List<CartProductRecord> cartProductRecords = cartListProducts.stream().map(entry -> {
            return new CartProductRecord(entry.getProductId().toString(), entry.getProductQuantity());
        }).toList();


        cartRepository.save(new CartEntity(userId,cartProductRecords));

    }
}
