package com.osc.productdataservice.serviceImpl;

import com.Product.GetCartProductDetailsRequest;
import com.Product.GetCartProductDetailsResponse;
import com.osc.avro.files.Product;
import com.osc.productdataservice.mapper.ProductMapper;
import com.osc.productdataservice.service.GetCartProductDetailsService;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class GetCartProductDetailsServiceImpl implements GetCartProductDetailsService {
    private ProductMapper productMapper;
    private ReadOnlyKeyValueStore<String, Product> productStore;

    public GetCartProductDetailsServiceImpl(ProductMapper productMapper,
                                            @Qualifier("productStore")ReadOnlyKeyValueStore<String, Product> productStore) {

        this.productMapper = productMapper;
        this.productStore = productStore;
    }

    @Override
    public GetCartProductDetailsResponse getCartProducts(GetCartProductDetailsRequest request) {
        List<String> productIds = new ArrayList<>();
        for (String productId : request.getProductIdsList()) {
            productIds.add(productId);
        }
        List<com.Product.Product> products = productMapper.fetchAndMapToProto(productIds);
        return GetCartProductDetailsResponse.newBuilder().addAllCartProducts(products).build();

    }
}
