package com.osc.productdataservice.kafka;

import com.osc.avro.files.ProductViewCount;
import com.osc.productdataservice.constants.AppConstants;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class KafkaManagerService {

    private KafkaTemplate<String, Object> productViewCountKafkaTemplate;
    private ReadOnlyKeyValueStore<String, ProductViewCount> productViewCountStore;

    public KafkaManagerService(@Qualifier("kafkaTemaplate") KafkaTemplate<String, Object> productViewCountKafkaTemplate, ReadOnlyKeyValueStore<String, ProductViewCount> productViewCountStore) {
        this.productViewCountKafkaTemplate = productViewCountKafkaTemplate;
        this.productViewCountStore = productViewCountStore;
    }

    public Map<String, Integer> fetchProductViewCountData() {
        KeyValueIterator<String, ProductViewCount> all = productViewCountStore.all();
        Map<String, Integer> productViewCountMap = new HashMap<>();

        try (KeyValueIterator<String, ProductViewCount> iterator = productViewCountStore.all()) {
            while (iterator.hasNext()) {
                KeyValue<String, ProductViewCount> entry = iterator.next();
                productViewCountMap.putIfAbsent(entry.key, entry.value.getViewCount());
            }
        }
        return productViewCountMap;
    }

    public Map<String, ProductViewCount> fetchProductViewCountMap() {
        KeyValueIterator<String, ProductViewCount> all = productViewCountStore.all();
        Map<String, ProductViewCount> productViewCountMap = new HashMap<>();

        try (KeyValueIterator<String, ProductViewCount> iterator = productViewCountStore.all()) {
            while (iterator.hasNext()) {
                KeyValue<String, ProductViewCount> entry = iterator.next();
                productViewCountMap.putIfAbsent(entry.key, entry.value);
            }
        }
        return productViewCountMap;
    }

    public  void updateViewCount(String productId){
        ProductViewCount productViewCount = productViewCountStore.get(productId);
        int newViewCount = productViewCount.getViewCount()+1;
        productViewCount.setViewCount(newViewCount);
        productViewCountKafkaTemplate.send(AppConstants.Product_Count_Topic,productId,productViewCount);
        System.out.println("viewCount Updated With new Value : "+newViewCount);
    }


}
