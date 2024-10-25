package com.osc.productdataservice.Utility;

import com.osc.avro.files.ProductViewCount;
import com.osc.productdataservice.entity.ProductCount;
import com.osc.productdataservice.kafka.KafkaManagerService;
import com.osc.productdataservice.repository.ProductCountRepository;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ProductViewCountSave {

    private KafkaManagerService kafkaManagerService;
    private ReadOnlyKeyValueStore<String, ProductViewCount> productViewCountStore;
    private ProductCountRepository productCountRepository;

    public ProductViewCountSave(KafkaManagerService kafkaManagerService, @Qualifier("productViewCountStore") ReadOnlyKeyValueStore<String, ProductViewCount> productViewCountStore, ProductCountRepository productCountRepository) {
        this.kafkaManagerService = kafkaManagerService;
        this.productViewCountStore = productViewCountStore;
        this.productCountRepository = productCountRepository;
    }
    @Scheduled(initialDelay = 60000, fixedRate = 600000)
    public void saveProductViewCount() {
        System.out.println("Updating Recoreds In DB");
        Map<String, ProductViewCount> viewCount = kafkaManagerService.fetchProductViewCountMap();

        Map<String, ProductCount> viewCountEntity = new HashMap<>();

        for (Map.Entry<String, ProductViewCount> entry : viewCount.entrySet()) {
            String productId = entry.getKey();
            ProductViewCount productViewCountAvro = entry.getValue();

            ProductCount productCountEntity = new ProductCount();
            productCountEntity.setProductId(productId);

            productCountEntity.setCategoryId(productViewCountAvro.getCategoryId().charAt(0));

            productCountEntity.setViewCount(productViewCountAvro.getViewCount());

            viewCountEntity.put(productId, productCountEntity);

        }
        if(!viewCountEntity.isEmpty()){
            productCountRepository.saveAll(viewCountEntity.values());
        }
    }
}
