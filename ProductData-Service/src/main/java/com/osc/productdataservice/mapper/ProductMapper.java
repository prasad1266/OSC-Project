package com.osc.productdataservice.mapper;

import com.Product.DashboardRequest;
import com.Product.FilterProductResponse;
import com.osc.avro.files.Product;
import com.osc.productdataservice.entity.Products;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Component
public class ProductMapper {

    private  ReadOnlyKeyValueStore<String, Product> productStore;

    public ProductMapper(@Qualifier("productStore") ReadOnlyKeyValueStore<String, Product> productStore) {
        this.productStore = productStore;
    }

    public static Product toProductAvro(Products product){
        return Product.newBuilder()
                .setCategoryId(String.valueOf(product.getCategoryId()))
                .setProductName(product.getProductName())
                .setProductPrice(product.getProductPrice())
                .setAddedDateTime(System.currentTimeMillis())
                .setImagePath(product.getImagePath())
                .setProductDescription(product.getProductDescription())
                .build();
    }

    public static List<String> mapToRecentlyViewList(DashboardRequest request){
        List<String> list = request.getProductIdList();
        return list;
    }

    public  Map<String, Product> getProductsByCategory(String categoryId) {
        KeyValueIterator<String, Product> products = productStore.all();
        Map<String, Product> productMap = new HashMap<>();

        while (products.hasNext()) {
            KeyValue<String, Product> entry = products.next();
            Product product = entry.value;

            if (product.getCategoryId().toString().trim().equals(categoryId.trim()) && !productMap.containsKey(entry.key.trim()) ) {
                productMap.put(entry.key.trim(), product);
            }
        }
        return productMap;
    }

    public List<com.Product.Product> fetchAndMapToProto(List<String> productIds) {
        return productIds.stream()
                .map(productId -> {
                   Product domainProduct = productStore.get(productId);
                        return com.Product.Product.newBuilder()
                                .setProductId(productId)
                                .setCategoryId(domainProduct.getCategoryId().toString())
                                .setName(domainProduct.getProductName().toString())
                                .setProductDetails(domainProduct.getProductDescription().toString())
                                .setPrice(domainProduct.getProductPrice())
                                .setImage(domainProduct.getImagePath().toString())
                                .build();


                })
                .collect(Collectors.toList());

    }

    public com.Product.Product mapAvroToProto(Product product, String productId) {
        return com.Product.Product.newBuilder()
                .setProductId(productId)
                .setCategoryId(product.getCategoryId().toString())
                .setName(product.getProductName().toString())
                .setProductDetails(product.getProductDescription().toString())
                .setPrice(product.getProductPrice())
                .setImage(product.getImagePath().toString())
                .build();
    }

    public FilterProductResponse map(List<Map.Entry<String, Product>> productEntries) {
        // Start building the response
        FilterProductResponse.Builder responseBuilder = FilterProductResponse.newBuilder();

        // Iterate over productEntries and map each Avro Product to Proto Product
        List<com.Product.Product> protoProducts = productEntries.stream().map(entry -> {
           Product avroProduct = entry.getValue();

            // Build the Proto Product from Avro Product
           return com.Product.Product.newBuilder()
                    .setProductId(entry.getKey())
                    .setCategoryId(avroProduct.getCategoryId().toString())
                    .setName(avroProduct.getProductName().toString())
                    .setProductDetails(avroProduct.getProductDescription().toString())
                    .setPrice(avroProduct.getProductPrice())
                    .setImage(avroProduct.getImagePath().toString())
                    .build();


        }).collect(Collectors.toList());

        // Add all Proto Products to the response
        responseBuilder.addAllProducts(protoProducts);

        // Build and return the FilterProductResponse
        return responseBuilder.build();
    }



}
