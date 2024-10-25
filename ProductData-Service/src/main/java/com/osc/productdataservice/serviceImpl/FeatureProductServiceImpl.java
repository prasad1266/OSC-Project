package com.osc.productdataservice.serviceImpl;

import com.Product.Product;
import com.osc.productdataservice.kafka.KafkaManagerService;
import com.osc.productdataservice.service.FeatureProductService;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class FeatureProductServiceImpl implements FeatureProductService {
    private ReadOnlyKeyValueStore<String, com.osc.avro.files.Product> productStore;

    public FeatureProductServiceImpl(ReadOnlyKeyValueStore<String, com.osc.avro.files.Product> productStore) {
        this.productStore = productStore;
    }


    @Autowired
    private KafkaManagerService kafkaManagerService;

    @Override
    public List<Product> getFeatureProducts() {
        Map<String, Integer> viewCountMap = kafkaManagerService.fetchProductViewCountData();

        return viewCountMap.entrySet().stream()
                // Sort by viewCount in descending order
                .sorted((entry1, entry2) -> Integer.compare(entry2.getValue(), entry1.getValue()))
                // Limit to the top 12 products
                .limit(12)
                .map(entry -> {
                    String productId = entry.getKey();
                    int viewCount = entry.getValue();

                    // Retrieve the Product from the productStore using productId
                    com.osc.avro.files.Product product = productStore.get(productId);

                    if (product != null) {
                        // Map Avro Product object to Proto Product
                        return Product.newBuilder()
                                .setProductId(productId)
                                .setCategoryId(product.getCategoryId().toString())
                                .setName(product.getProductName().toString())
                                .setProductDetails(product.getProductDescription().toString())
                                .setPrice(product.getProductPrice())
                                .setImage(product.getImagePath().toString())
                                .build();
                    } else {
                        // If product is not found, return null
                        System.out.println("Product not found for productId: " + productId);
                        return null;
                    }
                })
                // Filter out null values in case a product wasn't found
                .filter(productProto -> productProto != null)
                .collect(Collectors.toList());
    }
}
