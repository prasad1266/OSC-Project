package com.osc.cart.serviceImpl;

import com.cart.CartDetailsRequest;
import com.cart.CartDetailsResponse;
import com.osc.avro.files.CartList;
import com.osc.avro.files.RecentViewHistory;
import com.osc.cart.mapper.CartMapper;
import com.osc.cart.service.FetchCartDetailsServic;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.stereotype.Service;

@Service
public class FetchCartDetailsServiceImpl implements FetchCartDetailsServic {

    private ReadOnlyKeyValueStore<String, CartList>  cartProductsStore;

    public FetchCartDetailsServiceImpl(ReadOnlyKeyValueStore<String, CartList> cartProductsStore) {
        this.cartProductsStore = cartProductsStore;
    }

    @Override
    public CartDetailsResponse getCartDetails(CartDetailsRequest request) {

        CartList cartList = cartProductsStore.get(request.getUserId().toString());
        System.out.println(cartList);
        CartDetailsResponse response;
        if(cartList!=null) {
             response = CartMapper.maptoCartDetailsResponse(cartList);
            return response;
        }
         response = null;
        return response;
    }
}
